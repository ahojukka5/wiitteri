package wiitteri.controllers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wiitteri.models.Account;
import wiitteri.models.Connection;
import wiitteri.models.Image;
import wiitteri.models.Tweet;
import wiitteri.models.TweetKind;

@Controller
public class DemoController {

    @GetMapping("/demo/export_schema")
    @ResponseBody
    public String exportSchema() {
        Map<String, String> settings = new HashMap<>();
        settings.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        settings.put("hibernate.connection.url", "jdbc:h2:file:./database;create=true");
        settings.put("hibernate.connection.username", "sa");
        settings.put("hibernate.connection.password", "");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Account.class);
        metadataSources.addAnnotatedClass(Connection.class);
        metadataSources.addAnnotatedClass(Image.class);
        metadataSources.addAnnotatedClass(Tweet.class);
        metadataSources.addAnnotatedClass(TweetKind.class);
        Metadata metadata = metadataSources.buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setOutputFile("create.sql");
        schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
        return "Schema exported to create.sql";
    }

    @GetMapping("/demo/reset")
    public String reset(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
