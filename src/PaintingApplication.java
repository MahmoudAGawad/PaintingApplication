
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaintingApplication extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Panel drawing;
	private JPanel shapeChoser;
	private Shape drawSelctedShape;
	static JLabel coordinates;
	static JLabel fromStartPoint;
	static JPanel statueBar;
	static JPanel loadedShapes;

	public static void main(String[] args) {
		PaintingApplication app = new PaintingApplication();
		app.setTitle("Painting Application");

		app.setSize(1000, 500);
		// app.setSize(1300, 770);
		app.setLocation(100, 100);
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setVisible(true);
	}

	public PaintingApplication() {

		// setLayout(new BorderLayout());
		drawing = new Panel();
		// add(drawing);
		shapeChoser = new JPanel();
		// drawing.setSize(800, 400);
		// drawing.setBackground(Color.BLACK);
		shapeChoser.setSize(100, 300);

		setLocationRelativeTo(null);
		// / Adding Buttons

		// shapeChoser.setLayout(new GridLayout(3, 3));

		JButton line = new JButton("Line");
		JButton circle = new JButton("Circle \u039F ");
		JButton ellipse = new JButton("Ellipse");
		JButton triangle1 = new JButton("Triangle \u0394");
		// JButton triangle2 = new JButton("Triangle \u14AA");
		JButton rectangle = new JButton("Rectangle \u0A56");
		JButton square = new JButton("Square");
		final JButton color = new JButton("Choose Color");
		final JButton edit = new JButton("Edit Shape's Color");
		JButton undo = new JButton("\u00AB");
		JButton redo = new JButton("\u00BB");
		JButton mouse = new JButton("Mouse");
		JButton delete = new JButton("Delete Shape");
		JButton save1 = new JButton("Save as xml");
		JButton load = new JButton("Load");
		JButton save2 = new JButton("Save as json");
		JButton loadShape = new JButton("load New Shape");
		
		loadShape.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Class Files", "class");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       new LoadShape(chooser.getSelectedFile().getParent(),chooser.getSelectedFile().getName());
			    }
				
			}
		});
		
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "XML & JSON Files", "xml","json");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       int action=3;
			       String fileName=chooser.getSelectedFile().getAbsolutePath();
			       if(fileName.contains(".xml")) action=2;
			       new Save(fileName,action);
			    }
				
			}
		});
		
		save2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JSON Files", "json");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       
			       new Save(chooser.getSelectedFile().getAbsolutePath(),1);
			    }
				
			}
		});
		
		
		save1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "XML Files", "xml");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       
			       new Save(chooser.getSelectedFile().getAbsolutePath(),0);
			    }
				
			}
		});
		
		
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				drawing.deleteShape();

			}
		});

		mouse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				drawing.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				drawing.setMode(2);
			}
		});
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				drawing.setMode(1);
			}
		});

		line.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawSelctedShape = new Line();
				drawing.addShape(drawSelctedShape);
			}
		});

		circle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawSelctedShape = new Circle();
				drawing.addShape(drawSelctedShape);

			}
		});

		ellipse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawSelctedShape = new Ellipse();
				drawing.addShape(drawSelctedShape);

			}
		});

		triangle1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawSelctedShape = new Triangle();
				drawing.addShape(drawSelctedShape);

			}
		});

//		rectangle.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				drawSelctedShape = new Rectangle();
//				drawing.addShape(drawSelctedShape);
//
//			}
//		});

		square.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawSelctedShape = new Square();
				drawing.addShape(drawSelctedShape);

			}
		});

		color.setBackground(Color.GREEN);
		color.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Color toColor = drawing.getNextColor();
				toColor = JColorChooser.showDialog(null, "Pick a Color",
						toColor);
				if (toColor == null) {
					toColor = drawing.getNextColor();
				}
				drawing.setNextColor(toColor);
				if (Panel.mode == 1) {
					edit.setBackground(toColor);
				} else
					color.setBackground(toColor);
			}
		});

		undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				drawing.undo();
				drawing.repaint();
			}
		});

		redo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				drawing.redo();
				drawing.repaint();
			}
		});
		
		loadedShapes = new JPanel();
		loadedShapes.setLayout(new FlowLayout());
		loadedShapes.setBackground(Color.RED);
		
		
		shapeChoser.add(mouse);
		shapeChoser.add(line);
		shapeChoser.add(circle);
		shapeChoser.add(triangle1);
		shapeChoser.add(ellipse);
		shapeChoser.add(rectangle);
		shapeChoser.add(square);
		shapeChoser.add(delete);
		shapeChoser.add(save1);
		shapeChoser.add(save2);
		shapeChoser.add(load);
		shapeChoser.add(loadShape);

		shapeChoser.setLayout(new GridLayout(12, 1));
		JPanel options = new JPanel();
		JPanel choseColor = new JPanel();
		JPanel history = new JPanel();
		statueBar = new JPanel();
		history.setLayout(new GridLayout(1, 2));
		statueBar.setLayout(new GridLayout(1, 2));
		choseColor.setLayout(new GridLayout(1, 2));
		options.setLayout(new GridLayout(3, 4));
		choseColor.add(edit);

		coordinates = new JLabel();
		fromStartPoint = new JLabel();
		statueBar.setBackground(Color.WHITE);
		statueBar.add(coordinates, BorderLayout.WEST);
		statueBar.add(fromStartPoint, BorderLayout.EAST);

		options.setBackground(Color.BLACK);
		choseColor.add(color);

		history.add(undo);
		history.add(redo);
		options.add(choseColor, BorderLayout.NORTH);
		options.add(history, BorderLayout.SOUTH);
		options.add(loadedShapes,BorderLayout.NORTH);
		shapeChoser.setBackground(Color.BLACK);
		add(drawing, BorderLayout.CENTER);
		add(shapeChoser, BorderLayout.AFTER_LINE_ENDS);
		add(options, BorderLayout.BEFORE_FIRST_LINE);
		add(statueBar, BorderLayout.PAGE_END);
	}

}
