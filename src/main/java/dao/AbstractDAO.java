package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

import javax.swing.*;

/**
 * Aceasta este clasa în care se generează interogările generale pentru fiecare tabel si tip de date
 * @param <T>
 */

public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Se generează interogarea pentru a afișa toate datele din tabel
     * @return String
     */
    private String createSelectQuery()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Se generează interogarea pentru a afișa rezultatele din tabel care au aceleași date în câmpul ales cu cele introduse
     * @param field
     * @return String
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    /**
     * Se generează interogarea care va sterge o anumită linie din tabelul dorit
     * @param field
     * @return
     */
    private String createDeleteQuery(String field)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    /**
     * Se generează interogarea care va actualiza datele liniei cu id-ul dat
     * @param fields
     * @return String
     */
    private String createUpdateQuery(Field[] fields)
    {
        String s = "";
        s += "UPDATE ";
        s += type.getSimpleName();
        s += " SET ";
        for(int i = 1; i < fields.length; i++) {
            s += fields[i].getName();
            s += "=?";
            if (i < fields.length - 1)
                s += ", ";
        }
        s += " WHERE " + fields[0].getName() + "=?";
        return s;
    }

    /**
     * Se generează interogarea care va insera în tabel datele introduse
     * @param fields
     * @return String
     */
    private String createInsertQuery(Field[] fields) {
        String s = "";
        s += "INSERT INTO ";
        s += type.getSimpleName();
        s += " VALUES(";
        for (int i = 0; i < fields.length; i++){
            s += "?";
            if(i < fields.length - 1)
                s += ", ";
        }
        s += ")";
        return s;
    }

    /**
     * Se generează o listă cu toate datele din tabelul dorit
     * @return list
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Se returnează linia care are id-ul introdus
     * @return T
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("ID");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Se returnează primul rezultat care conține numele introdus
     * @param name
     * @return T
     */
    public T findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Se elimina linia care are id-ul introdus
     * @param id
     * @return boolean
     */
    public boolean deleteById(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("ID");
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Se realizează inserarea in tabel a datelor introduse
     * @param t
     * @return boolean
     */
    public boolean insert(T t){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        Field[] fields = type.getDeclaredFields();
        String query = createInsertQuery(fields);
        try {
            statement = dbConnection.prepareStatement(query);
            for(int i = 1; i <= fields.length; i++)
            {
                fields[i - 1].setAccessible(true);
                statement.setObject(i, fields[i - 1].get(t));
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(dbConnection);
        }
        return false;
    }

    /**
     * Se actualizează linia cu id-ul din datele introduse
     * @param t
     * @return boolean
     */
    public boolean update(T t){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        Field[] fields = type.getDeclaredFields();
        String query = createUpdateQuery(fields);
        try {
            statement = dbConnection.prepareStatement(query);
            for(int i = 1; i < fields.length; i++)
            {
                fields[i].setAccessible(true);
                statement.setObject(i, fields[i].get(t));
            }
            fields[0].setAccessible(true);
            statement.setObject(fields.length, fields[0].get(t));
            statement.executeUpdate();
            return true;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(dbConnection);
        }
        return false;
    }

    public JTable createTable(List<T> list) throws IllegalAccessException {
        Field[] fields = type.getDeclaredFields();
        Object[] columns = new Object[fields.length];
        for (int i = 0; i < fields.length; ++i){
            fields[i].setAccessible(true);
            //System.out.println(fields[i].getName());
            columns[i] = fields[i].getName();
        }
        Object[][] rows = new Object[list.size()][fields.length];
        for(int i = 0; i < list.size(); ++i)
        {
            Object[] newRow = new Object[fields.length];
            for(int k = 0; k < fields.length; ++k)
            {
                newRow[k] = fields[k].get(list.get(i));
                //System.out.print(fields[k].get(list.get(i)) + " ");
            }
            //System.out.println();
            rows[i] = newRow;
        }
        return new JTable(rows, columns);
    }
}
