package main;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ArrayListReflection {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        showInside(list);
        log.info("+ 1");
        list.add("a");
        showInside(list);
        log.info("+ 2");
        list.add("b");
        list.add("c");
        showInside(list);
        log.info("+ 7");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("j");
        showInside(list);
        log.info("+ 1");
        list.add("k");
        showInside(list);
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
}
