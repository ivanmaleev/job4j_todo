package store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.todo.model.User;

public class PsqlStore implements Store {

    private static final PsqlStore INSTANCE = new PsqlStore();

    private static final Logger LOG = LogManager.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        PsqlStore.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            LOG.error("Invalid parameters", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            LOG.error("Driver not loaded", e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T query(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> findAllItems(boolean all, int userid) {
        return query(session -> {
            if (all) {
                Query<Item> query = session.createQuery(
                        "select distinct it from  Item as it "
                                + "left join fetch it.categories "
                                + "where it.user = : user order by it.id");
                query.setParameter("user", new User(userid));
                return query.list();
            } else {
                Query<Item> query = session.createQuery(
                        "select distinct it from  Item as it "
                                + "left join fetch it.categories "
                                + "where it.done = false and it.user = : user order by it.id");
                query.setParameter("user", new User(userid));
                return query.list();
            }
        });
    }

    @Override
    public Item saveItem(Item item, String[] cids) {
        return query(session -> {
            for (String cid : cids) {
                Category category = session.find(Category.class, Integer.parseInt(cid));
                item.addCategory(category);
            }
            session.save(item);
            return item;
        });
    }

    @Override
    public void setDone(int id) {
        query(session -> {
            Query<Item> query = session.createQuery(
                    "update Item set done = true where id =: id");
            query.setParameter("id", id);
            query.executeUpdate();
            return null;
        });
    }

    @Override
    public User saveUser(User user) {
        return query(session -> {
            session.save(user);
            return user;
        });
    }

    @Override
    public User findUserByEmail(String email) {
        return query(session -> {
            Query<User> query = session.createQuery(
                    "from User where email = : email");
            query.setParameter("email", email);
            return query.uniqueResult();
        });
    }

    @Override
    public List<Category> findAllCategories() {
        return query(session ->
                session.createQuery(
                        "from Category").list());
    }
}
