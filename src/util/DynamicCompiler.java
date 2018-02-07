package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import model.Company;

public class DynamicCompiler {
	public static List<Company> compileAndLoad(final File directory, String classname) {
        final List<Company> res = new LinkedList<Company>();
        if (!directory.exists()) {
            throw new IllegalArgumentException("Directory not found!");
        }
        final List<File> sourceFiles =
            Arrays
            .asList(directory.listFiles())
            .stream()
            .filter(f -> f.getName().equals(classname))
            .collect(Collectors.toList());
        if (sourceFiles.isEmpty()) {
            return Collections.emptyList();
        }
        final List<String> names = new LinkedList<String>();
        for (final File source : sourceFiles) {
            final String fullName = source.getName();
            names.add(fullName.substring(0, fullName.length() - 5));
        }
        try {
            final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            try (final StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
                final List<String> optionList = new LinkedList<String>();
                optionList.add("-classpath");
                optionList.add(System.getProperty("java.class.path"));
                final Iterable<? extends JavaFileObject> compilationUnit =
                    fileManager.getJavaFileObjectsFromFiles(sourceFiles);
                final JavaCompiler.CompilationTask task =
                    compiler.getTask(null, fileManager, diagnostics, optionList, null, compilationUnit);
                if (task.call()) {
                    try (
                        final URLClassLoader classLoader =
                            new URLClassLoader(new URL[]{new File("./").toURI().toURL()})
                    ) {
                        for (final String name : names) {
                            final Class<?> loadedClass = classLoader.loadClass("control.Samples." + name);
                            res.add((Company)loadedClass.newInstance());
                        }
                    }
                } else {
                    final StringBuilder errString = new StringBuilder();
                    for (final Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                        errString.append("Error on line ");
                        errString.append(diagnostic.getLineNumber());
                        errString.append(" in ");
                        errString.append(diagnostic.getSource().toUri());
                        errString.append("\n");
                    }
                    throw new IllegalArgumentException(errString.toString());
                }
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
        return res;
    }
}
