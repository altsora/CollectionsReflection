package main;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class ArrayListReflection {
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        showInside(list);
        add("a");
        add("b", "c");
        add("d", "e", "f", "g", "h", "i", "j");
        add("k");
    }

    @SneakyThrows
    private static void showInside(List<String> list) {
        Class<? extends List> listClass = list.getClass();
        Field sizeField = listClass.getDeclaredField("size");
        sizeField.setAccessible(true);
        log.info("Size = {}", sizeField.get(list));

        Field defaultCapacityField = listClass.getDeclaredField("DEFAULT_CAPACITY");
        defaultCapacityField.setAccessible(true);
        log.info("Default capacity: {}", defaultCapacityField.get(ArrayList.class));

        Field elementDataField = listClass.getDeclaredField("elementData");
        elementDataField.setAccessible(true);
        Object[] objects = (Object[]) elementDataField.get(list);
        log.info("elementData.length = {}", objects.length);
        for (int i = 0; i < objects.length; i++) {
            log.info("\t{}.\t{}", i, objects[i]);
        }
        System.out.println();
    }

    private static void add(String... s) {
        log.info("+ {}", s.length);
        list.addAll(Arrays.asList(s));
        showInside(list);
    }
}
