package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE " + Util.getDBName() +
                    "(`id` BIGINT(20) NOT NULL AUTO_INCREMENT," +
                    " `name` VARCHAR(45) ," +
                    " `lastName` VARCHAR(45) ," +
                    " `age` TINYINT(0) UNSIGNED ," +
                    " PRIMARY KEY (`id`))\n";

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [СОЗДАНИЕ ТАБЛИЦЫ]");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "DROP TABLE " + Util.getDBName();

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [УДАЛЕНИЕ ТАБЛИЦЫ]");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [СОХРАНЕНИЕ ПОЛЬЗОВАТЕЛЯ]");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.load(User.class, id);
            session.delete(user);

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ]");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Что-то пошло не так! [ПОЛУЧЕНИЕ СПИСКА ПОЛЬЗОВАТЕЛЕЙ]");
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Что-то пошло не так! [ОТЧИСТКА ТАБЛИЦЫ]");
        }
    }
}
