package core.basesyntax.lib;

import core.basesyntax.dao.BetDao;
import core.basesyntax.dao.BetDaoImpl;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.UserDaoImpl;
import core.basesyntax.exception.MyException;
import core.basesyntax.factory.Factory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Injector {
    public static Object getInstance(Class clazz) {
        Constructor constructor = null;
        Object instance = null;
        try {
            constructor = clazz.getDeclaredConstructor();
            instance = constructor.newInstance();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (field.getType().equals(BetDao.class) && isInjectAnnotation(field)) {
                    if (BetDaoImpl.class.isAnnotationPresent(Dao.class)) {
                        field.set(instance, Factory.getBetDao());
                    } else {
                        throw new MyException("Class " + BetDaoImpl.class.getSimpleName()
                                + " can't contain Dao annotation!");
                    }
                }

                if (field.getType().equals(UserDao.class) && isInjectAnnotation(field)) {
                    if (UserDaoImpl.class.isAnnotationPresent(Dao.class)) {
                        field.set(instance, new UserDaoImpl());
                    } else {
                        throw new MyException("Class " + UserDaoImpl.class.getSimpleName()
                                + " can't contain Dao annotation!");
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Can't create Instance or NoSuch methodException");
        }
        return instance;
    }

    private static boolean isInjectAnnotation(Field field) {
        return field.getAnnotation(Inject.class) != null;
    }
}
