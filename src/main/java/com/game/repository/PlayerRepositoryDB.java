package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {

    private final SessionFactory sessionFactory;

    public PlayerRepositoryDB() {
        Properties prop = new Properties();
        prop.setProperty(Environment.HBM2DDL_AUTO, "update");
        prop.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        prop.setProperty(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        prop.setProperty(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/rpg");
//        prop.setProperty(Environment.URL,"jdbc:mysql://localhost:3306/rpg");
//        prop.setProperty(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
        prop.setProperty(Environment.USER, "root");
        prop.setProperty(Environment.PASS, "root");
        prop.setProperty(Environment.SHOW_SQL,"false");

        sessionFactory = new Configuration()
                .setProperties(prop)
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();
    }

    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {
        try (Session session = sessionFactory.openSession()) {

            return session.createNativeQuery("SELECT * FROM rpg.player", Player.class)
                    .setFirstResult(pageNumber * pageSize)
                    .setMaxResults(pageSize)
//            return session.createNativeQuery("SELECT * FROM player LIMIT :limit OFFSET :offset", Player.class)
//                    .setParameter("limit", pageSize)
//                    .setParameter("offset", pageNumber * pageSize)
                    .list();
        }
    }

    @Override
    public int getAllCount() {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> players = session.createNamedQuery("Player_getAllCount", Long.class);
            return Math.toIntExact(players.uniqueResult());
        }
    }

    @Override
    public Player save(Player player) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(player);
            tx.commit();
            System.out.println(player.getId()   );
        }
        return player;
    }

    @Override
    public Player update(Player player) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            player = (Player) session.merge(player);
            tx.commit();
        }
        return player;
    }

    @Override
    public Optional<Player> findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Player.class, id));
        }
    }

    @Override
    public void delete(Player player) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(player);
            tx.commit();
        }
    }

    @PreDestroy
    public void beforeStop() {
        sessionFactory.close();
    }
}