package main;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public class LinkedListReflection {
    private static Object last;
    private static int index;

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        showInside(list);
    }

    @SneakyThrows
    private static void showInside(List<String> list) {
        Class<? extends List> listClass = list.getClass();
        Field sizeField = listClass.getDeclaredField("size");
        sizeField.setAccessible(true);
        log.info("Size = {}\n", sizeField.get(list));

        Field firstField = listClass.getDeclaredField("first");
        firstField.setAccessible(true);
        String nodeClassName = "java.util.LinkedList$Node";
        Object first = Class.forName(nodeClassName).cast(firstField.get(list));
        log.info("Index: {}", index);
        log.info("First: {}", first);

        Field lastField = listClass.getDeclaredField("last");
        lastField.setAccessible(true);
        Object last = Class.forName(nodeClassName).cast(lastField.get(list));
        LinkedListReflection.last = last;

        nextPrevItem(first);
        System.out.println();
        log.info("Index: {}", index);
        log.info("Last: {}", last);
        nextPrevItem(last);
    }

    @SneakyThrows
    private static void nextPrevItem(Object node) {
        Class<?> nodeClass = node.getClass();
        Field itemField = nodeClass.getDeclaredField("item");
        itemField.setAccessible(true);
        log.info("\titem: {}", itemField.get(node));

        Field nextField = nodeClass.getDeclaredField("next");
        nextField.setAccessible(true);
        Object next = nextField.get(node);
        log.info("\tnext: {}", next);

        Field prevField = nodeClass.getDeclaredField("prev");
        prevField.setAccessible(true);
        Object prev = prevField.get(node);
        log.info("\tprev: {}", prev);

        if (next != null) {
            index++;
            if (next != last) {
                System.out.println();
                log.info("Index: {}", index);
                log.info("Node: {}", next);
                nextPrevItem(next);
            }
        }

//        Field nextField = nodeClass.getDeclaredField("next");
//        nextField.setAccessible(true);
//        log.info();
//        Object next = Class.forName(nodeClassName).cast(nodeClass.getDeclaredField("next"));
//        Object prev = Class.forName(nodeClassName).cast(nodeClass.getDeclaredField("prev"));
//        log.info("item = {}", item);
    }


}
