package com.mvc.PackageScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClasspathPackageScanner implements PackageScanner{
    private Logger logger = LoggerFactory.getLogger(ClasspathPackageScanner.class);
    private String basePackage;
    private ClassLoader cl;

    /**
     * ��ʼ��
     * @param basePackage
     */
    public ClasspathPackageScanner(String basePackage) {
        this.basePackage = basePackage;
        this.cl = getClass().getClassLoader();
    }
    public ClasspathPackageScanner(String basePackage, ClassLoader cl) {
        this.basePackage = basePackage;
        this.cl = cl;
    }
    /**
     *��ȡָ�����µ������ֽ����ļ���ȫ����
     */
    public List<String> getFullyQualifiedClassNameList() throws IOException {
        logger.info("��ʼɨ���{}�µ�������", basePackage);
        return doScan(basePackage, new ArrayList<String>());
    }

    /**
     *doScan����
     * @param basePackage
     * @param nameList
     * @return
     * @throws IOException
     */
    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {
        String splashPath = StringUtil.dotToSplash(basePackage);
        URL url = cl.getResource(splashPath);   //file:/D:/WorkSpace/java/ScanTest/target/classes/com/scan
        String filePath = StringUtil.getRootPath(url);
        System.out.println(filePath);
        List<String> names = null; // contains the name of the class file. e.g., Apple.class will be stored as "Apple"
        if (isJarFile(filePath)) {// ���ж��Ƿ���jar���������jar����ͨ��JarInputStream������JarEntityȥ�ݹ��ѯ������
            if (logger.isDebugEnabled()) {
                logger.debug("{} ��һ��JAR��", filePath);
            }
            names = readFromJarFile(filePath, splashPath);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("{} ��һ��Ŀ¼", filePath);
            }
            names = readFromDirectory(filePath);
        }
        for (String name : names) {
            if (isClassFile(name)) {
                nameList.add(toFullyQualifiedName(name, basePackage));
            } else {
                doScan(basePackage + "." + name, nameList);
            }
        }
        if (logger.isDebugEnabled()) {
            for (String n : nameList) {
                logger.debug("�ҵ�{}", n);
            }
        }
        return nameList;
    }

    private String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(StringUtil.trimExtension(shortName));
        //��ӡ�����
        System.out.println(sb.toString());
        return sb.toString();
    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("��JAR���ж�ȡ��: {}", jarPath);
        }
        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();
        List<String> nameList = new ArrayList<String>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    private List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    /**
     * For test purpose.
     */
    public static void main(String[] args) throws Exception {
        PackageScanner scan = new ClasspathPackageScanner("com.gxh.Controller");
        scan.getFullyQualifiedClassNameList();
    }
}