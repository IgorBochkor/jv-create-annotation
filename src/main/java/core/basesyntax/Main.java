package core.basesyntax;

import core.basesyntax.controller.ConsoleHandler;
import core.basesyntax.dao.BetDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.factory.Factory;
import core.basesyntax.lib.Injector;

public class Main {
    public static void main(String[] args) {
        ConsoleHandler handler = (ConsoleHandler) Injector.getInstance(ConsoleHandler.class);
        System.out.println("Введіть value та risk для Вашої ставки");
        handler.handle();

        BetDao betDao = Factory.getBetDao();
        System.out.println("all bets: " + betDao.getAll());

        UserDao userDao = Factory.getUserDao();
        System.out.println("all users: " + userDao.getAll());
    }
}
