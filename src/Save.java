
import java.awt.Color;
import java.io.File;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.*;

import org.w3c.dom.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class Save {

	public Save(String fileName, int action) {
		if (action == 0)
			writeXML(fileName);
		else if(action==1){
			writeJSON(fileName);
		}
		else if (action == 2) {
			loadXML(fileName);
		}
		else if(action==3){
			loadJSON(fileName);
		}

	}
	
	private void loadJSON(String fileName){
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(fileName));
			 
			JSONArray arr = (JSONArray) obj;
			
			Stack<Shape> shapes = new Stack<Shape>();
			
			for (int i = 0; i < arr.size(); i++) {
				JSONObject shapei=(JSONObject) arr.get(i);
				int sx, ex, sy, ey, clr;
				String type=(String)shapei.get("type");
				sx=Integer.parseInt(shapei.get("stx").toString());
				ex=Integer.parseInt(shapei.get("endx").toString());
				sy=Integer.parseInt(shapei.get("sty").toString());
				ey=Integer.parseInt(shapei.get("endy").toString());
				clr=Integer.parseInt(shapei.get("color").toString());
				
				Shape shp = new Line();
				if (type.equals("painting.Circle")) {
					shp = new Circle();
				} else if (type.equals("painting.Triangle")) {
					shp = new Triangle();
				} else if (type.equals("painting.Rectangle")) {
					
					shp = new Rectangle();
				} else if (type.equals("painting.Ellipse")) {
					shp = new Ellipse();
				} else if (type.equals("painting.Square")) {
					shp = new Square();
				}
				shp.addColor(new Color(clr));
				//System.out.println(sx + " " + sy + " " + ex + " " + ey);
				shp.setStartPoint(sx, sy);
				shp.setEndPoint(ex, ey);
				
				shapes.push(shp);
				
			}
			PaintingApplication.drawing.setShapes(shapes);
			
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null,
					   "File Not Loaded");
		}
	}
	
	private void writeJSON(String fileName){
		try {
			JSONArray shape=new JSONArray();
			
			
			Stack<Shape> shapes = PaintingApplication.drawing.getShapes();
			for(Shape shp : shapes){
				JSONObject obj = new JSONObject();
				obj.put("type", shp.getClass().getName());
				obj.put("color", shp.getColor().getRGB());
				obj.put("stx", shp.getStartPoint().getX());
				obj.put("sty", shp.getStartPoint().getY());
				obj.put("endx", shp.getEndPoint().getX());
				obj.put("endy", shp.getEndPoint().getY());
				
				shape.add(obj);
			}
			
			FileWriter file = new FileWriter(fileName+".json");
			file.write(shape.toJSONString());
			file.flush();
			file.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			 JOptionPane.showMessageDialog(null,
					   "File Not Saved");
		}
	}
	
	
	private void loadXML(String fileName) {
		try {

			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			NodeList shapes = doc.getElementsByTagName("shape");
			Stack<Shape> loadShapes = new Stack<Shape>();
			//System.out.println(shapes.getLength());
			for (int i = 0; i < shapes.getLength(); i++) {
				int sx, ex, sy, ey, clr;
				String type;

				Node temp = shapes.item(i);
				Element shapei = (Element) temp;
				type = (shapei.getElementsByTagName("type").item(0)
						.getTextContent());

				Node stxy = shapei.getElementsByTagName("start_point").item(0);
				Node endxy = shapei.getElementsByTagName("end_point").item(0);
				
				
				
				sx = Integer.parseInt(stxy.getChildNodes().item(0)
						.getTextContent());
				sy = Integer.parseInt(stxy.getChildNodes().item(1)
						.getTextContent());
				ex = Integer.parseInt(endxy.getChildNodes().item(0)
						.getTextContent());
				ey = Integer.parseInt(endxy.getChildNodes().item(1)
						.getTextContent());
				
//				System.out.println(stxy.getChildNodes().item(0)
//						.getTextContent());
//
//				System.out.println(stxy.getChildNodes().item(1)
//						.getTextContent());
//				System.out.println(endxy.getChildNodes().item(0)
//						.getTextContent());
//				System.out.println(endxy.getChildNodes().item(1)
//						.getTextContent());

				clr  = Integer
						.parseInt(shapei.getElementsByTagName("color").item(0)
								.getTextContent());

				Shape shp = new Line();
				if (type.equals("painting.Circle")) {
					shp = new Circle();
				} else if (type.equals("painting.Triangle")) {
					shp = new Triangle();
				} else if (type.equals("painting.Rectangle")) {
					shp = new Rectangle();
				} else if (type.equals("painting.Ellipse")) {
					shp = new Ellipse();
				} else if (type.equals("painting.Square")) {
					shp = new Square();
				}
				shp.addColor(new Color(clr));
				//System.out.println(sx + " " + sy + " " + ex + " " + ey);
				shp.setStartPoint(sx, sy);
				shp.setEndPoint(ex, ey);

				loadShapes.push(shp);

			}
			PaintingApplication.drawing.setShapes(loadShapes);
			 JOptionPane.showMessageDialog(null,
					   "File Loaded Successfully");
		} catch (Exception e) {
			// TODO: handle exception
			 JOptionPane.showMessageDialog(null,
					   "File Not Loaded");
		}
	}

	private void writeXML(String fileName) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("shapes");
			doc.appendChild(rootElement);

			Stack<Shape> shapes = PaintingApplication.drawing.getShapes();
			int i = 0;
			for (Shape shp : shapes) {
				i++;
				Element shape = doc.createElement("shape");
				shape.setAttribute("id", i + "");

				// /// 4 info for the shape ////////

				// Element #1
				Element type = doc.createElement("type");

				type.appendChild(doc.createTextNode(shp.getClass().getName()));
				shape.appendChild(type);

				// Element #2
				Color clr = shp.getColor();

				Element color = doc.createElement("color");
				color.appendChild(doc.createTextNode(clr.getRGB() + ""));
				shape.appendChild(color);

				// Element #3
				Element startPoint = doc.createElement("start_point");

				Element x = doc.createElement("x");
				x.appendChild(doc.createTextNode(shp.getStartPoint().getX()
						+ ""));

				Element y = doc.createElement("y");
				y.appendChild(doc.createTextNode(shp.getStartPoint().getY()
						+ ""));

				startPoint.appendChild(x);
				startPoint.appendChild(y);

				shape.appendChild(startPoint);

				// Element #4
				Element endPoint = doc.createElement("end_point");

				Element x2 = doc.createElement("x");
				x2.appendChild(doc
						.createTextNode(shp.getEndPoint().getX() + ""));

				Element y2 = doc.createElement("y");
				y2.appendChild(doc
						.createTextNode(shp.getEndPoint().getY() + ""));

				endPoint.appendChild(x2);
				endPoint.appendChild(y2);

				shape.appendChild(endPoint);
				// ////////////////////////////////

				rootElement.appendChild(shape);

			}
			
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName + ".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (Exception e) {
			// TODO: handle exception
			 JOptionPane.showMessageDialog(null,
					   "File Not Saved");
		}
	}

}
