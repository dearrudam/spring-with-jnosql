package com.github.dearrudam.springwithjnosqlmongodb;

import com.github.dearrudam.springwithjnosqlmongodb.sample.Developers;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.inject.spi.CDI;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseQualifier;
import org.eclipse.jnosql.mapping.DatabaseType;

import java.lang.annotation.Annotation;

@Database(DatabaseType.DOCUMENT)
public class App {

    public static void main(String[] args) {
        try (var container = SeContainerInitializer.newInstance().initialize()) {

            Annotation[] annotations = App.class.getAnnotations();
            Instance<Developers> select = CDI.current().select(Developers.class, annotations);
            Developers developers = select.get();

            developers.findAll().forEach(System.out::println);

        }
    }
}
