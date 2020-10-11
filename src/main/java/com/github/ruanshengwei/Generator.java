package com.github.ruanshengwei;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Generator {

    private static List<Class> classes = new ArrayList<>();

    private static MyClassLoader classLoader = new MyClassLoader();

    public static void main(String[] args) {

        generateTest("/Users/ruanshengwei/Desktop/dev/code/testGenerator/target/classes/com/github/ruanshengwei/model",
                "/Users/ruanshengwei/Desktop/dev/code/testGenerator/src/test/java");

    }

    private static void generateTest(String modelDirectory, String outPutDirectory) {

        getClassFromDict(modelDirectory);
        System.out.print(classes);
        generateTestClass(outPutDirectory);

    }

    private static void generateTestClass(String outPutDirectory) {

        for (Class clazz : classes) {

            String className = clazz.getName() + "Test";

            String classOldSimpleName = clazz.getSimpleName();

            String classNewSimpleName = clazz.getSimpleName() + "Test";

            String outPutName = outPutDirectory + "/" + className.replace(".", "/") + ".java";

            File file = new File(outPutName);

            try {
                List<String> lines = new ArrayList<>();

                lines.add("package " + clazz.getPackage().getName() + ";");

                for (Field field : clazz.getDeclaredFields()) {
                    if (!field.getType().getName().startsWith("java")) {

                        if (!isNative(field.getType().getName())) {

                            if (!field.getType().getPackage().getName().equals(clazz.getPackage().getName())) {
                                lines.add("import " + field.getType().getName() + ";");
                            }
                        }

                    }

                }

                lines.add("");

                lines.add("public class " + classNewSimpleName + "{");

                lines.add("    public void test(){");

                lines.add("        " + classOldSimpleName + " " + classOldSimpleName.toLowerCase() + "=" + "new " + classOldSimpleName + "();");

                for (Method method : Arrays.stream(clazz.getDeclaredMethods()).filter(a -> a.getName().startsWith("set")).collect(Collectors.toList())) {

                    System.out.println(method.getName());
                    String value = "";

                    System.out.println("method.getParameterTypes: "+method.getParameterTypes()[0]);

                    switch (method.getParameterTypes()[0].getName()){
                        case "java.lang.Integer": value = "1"; break;
                        case "int": value = "1"; break;
                        case "java.lang.Long": value = "1L"; break;
                        case "long": value = "1L"; break;
                        case "java.lang.Double": value = "1d"; break;
                        case "double": value = "1d"; break;
                        default: value = "new " +  method.getParameterTypes()[0].getSimpleName() +"()";
                    }


                    lines.add("        " + classOldSimpleName.toLowerCase() + "." + method.getName() + "(" + value + ");");

                }


                lines.add("    }");

                lines.add("}");

                FileUtils.writeLines(file, lines);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static boolean isNative(String typeName){

        if (typeName.equals("float")||typeName.equals("short")||typeName.equals("char")||typeName.equals("byte")||
        typeName.equals("int")||typeName.equals("long")||typeName.equals("double")||typeName.equals("boolean")){
            return true;
        }
        return false;

    }

    private static String firstCharToUpperCase(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    private static List<Class> getClassFromDict(String dict) {

        File dir = new File(dict);

        if (!dir.exists() || !dir.isDirectory()) {
            return classes;
        }


        // 过滤获取目录，or class文件
        File[] dirfiles = dir.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith("class"));
        String className;
        Class clz;
        for (File f : dirfiles) {
            if (f.isDirectory()) {
                getClassFromDict(f.getAbsolutePath());
                continue;
            }

            // 加载类
            clz = classLoader.load(f.getAbsolutePath());
            if (clz != null) {
                classes.add(clz);
            }
        }

        return classes;

    }


}
