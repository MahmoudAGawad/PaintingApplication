
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Stack;

import javax.swing.JButton;

public class LoadShape {
	
	public LoadShape(String path,String name){
		name=name.replace(".class", "");
//		name="painting."+name;
		load(path,name);
	}
	
	private void load(String path,String name){
		
		try {
		System.out.println(path);
		File file = new File(path);

		// Convert File to a URL
		URL url;
		
		url = file.toURI().toURL();
		

		URL[] urls = { url };
 
		// Create a new class loader with the directory
		URLClassLoader cl = new URLClassLoader(urls);
		System.out.println(name);
		final Class cls = cl.loadClass(name);
		
		JButton newShape= new JButton(name);
		newShape.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				
				try {
					Shape ob =(Shape) cls.newInstance();
					PaintingApplication.drawing.addShape(ob);
				} catch (InstantiationException | IllegalAccessException e) {
					System.out.println("Can't add Shape Object");
				}
			}
		});
		
		PaintingApplication.loadedShapes.add(newShape);
		
		
		
		System.out.println("Done");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
