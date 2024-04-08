package com.itemis.annotationfinder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.ConstructorCriteria;
import org.burningwave.core.classes.FieldCriteria;
import org.burningwave.core.classes.MethodCriteria;
import org.burningwave.core.classes.SearchConfig;

public class ApplicationMain {

	public static void main(String[] args) {
		System.out.println("*** Annotated Class Finder ***");
		
		if(args.length != 1) {
			System.out.println("Expected one command line argument. Folder search path not specified.");
			System.exit(1);
		}else {
			if(! new File(args[0]).exists()) {
				System.out.println("The given argument is not a valid File or Folder. Application will be terminated");
				System.exit(1);
			}else {
				System.out.println("File Exists");
			}
		}
		try {
			Collection<Class<?>> annotatedClasses = find(args[0]);
			for(Class<?> classname: annotatedClasses) {
				//Filter only the classes related to xtext
				if(classname.getName().contains("xtext"))
				System.out.println(classname.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Collection<Class<?>> find(String searchPath) {
        ComponentSupplier componentSupplier = ComponentContainer.getInstance();
        ClassHunter classHunter = componentSupplier.getClassHunter();
        Collection<String> searchpaths = new ArrayList<String>();
        searchpaths.add(searchPath);
        SearchConfig searchConfig = SearchConfig.forPaths(
        	searchpaths
        ).by(
            ClassCriteria.create().allThoseThatMatch((cls) -> {
                return cls.getAnnotations() != null && cls.getAnnotations().length > 0;
            }).or().byMembers(
                MethodCriteria.withoutConsideringParentClasses().allThoseThatMatch((method) -> {
                    return method.getAnnotations() != null && method.getAnnotations().length > 0;
                })
            ).or().byMembers(
                FieldCriteria.withoutConsideringParentClasses().allThoseThatMatch((field) -> {
                    return field.getAnnotations() != null && field.getAnnotations().length > 0;
                })
            ).or().byMembers(
                ConstructorCriteria.withoutConsideringParentClasses().allThoseThatMatch((ctor) -> {
                    return ctor.getAnnotations() != null && ctor.getAnnotations().length > 0;
                })
            )
        );
        try (ClassHunter.SearchResult searchResult = classHunter.findBy(searchConfig)) {
    
            //If you need all annotated methods uncomment this
            //searchResult.getMemberFlatMap().values();
    
            return searchResult.getClasses();
        }
    }
}
