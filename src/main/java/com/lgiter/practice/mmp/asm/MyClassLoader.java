package com.lgiter.practice.mmp.asm;


public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if ("lgiter.practice.mmp.asm.HelloWorld".equals(name)) {
            byte[] bytes = HelloWorldDump.dump();
            Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
            return clazz;
        }

        throw new ClassNotFoundException("Class Not Found: " + name);
    }
}