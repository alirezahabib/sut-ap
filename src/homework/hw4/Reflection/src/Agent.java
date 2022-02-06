import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Agent {
    public List<String> getMethodNames(Object object) {
        return Arrays.stream(object.getClass().getDeclaredMethods()).map(Method::getName).collect(Collectors.toList());
    }

    public Object getFieldContent(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    public void setFieldContent(Object object, String fieldName, Object content) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        if (field.get(object).getClass().equals(content.getClass())) field.set(object, content);
    }

    public Object call(Object object, String methodName, Object[] parameters) throws Exception {
        Method method = object.getClass().getDeclaredMethod(methodName,
                Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
        method.setAccessible(true);
        return method.invoke(object, parameters);
    }

    public Object createANewObject(String fullClassName, Object[] initials) throws Exception {
        Constructor<?> constructor = Class.forName(fullClassName).getConstructor(
                Arrays.stream(initials).map(Object::getClass).toArray(Class[]::new));
        constructor.setAccessible(true);
        return constructor.newInstance(initials);
    }

    public String debrief(Object object) {
        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        Method[] methods = objectClass.getDeclaredMethods();

        return "Name: " + objectClass.getSimpleName() + "\n" +
                "Package: " + objectClass.getPackage().getName() + "\n" +
                "No. of Constructors: " + objectClass.getDeclaredConstructors().length +
                "\n===\nFields:\n" +
                Arrays.stream(fields).sorted(Comparator.comparing(Field::getName))
                        .map(field -> Modifier.toString(field.getModifiers()) + " " +
                                field.getType().getSimpleName() + " " + field.getName() + "\n")
                        .collect(Collectors.joining()) +
                "(" + fields.length + " fields)\n" +
                "===\nMethods:\n" +
                Arrays.stream(methods).sorted(Comparator.comparing(Method::getName))
                        .map(method -> {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            return method.getReturnType().getSimpleName() + " " + method.getName()
                                    + "("
                                    + (parameterTypes.length > 0 ? parameterTypes[0].getSimpleName() : "") +
                                    Arrays.stream(parameterTypes)
                                            .skip(1).map(parameterClass -> ", " + parameterClass.getSimpleName())
                                            .collect(Collectors.joining()) + ")\n";
                        }).collect(Collectors.joining()) +
                "(" + methods.length + " methods)";
    }

    public Object clone(Object toClone) throws Exception {
        if (toClone == null) return null;
        Class<?> clazz = toClone.getClass();

        Object newEntity;
        if ((newEntity = setIfReadOnly(toClone)) != null) return newEntity;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            newEntity = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            // object does not have no-input constructor. Trying to make a primitive array instead.
            newEntity = newPrimitiveArray(toClone);
        }

        while (clazz != null) {
            copyFields(toClone, newEntity, clazz);
            clazz = clazz.getSuperclass();
        }
        return newEntity;
    }

    private Object setIfReadOnly(Object toClone) {
        Class<?> clazz = toClone.getClass();
        if (clazz.isPrimitive() || clazz.isEnum() ||
                toClone instanceof String || toClone instanceof Integer ||
                toClone instanceof Byte || toClone instanceof Character ||
                toClone instanceof Short || toClone instanceof Long ||
                toClone instanceof Float || toClone instanceof  Double ||
                toClone instanceof Boolean) return toClone;
        return null;
    }

    private Object newPrimitiveArray(Object toClone) {
        if (toClone instanceof int[]) return new int[((int[]) toClone).length];
        if (toClone instanceof byte[]) return new byte[((byte[]) toClone).length];
        if (toClone instanceof char[]) return new char[((char[]) toClone).length];
        if (toClone instanceof short[]) return new short[((short[]) toClone).length];
        if (toClone instanceof long[]) return new long[((long[]) toClone).length];
        if (toClone instanceof float[]) return new float[((float[]) toClone).length];
        if (toClone instanceof double[]) return new double[((double[]) toClone).length];
        if (toClone instanceof boolean[]) return new boolean[((boolean[]) toClone).length];
        if (toClone.getClass().isArray()) return new Object[((Object[]) toClone).length];
        return null;
    }

    private void copyFields(Object entity, Object newEntity, Class<?> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();

        if (entity.getClass().isArray()) {
            try {
                Object[] array = (Object[]) entity;
                Object[] newArray = (Object[]) newEntity;
                for (int i = 0; i < array.length; i++) newArray[i] = clone(array[i]);
            } catch (Exception ignored) {
            }
        }

        for (Field field : fields) {
            field.setAccessible(true);

            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(newEntity, clone(field.get(entity)));
        }
    }
}
