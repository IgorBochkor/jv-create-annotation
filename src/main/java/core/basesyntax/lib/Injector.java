package core.basesyntax.lib;

import core.basesyntax.dao.BetDao;
import core.basesyntax.dao.BetDaoImpl;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.UserDaoImpl;
import core.basesyntax.exception.AnnotationException;
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
                if (field.getAnnotation(Inject.class) != null) {
                    if (BetDaoImpl.class.isAnnotationPresent(Dao.class)
                            && field.getType().equals(BetDao.class)) {
                        field.set(instance, Factory.getBetDao());
                    } else if (field.getType().equals(UserDao.class)
                            && UserDaoImpl.class.isAnnotationPresent(Dao.class)) {
                        field.set(instance, Factory.getUserDao());
                    } else {
                        throw new AnnotationException("Implement class can't"
                                + " contain @Dao annotation!");
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Can't create Instance or NoSuch methodException");
        }
        return instance;
    }
}
