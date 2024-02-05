package com.tech.project;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

public class HibernateUtil {
	
	private static StandardServiceRegistry registry;
	private static SessionFactory sf;
	
	public static SessionFactory getsessionFactory()
	{
		Map<String, Object> setting=new HashMap<String, Object>();
		setting.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
		setting.put(Environment.URL, "jdbc:mysql://localhost:3306/bank");
		setting.put(Environment.USER, "root");
		setting.put(Environment.PASS, "root");
		
		setting.put(Environment.HBM2DDL_AUTO, "update");
		setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQL55Dialect");
		setting.put(Environment.SHOW_SQL, "true");
		
		registry=new StandardServiceRegistryBuilder().applySettings(setting).build();
		
		MetadataSources ms=new MetadataSources(registry).addAnnotatedClass(Customer.class).addAnnotatedClass(Transaction_History.class);
		Metadata md = ms.getMetadataBuilder().build();
		sf=md.getSessionFactoryBuilder().build();
		 return sf;
		
		
	
	}

}
