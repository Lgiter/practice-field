package com.lgiter.practice.mmp.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Author: lixiaolong
 * Date: 2023/5/16
 * Desc:
 */
public class HelloWorldDump implements Opcodes {

    public static byte[] dump() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        /**
         *  final int version,
         *  final int access,
         *  final String name,
         *  final String signature,
         *  final String superName,
         *  final String[] interfaces
         */
        cw.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "lgiter/practice/mmp/asm/HelloWorld", null, "java/lang/Object", null);

        {
            MethodVisitor mv1 = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv1.visitCode();
            mv1.visitVarInsn(ALOAD, 0);
            mv1.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv1.visitInsn(RETURN);
            mv1.visitMaxs(1, 1);
            mv1.visitEnd();
        }
        {
            MethodVisitor mv2 = cw.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
            mv2.visitCode();
            mv2.visitLdcInsn("This is a HelloWorld object.");
            mv2.visitInsn(ARETURN);
            mv2.visitMaxs(1, 1);
            mv2.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

}
