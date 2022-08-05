package jm.task.core.hibernate.dao;

import jm.task.core.hibernate.Main;
import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String sql = "CREATE TABLE " + Main.getDBName() +
                        "(`id` BIGINT(20) NOT NULL AUTO_INCREMENT," +
                        " `name` VARCHAR(45) ," +
                        " `lastName` VARCHAR(45) ," +
                        " `age` TINYINT(0) UNSIGNED ," +
                        " PRIMARY KEY (`id`))\n";

                session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [СОЗДАНИЕ ТАБЛИЦЫ]");
        }
    }

    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String sql = "DROP TABLE " + Main.getDBName();

                session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [УДАЛЕНИЕ ТАБЛИЦЫ]");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));

                transaction.commit();
                System.out.println("User с именем " + name + " добавлен в базу данных");
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [СОХРАНЕНИЕ ПОЛЬЗОВАТЕЛЯ]");
        }
    }

    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.load(User.class, id);
                session.delete(user);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ]");
        }
    }

    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [ПОЛУЧЕНИЕ СПИСКА ПОЛЬЗОВАТЕЛЕЙ]");
            return null;
        }
    }

    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createQuery("DELETE FROM User").executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так! [ОТЧИСТКА ТАБЛИЦЫ]");
        }
    }
}
