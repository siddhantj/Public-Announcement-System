
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;

import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;



import oracle.spatial.geometry.JGeometry;

public class hw2 extends JFrame 
{
	Connection mainConnection=null;
	Statement mainStatement=null;
	ResultSet mainResultSet = null;
	static Graphics2D globGraphics2d;
	 JPanel panel,img_panel;
	JButton queryButton;
	JCheckBox chk1,chk2,chk3;
	JRadioButton wholeRegButton,ptQueryButton,rangeQueryButton,surrStudButton,emerQueryButton;
	JTextArea queryArea;
	JScrollPane area;
	JLabel label1,label2;
	boolean flagchk1,flagchk2,flagchk3;
	List<Point> pointList=new ArrayList<Point>();
	int flag=0;
	String radioText=null;
	String radioText1="";
	String rightClick="";
	int xCoor,yCoor;		// points on map
	boolean query3Flag=false;
	boolean globalFlag;
	boolean clearArrayListFlag=false;
	int queryCounter=1;
	JLabel labelX,labelY;
	JTextArea mouseTextAreaX,mouseTextAreaY;
	public hw2()
	{
		connectToDB();
		String queryText=null;
		    panel = new JPanel();
		    //img_panel=null;
	       getContentPane().add(panel);		//getContentPane() is for JFrame
	       panel.setLayout(null); 
	       queryButton = new JButton("Submit Query");
	       queryButton.setBounds(860,360,120,30);
	      
	       	       	       
	       queryButton.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	               
	        	   selectQueryType();
	          }
	       });
	       class MouseListeningInImage 
	       {
	    	   public MouseListeningInImage()
	    	   {
	    		   img_panel.addMouseListener(new MouseListener()
	    		   {
	    			   public void mouseClicked(MouseEvent e)
	    			   {
	    				   if((e.getModifiers() & InputEvent.BUTTON1_MASK)!=0)
	    				   {
	    					   if(radioText1.equalsIgnoreCase("Coordinates"))
	    					   {
	    						   
	    						   xCoor=e.getX();
	    						   yCoor=e.getY();
	    						   globalFlag=true;
	    						   radioText="empty";
	    				  				
	    						   img_panel=new ImagePanel(radioText);	//inside calling of function paintComponent takes place.
	    						   img_panel.setBounds(10,10,820,580);
	    						   img_panel.setLayout(null);
	    						   //panel.add(img_panel); 
	    						   panel.revalidate();
	    						   panel.repaint(); 
	    					   }
	    					   
	    					   if(radioText1.equalsIgnoreCase("drawPolygon"))
	    					   {
	    						   query3Flag=false;
	    						   globalFlag=true;
	    						   xCoor=e.getX();
	    						   yCoor=e.getY();
	    						   if(clearArrayListFlag==true)
	    						   {
	    							   pointList.clear();
	    							   clearArrayListFlag=false;
	    						   }
	    						   pointList.add(new Point(xCoor,yCoor));
	    						   radioText="empty";
	    						   img_panel=new ImagePanel(radioText);
	    						   img_panel.setBounds(10,10,820,580);
	    						   img_panel.setLayout(null);
	    						   panel.revalidate();
	    						   panel.repaint();
	    					   }
	    					   if(radioText1.equalsIgnoreCase("nearestAnnSystem"))
	    					   {
	    						  // System.out.println("YP");
	    						   xCoor=e.getX();
	    						   yCoor=e.getY();
	    						   globalFlag=true;
	    						   radioText="empty";
	    						   img_panel=new ImagePanel(radioText);
	    						   img_panel.setBounds(10,10,820,580);
	    						   img_panel.setLayout(null);
	    						   panel.revalidate();
	    						   panel.repaint();
	    					   }
	    					   
	    					   if(radioText1.equalsIgnoreCase("Emergency"))
	    					   {
	    						   xCoor=e.getX();
	    						   yCoor=e.getY();
	    						   globalFlag=true;
	    						   radioText="empty";
	    						   img_panel=new ImagePanel(radioText);
	    						   img_panel.setBounds(10,10,820,580);
	    						   img_panel.setLayout(null);
	    						   panel.revalidate();
	    						   panel.repaint();
	    					   }
	    				   }
	    				   if((e.getModifiers() & InputEvent.BUTTON3_MASK)!=0)
	    				   {
	    					  // System.out.println("Right click");
	    					   query3Flag=true;
	    					   img_panel=new ImagePanel(radioText);
	    					   img_panel.setBounds(10,10,820,580);
    						   img_panel.setLayout(null);
    						   panel.revalidate();
    						   panel.repaint();
	    				   }
	    			   }
		    	  
	    			   public void mousePressed(MouseEvent e)
	    			   {
	    			   }
		    	  
	    			   public void mouseExited(MouseEvent e)
	    			   {
		    		   }
	    			   public void mouseEntered(MouseEvent e)
	    			   {
		    		   }
	    			   public void mouseReleased(MouseEvent e)
	    			   {
		    		   }
	    			   
		       
	    		   	});
	    	 }
	    	  
	       }
	        
	    	class AddMouseMotionListenerToPanel
	    	{
	    		public AddMouseMotionListenerToPanel(JPanel panel)
	    		{
	    			panel.addMouseMotionListener(new java.awt.event.MouseAdapter()
	    			{
	    				public void mouseMoved(MouseEvent e)
	    		    	   {
	    		    		  String x=String.valueOf(e.getPoint().getX());
	    		    		  String y=String.valueOf(e.getPoint().getY());
	    		    		  mouseTextAreaX.setText(x);
	    		    		  mouseTextAreaY.setText(y);
	    		    	   }
	    			});
	    		
	    			panel.addMouseListener(new java.awt.event.MouseAdapter()
		    		{
	    					public void mouseExited(MouseEvent e)
	    					{
	    						String x="";
	    						String y="";
	    						mouseTextAreaX.setText(x);
	    						mouseTextAreaY.setText(y);
	    					}
		    		});
	    		}
	    		
	    		
	    	}
	    	
	    		       
	        panel.add(queryButton);
	       /* Add checkboxes */
	      
	        chk1 = new JCheckBox("AS");
	       chk1.setBounds(850,50,100,50);
	       chk1.setFont(new Font("Default",Font.PLAIN,14));
	       panel.add(chk1);
	       //chk.setSelected(true);
	       
	       chk2=new JCheckBox("Building");
	       chk2.setFont(new Font("Default",Font.PLAIN,14));
	       chk2.setBounds(850,85,100,50);
	       panel.add(chk2);
	       
	        chk3=new JCheckBox("Students");
	       chk3.setFont(new Font("Default",Font.PLAIN,14));
	       chk3.setBounds(950,85,200,50);
	       panel.add(chk3);
	       
	       //code for radio buttons
	        wholeRegButton=new JRadioButton("Whole Region");
	       wholeRegButton.setActionCommand("Whole Region");
	       wholeRegButton.setFont(new Font("Default",Font.PLAIN,14));
	       wholeRegButton.setBounds(850,200,200,20);
	       panel.add(wholeRegButton);
	       
	       wholeRegButton.addActionListener(new ActionListener()
	       {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    		  globalFlag=false;
	    		  panel.remove(img_panel);
	    		  img_panel=new ImagePanel();
	    		  new AddMouseMotionListenerToPanel(img_panel);
	    		  radioText1="";
	    		  img_panel.setBounds(10,10,820,580);
	    		  img_panel.setLayout(null);
			      panel.add(img_panel); 
	    		  panel.revalidate();
	    		  panel.repaint(); 
	    	  }
	       }
	       );
	    	   
	       
	       
	       ptQueryButton=new JRadioButton("Point Query");
	       ptQueryButton.setActionCommand("Point Query");
	       ptQueryButton.setFont(new Font("Default",Font.PLAIN,14));
	       ptQueryButton.setBounds(850,230,200,20);
	       panel.add(ptQueryButton);
	       
	       ptQueryButton.addActionListener(new ActionListener()
	       {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    		  globalFlag=false;
	    		  panel.remove(img_panel);
	    		  radioText="empty";
	    		  img_panel=new ImagePanel(radioText);
	    		  new AddMouseMotionListenerToPanel(img_panel);
	    		  img_panel.setBounds(10,10,820,580);
	    		  radioText1="Coordinates";
	    		  xCoor=1200;
	    		  yCoor=1200;
	    		  img_panel.setLayout(null);
			      panel.add(img_panel);
			      new MouseListeningInImage();
	    		  panel.revalidate();
	    		  panel.repaint();
	    		  
	    	  }
	       }
	       );
	              
	       rangeQueryButton=new JRadioButton("Range Query");
	       rangeQueryButton.setActionCommand("Range Query");
	       rangeQueryButton.setFont(new Font("Default",Font.PLAIN,14));
	       rangeQueryButton.setBounds(850,260,200,20);
	       panel.add(rangeQueryButton);
	       
	       rangeQueryButton.addActionListener(new ActionListener()
	       {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    		  globalFlag=false;
	    		  panel.remove(img_panel);
	    		  img_panel=new ImagePanel();
	    		  new AddMouseMotionListenerToPanel(img_panel);
	    		  img_panel.setBounds(10,10,820,580);
	    		  img_panel.setLayout(null);
	    		  xCoor=1200;
	    		  yCoor=1200;
	    		  radioText1="drawPolygon";
	    		  
			      panel.add(img_panel);			      
			      new MouseListeningInImage();
			      pointList.clear();
	    		  panel.revalidate();
	    		  panel.repaint(); 
	    	  }
	       }
	       );
	       
	        surrStudButton=new JRadioButton("Surrounding Student");
	       surrStudButton.setActionCommand("Surrounding Student");
	       surrStudButton.setFont(new Font("Default",Font.PLAIN,14));
	       surrStudButton.setBounds(850,290,200,20);
	       panel.add(surrStudButton);
	       
	       surrStudButton.addActionListener(new ActionListener()
	       {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    		  panel.remove(img_panel);
	    		  img_panel=new ImagePanel();
	    		  new AddMouseMotionListenerToPanel(img_panel);
	    		  img_panel.setBounds(10,10,820,580);
	    		  img_panel.setLayout(null);
	    		  radioText1="nearestAnnSystem";
	    		  xCoor=1200;
	    		  yCoor=1200;	    		  
	   			  panel.add(img_panel); 
	   			new MouseListeningInImage();
	   			  panel.revalidate();
	    		  panel.repaint(); 
	    	  }
	       }
	       );
	       
	        emerQueryButton=new JRadioButton("Emergency Query");
	       emerQueryButton.setActionCommand("Emergency Query");
	       emerQueryButton.setFont(new Font("Default",Font.PLAIN,14));
	       emerQueryButton.setBounds(850,320,200,20);
	       panel.add(emerQueryButton);
	       
	       emerQueryButton.addActionListener(new ActionListener()
	       {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    		  panel.remove(img_panel);
	    		  img_panel=new ImagePanel();
	    		  new AddMouseMotionListenerToPanel(img_panel);
	    		  img_panel.setBounds(10,10,820,580);
	    		  img_panel.setLayout(null);
	    		  radioText1="Emergency";
	    		  xCoor=1200;
	    		  yCoor=1200;
			      panel.add(img_panel); 
			      new MouseListeningInImage();
	    		  panel.revalidate();
	    		  panel.repaint(); 
	    	  }
	       }
	       );
	       
	       //Group the buttons
	       ButtonGroup group=new ButtonGroup();
	       group.add(wholeRegButton);
	       group.add(ptQueryButton);
	       group.add(rangeQueryButton);
	       group.add(surrStudButton);
	       group.add(emerQueryButton);
	       
	       /*Add text area*/
	        queryArea=new JTextArea("",5,30);
	       queryArea.setEditable(true);
	       queryArea.setLineWrap(true);
	       queryArea.setWrapStyleWord(true);
	       queryArea.setBounds(200,500,50,50);
	       panel.add(queryArea);
	       
	        area = new JScrollPane(queryArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	      // setPreferredSize(new Dimension(300, 200));
	       area.setBounds(10, 600, 1000, 20); 
	      panel.add(area); 
	       /* Mouse Coordinates JTextArea and JLabel */
	        mouseTextAreaX=new JTextArea("",30,30);
	      			mouseTextAreaX.setEditable(true);
	      			mouseTextAreaX.setLineWrap(true);
	      			mouseTextAreaX.setWrapStyleWord(true);
	      			mouseTextAreaX.setBounds(850,450,50,20);
	      			panel.add(mouseTextAreaX);
	      			
	       mouseTextAreaY=new JTextArea("",30,30);
	      			mouseTextAreaY.setEditable(true);
	      			mouseTextAreaY.setLineWrap(true);
	      			mouseTextAreaY.setWrapStyleWord(true);
	      			mouseTextAreaY.setBounds(950,450,50,20);
	      			panel.add(mouseTextAreaY);
	      			
	       labelX=new JLabel("X");
	      		 labelX.setBounds(875,470,20,20);
	      		 panel.add(labelX);
	      
	       labelY=new JLabel("Y");
	      		 labelY.setBounds(975,470,20,20);
	      		 panel.add(labelY);
	      
	      /* Add labels */
		    JLabel label1=new JLabel("Active Feature Type");
		    label1.setBounds(850,20,200,30);
		    panel.add(label1);
		    		    		    
		    JLabel label2=new JLabel("Query");
		    label2.setBounds(860,135,100,100);
		    panel.add(label2);
		    
		    /* Add image */
		      img_panel=new ImagePanel();
		      img_panel.setBounds(10,10,820,580);
		      img_panel.setLayout(null);
		      panel.add(img_panel);
		      new AddMouseMotionListenerToPanel(img_panel);
	       setTitle("Siddhant Jawa 7769-2282-69");
	       setSize(1100, 700);
	       setLocationRelativeTo(null);
	       setDefaultCloseOperation(EXIT_ON_CLOSE);	
	       
	}
	
	class ImagePanel extends JPanel
    {
  	  private BufferedImage image;
  	  
  	  public ImagePanel()
  	  {
  		  try
  		  {
  			  image=ImageIO.read(new File("D:\\Work\\USC\\Fall 2013\\Database Management\\Course Material\\Assignments\\HW2\\HW2\\map.jpg"));
  			  radioText="empty";
  		  }catch(IOException e)
  		  {
  			  System.out.println("Error: "+e.toString());
  		  }
  	   }
  	  
  	  public ImagePanel(String radioText)
  	  {
  		  try
  		  	{
			  image=ImageIO.read(new File("D:\\Work\\USC\\Fall 2013\\Database Management\\Course Material\\Assignments\\HW2\\HW2\\map.jpg"));
  		  	}catch(IOException e)
  		  	{
			  System.out.println("Error: "+e.toString());
  		  	}
	   } 
  	  
  	  
  	  @Override
  	  protected void paintComponent(Graphics g)
  	  {
  		  
  		  super.paintComponent(g);
  		  g.drawImage(image,0,0,820,580,null);
  		  Graphics2D graphics2d=(Graphics2D)g;
  		  switch(radioText)
  		  {
  		  case "Whole Region":
  			  runWholeRegionQuery(graphics2d);
  			  break;
  			  
  		  case "Point Query":
  			  runPointQuery(graphics2d);
  			  break;
  			  
  		  case "Range Query":
  			  runRangeQuery(graphics2d);
  			  break;
  			  
  		  case "Surrounding Student":
  			  runSurrStudQuery(graphics2d);
  			  break;
  		  case "Emergency Query":
  			  runEmergencyQuery(graphics2d);
  			  break;
  		case "empty":
			  // Do nothing
			  break;
  		  }
  		  if(globalFlag==true)
  		  {
  			  if(radioText1.equalsIgnoreCase("Coordinates"))
  			  {
  				  showPointOnMap(graphics2d);
  			  }
  			  if(radioText1.equalsIgnoreCase("drawPolygon"))
  			  {
  				  if(query3Flag==false)
  					  drawPolygon(graphics2d);
  				  else
  					  query3JoinPolygon(graphics2d);
  				  
  			  }
  			  if(radioText1.equalsIgnoreCase("nearestAnnSystem"))
  			  {
  				  query4FindClosestAnnSystem(graphics2d);
  			  
  			  }
  			  if(radioText1.equalsIgnoreCase("Emergency"))
  			  {
  				  query5BrokenAnnSystem(graphics2d);
  			  }
  		  }
  		  
  	  }
    }
	
	
	
	public void selectQueryType()
	{
				
		if(chk1.isSelected())
			flagchk1=true;
		else
			flagchk1=false;
		
		if(chk2.isSelected())
			flagchk2=true;
		else
			flagchk2=false;
		if(chk3.isSelected())
			flagchk3=true;
		else
			flagchk3=false;
		
		
		// Query 1-whole Region
		if(wholeRegButton.isSelected())
		{
			radioText=wholeRegButton.getText();
			globalFlag=false;
		}
		
		//Query2- Point Query
		if( ptQueryButton.isSelected())
		{
			radioText=ptQueryButton.getText();
			globalFlag=false;
			//runPointQuery();
		}
		//Query3- Range Query
		if(rangeQueryButton.isSelected())
		{
			radioText=rangeQueryButton.getText();
			globalFlag=false;
		}
		
		//Query-4Surrounding student query
		if(surrStudButton.isSelected())
		{
			radioText=surrStudButton.getText();
			globalFlag=false;
		}
		
		if(emerQueryButton.isSelected())
		{
			radioText=emerQueryButton.getText();
			globalFlag=false;
		} 
			panel.remove(img_panel);
			img_panel=new ImagePanel(radioText);	//inside calling of function paintComponent takes place.
			img_panel.setBounds(10,10,820,580);
  		  	img_panel.setLayout(null);
		    panel.add(img_panel); 
  		  	panel.revalidate();
  		  	panel.repaint(); 
  		  	
	}
	
	public void runWholeRegionQuery(Graphics2D graphics2d)
	{
		Polygon polygon=null;
		
		try
		{
			
			Statement statement= mainConnection.createStatement();
				if(flagchk1==true)
				{
					
					String sql="SELECT as_position,as_radius from asystems";
					displaySQLQuery(sql);
					ResultSet myResultSet=statement.executeQuery(sql);
					graphics2d.setColor(Color.RED);
					while(myResultSet.next())
					{
						 /* Coordinates of Announcement Systems */
						Struct struct = (Struct)myResultSet.getObject("as_position");	
						JGeometry jgeometry = JGeometry.loadJS(struct);
						double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
						int x = (new Double(ordinates[0])).intValue();
						int y = (new Double(ordinates[1])).intValue();
						//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
						polygon=new Polygon();
						
						polygon.addPoint(x-7, y-7);
						polygon.addPoint(x+8, y-7);
						polygon.addPoint(x+8, y+8);
						polygon.addPoint(x-7, y+8);
						
						graphics2d.fillPolygon(polygon); 
						/* Announcement systems coverage area */
						int radius;
						ResultSetMetaData meta = myResultSet.getMetaData();
						for( int col=1; col<=meta.getColumnCount(); col++)
						{
				   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
				   			{
				   				radius=Integer.parseInt(myResultSet.getString(col));
				   				graphics2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
				   			}
						}
					} 
				  }
				
		
				if(flagchk2==true)
				{
					
					int counter=0;
					String sql="SELECT b_conf from buildings";	
					displaySQLQuery(sql);
					ResultSet myResultSet=statement.executeQuery(sql);
					graphics2d.setColor(Color.YELLOW);
					while(myResultSet.next())
					{
						int[] x=new int[20];
						int[] y=new int[20];
						Struct struct = (Struct)myResultSet.getObject("b_conf");	//b_conf
						JGeometry jgeometry = JGeometry.loadJS(struct);
						double[] ordinates = jgeometry.getOrdinatesArray();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
						for(int i=0;i<ordinates.length;i=i+2)
						{
							//System.out.print("("+ordinates[i]+","+ordinates[i+1]+")");
							x[counter]=(new Double(ordinates[i])).intValue();
							y[counter]=(new Double(ordinates[i+1])).intValue();
							counter++;
					     }
						graphics2d.drawPolygon(x, y, counter);
						counter=0;
					} 
					
				}
				
				if(flagchk3==true)
				{
					String sql="SELECT std_position from students";	
					displaySQLQuery(sql);
					ResultSet myResultSet=statement.executeQuery(sql);
					graphics2d.setColor(Color.GREEN);
					while(myResultSet.next())
					{
						Struct struct = (Struct)myResultSet.getObject("std_position");	//b_conf
						JGeometry jgeometry = JGeometry.loadJS(struct);
						double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_ORDINATES_ARRAY
						int x = (new Double(ordinates[0])).intValue();
						int y = (new Double(ordinates[1])).intValue();
						//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
						polygon=new Polygon();
						
						polygon.addPoint(x-5, y-5);
						polygon.addPoint(x+5, y-5);
						polygon.addPoint(x+5, y+5);
						polygon.addPoint(x-5, y+5);
						
						graphics2d.fillPolygon(polygon); 
					} 
				}
													
		}catch(SQLException e)
		{
			System.out.println("Error: "+e.toString());
			System.exit(-1);
		}
		
	}
	
	public void runPointQuery(Graphics2D graphics2d)
	{
		Polygon polygon=null;
		showPointOnMap(graphics2d);
		
		try
		{
			Statement ptQueryStatement=mainConnection.createStatement();
			
			String sql1="SELECT coordinates from pointQueryTable where pt_id=1";
			displaySQLQuery(sql1);
			ResultSet resultSet1=ptQueryStatement.executeQuery(sql1);
			while(resultSet1.next())
			{
				Struct mapPoint=(Struct)resultSet1.getObject("coordinates");
				
				if(flagchk1==true)
				{
					
					PreparedStatement statement=mainConnection.prepareStatement("SELECT as_position,as_radius from asystems where SDO_WITHIN_DISTANCE(as_position,?,'distance=50')='TRUE'");
					statement.setObject(1, mapPoint);
					String printsql="SELECT as_position,as_radius from asystems where SDO_WITHIN_DISTANCE(as_position,?,'distance=50')='TRUE'";
					displaySQLQuery(printsql);
					ResultSet myResultSet=statement.executeQuery();
					
					while(myResultSet.next())
					{
						graphics2d.setColor(Color.GREEN);
						 /* Coordinates of Announcement Systems */
						Struct struct = (Struct)myResultSet.getObject("as_position");	
						JGeometry jgeometry = JGeometry.loadJS(struct);
						double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
						int x = (new Double(ordinates[0])).intValue();
						int y = (new Double(ordinates[1])).intValue();
						//System.out.println( "(X = " + x + ", Y = " + y + ")" );
					
						polygon=new Polygon();
						
						polygon.addPoint(x-7, y-7);
						polygon.addPoint(x+8, y-7);
						polygon.addPoint(x+8, y+8);
						polygon.addPoint(x-7, y+8);
						
						graphics2d.fillPolygon(polygon); 
						int radius;
						ResultSetMetaData meta = myResultSet.getMetaData();
						for( int col=1; col<=meta.getColumnCount(); col++)
						{
				   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
				   			{
				   				radius=Integer.parseInt(myResultSet.getString(col));
				   				graphics2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
				   			}
						}
					}
					
					
					PreparedStatement statement1=mainConnection.prepareStatement("SELECT as_position,as_radius from asystems where SDO_NN(as_position,?,'sdo_num_res=1')='TRUE' and SDO_WITHIN_DISTANCE(as_position,?,'distance=50')='TRUE'");
					statement1.setObject(1, mapPoint);
					statement1.setObject(2, mapPoint);
					ResultSet myResultSet1=statement1.executeQuery();
						while(myResultSet1.next())
						{
							graphics2d.setColor(Color.YELLOW);
							Struct struct1 = (Struct)myResultSet1.getObject("as_position");	
							JGeometry jgeometry1 = JGeometry.loadJS(struct1);
							double[] ordinates1 = jgeometry1.getPoint();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
							int x = (new Double(ordinates1[0])).intValue();
							int y = (new Double(ordinates1[1])).intValue();
							//System.out.println( "(X = " + x + ", Y = " + y + ")" );
						
							polygon=new Polygon();
							
							polygon.addPoint(x-7, y-7);
							polygon.addPoint(x+8, y-7);
							polygon.addPoint(x+8, y+8);
							polygon.addPoint(x-7, y+8);
							
							graphics2d.fillPolygon(polygon); 
							int radius;
							ResultSetMetaData meta = myResultSet.getMetaData();
							for( int col=1; col<=meta.getColumnCount(); col++)
							{
					   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
					   			{
					   				radius=Integer.parseInt(myResultSet1.getString(col));
					   				graphics2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
					   			}
							}
						}
				}
				
				if(flagchk2==true)
				{
					
					int counter=0;
					PreparedStatement statement=mainConnection.prepareStatement("SELECT b_conf from buildings where SDO_WITHIN_DISTANCE(b_conf,?,'distance=50')='TRUE'");				
					String printsql="SELECT b_conf from buildings where SDO_WITHIN_DISTANCE(b_conf,?,'distance=50')='TRUE'";
					displaySQLQuery(printsql);
					statement.setObject(1, mapPoint);
					ResultSet myResultSet=statement.executeQuery();
					while(myResultSet.next())
					{
						graphics2d.setColor(Color.GREEN);
						int[] x=new int[20];
						int[] y=new int[20];
						Struct struct = (Struct)myResultSet.getObject("b_conf");	//b_conf
						JGeometry jgeometry = JGeometry.loadJS(struct);
						double[] ordinates = jgeometry.getOrdinatesArray();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
						for(int i=0;i<ordinates.length;i=i+2)
						{
							//System.out.print("("+ordinates[i]+","+ordinates[i+1]+")");
							x[counter]=(new Double(ordinates[i])).intValue();
							y[counter]=(new Double(ordinates[i+1])).intValue();
							counter++;
					     }
						graphics2d.drawPolygon(x, y, counter);
						counter=0;
					}
					
					PreparedStatement statement1=mainConnection.prepareStatement("SELECT b_conf from buildings where SDO_NN(b_conf,?,'sdo_num_res=1')='TRUE' and SDO_WITHIN_DISTANCE(b_conf,?,'distance=50')='TRUE'");
					statement1.setObject(1, mapPoint);
					statement1.setObject(2,mapPoint);
					ResultSet myResultSet1=statement1.executeQuery();
					while(myResultSet1.next())
					{
						graphics2d.setColor(Color.YELLOW);
						int[] x=new int[20];
						int[] y=new int[20];
						Struct struct1 = (Struct)myResultSet1.getObject("b_conf");	//b_conf
						JGeometry jgeometry1 = JGeometry.loadJS(struct1);
						double[] ordinates1 = jgeometry1.getOrdinatesArray();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
						for(int i=0;i<ordinates1.length;i=i+2)
						{
							//System.out.print("("+ordinates1[i]+","+ordinates1[i+1]+")");
							x[counter]=(new Double(ordinates1[i])).intValue();
							y[counter]=(new Double(ordinates1[i+1])).intValue();
							counter++;
					     }
						graphics2d.drawPolygon(x, y, counter);
						counter=0;
					}
					
				}
					
				
				
				if(flagchk3==true)
				{
					PreparedStatement statement=mainConnection.prepareStatement("SELECT std_position from students where SDO_WITHIN_DISTANCE(std_position,?,'distance=50')='TRUE'");				
					statement.setObject(1, mapPoint);
					String printSql="SELECT std_position from students where SDO_WITHIN_DISTANCE(std_position,?,'distance=50')='TRUE'";
					displaySQLQuery(printSql);
					ResultSet myResultSet=statement.executeQuery();
					while(myResultSet.next())
					{
						graphics2d.setColor(Color.GREEN);
						Struct struct = (Struct)myResultSet.getObject("std_position");	//b_conf
						JGeometry jgeometry = JGeometry.loadJS(struct);
						double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_ORDINATES_ARRAY
						int x = (new Double(ordinates[0])).intValue();
						int y = (new Double(ordinates[1])).intValue();
						//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
						polygon=new Polygon();
						
						polygon.addPoint(x-5, y-5);
						polygon.addPoint(x+5, y-5);
						polygon.addPoint(x+5, y+5);
						polygon.addPoint(x-5, y+5);
						
						graphics2d.fillPolygon(polygon); 
					}
					PreparedStatement statement1=mainConnection.prepareStatement("SELECT std_position from students where SDO_NN(std_position,?,'sdo_num_res=1')='TRUE' and SDO_WITHIN_DISTANCE(std_position,?,'distance=50')='TRUE'");
					statement1.setObject(1, mapPoint);
					statement1.setObject(2,mapPoint);
					ResultSet myResultSet1=statement1.executeQuery();
					while(myResultSet1.next())
					{
						graphics2d.setColor(Color.YELLOW);
						Struct struct1 = (Struct)myResultSet1.getObject("std_position");	//b_conf
						JGeometry jgeometry = JGeometry.loadJS(struct1);
						double[] ordinates1 = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_ORDINATES_ARRAY
						int x = (new Double(ordinates1[0])).intValue();
						int y = (new Double(ordinates1[1])).intValue();
						//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
						polygon=new Polygon();
						
						polygon.addPoint(x-5, y-5);
						polygon.addPoint(x+5, y-5);
						polygon.addPoint(x+5, y+5);
						polygon.addPoint(x-5, y+5);
						
						graphics2d.fillPolygon(polygon);
					}
				}
				
			}
			}catch(SQLException e)
			{
				System.out.println("Error in runPointQuery(): "+e.toString());
				System.exit(-1);
			}	
			
		
	}
	
	public void runRangeQuery(Graphics2D graphics2d)
	{
		Polygon polygon=null;
		graphics2d.setColor(Color.RED);
		query3JoinPolygon(graphics2d);
		int radius=0;
		try
		{
			if(flagchk1==true)
				{
					Statement statement=mainConnection.createStatement();
					String sql="SELECT as_position,as_radius FROM asystems,drawPolygonTable WHERE SDO_ANYINTERACT(coordinates,as_position)='TRUE'";
					displaySQLQuery(sql);
					ResultSet myResultSet=statement.executeQuery(sql);
					while(myResultSet.next())
					{
						/* Coordinates of Announcement Systems */
						Struct struct = (Struct)myResultSet.getObject("as_position");	
						JGeometry jgeometry = JGeometry.loadJS(struct);
						double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
						int x = (new Double(ordinates[0])).intValue();
						int y = (new Double(ordinates[1])).intValue();
						//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
						polygon=new Polygon();
						
						polygon.addPoint(x-7, y-7);
						polygon.addPoint(x+8, y-7);
						polygon.addPoint(x+8, y+8);
						polygon.addPoint(x-7, y+8);
						
						graphics2d.fillPolygon(polygon); 
						ResultSetMetaData meta = myResultSet.getMetaData();
						for( int col=1; col<=meta.getColumnCount(); col++)
						{
				   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
				   			{
				   				radius=Integer.parseInt(myResultSet.getString(col));
				   			}
						}
						graphics2d.drawOval(x-radius, y-radius, 2*radius, 2*radius); 
					}
				}
			if(flagchk2==true)
			{
				int counter=0;
				Statement statement=mainConnection.createStatement();
				String sql="SELECT b_conf FROM buildings,drawPolygonTable WHERE SDO_RELATE(b_conf,coordinates,'mask=anyinteract')='TRUE'";
				displaySQLQuery(sql);
				ResultSet myResultSet=statement.executeQuery(sql);
				while(myResultSet.next())
				{
					int[] x=new int[20];
					int[] y=new int[20];
					Struct struct = (Struct)myResultSet.getObject("b_conf");	//b_conf
					JGeometry jgeometry = JGeometry.loadJS(struct);
					double[] ordinates = jgeometry.getOrdinatesArray();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
					for(int i=0;i<ordinates.length;i=i+2)
					{
						//System.out.print("("+ordinates[i]+","+ordinates[i+1]+")");
						x[counter]=(new Double(ordinates[i])).intValue();
						y[counter]=(new Double(ordinates[i+1])).intValue();
						counter++;
				     }
					graphics2d.setColor(Color.GREEN);
					graphics2d.drawPolygon(x, y, counter);
					counter=0;
				}
			}
			
			if(flagchk3==true)
			{
				graphics2d.setColor(Color.GREEN);
				Statement statement=mainConnection.createStatement();
				String sql="SELECT std_position FROM students,drawPolygonTable WHERE SDO_ANYINTERACT(coordinates,std_position)='TRUE'";
				displaySQLQuery(sql);
				ResultSet myResultSet=statement.executeQuery(sql);
				while(myResultSet.next())
				{
					Struct struct = (Struct)myResultSet.getObject("std_position");	//b_conf
					JGeometry jgeometry = JGeometry.loadJS(struct);
					double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_ORDINATES_ARRAY
					int x = (new Double(ordinates[0])).intValue();
					int y = (new Double(ordinates[1])).intValue();
					//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
					polygon=new Polygon();
					
					polygon.addPoint(x-5, y-5);
					polygon.addPoint(x+5, y-5);
					polygon.addPoint(x+5, y+5);
					polygon.addPoint(x-5, y+5);
					
					graphics2d.fillPolygon(polygon); 
					
				}
			}
			
		}catch(SQLException e)
		{
			System.out.println("Error in runRangeQuery(): "+ e.toString());
		}
		//pointList.clear();
		query3Flag=false;
		clearArrayListFlag=true;
	}
	
	
	
	public void runSurrStudQuery(Graphics2D graphics2d)
	{
		Polygon polygon=null;
		Struct struct=null;
		int radius=0;
		graphics2d.setColor(Color.RED);
		query4FindClosestAnnSystem(graphics2d);
		
		try
		{
			Statement statement=mainConnection.createStatement();
			statement.executeUpdate("delete from pointQueryTable where pt_id=2");
			displaySQLQuery("delete from pointQueryTable where pt_id=2");
			
			statement.executeUpdate("insert into pointQueryTable values(2,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+xCoor+","+yCoor+",NULL),NULL,NULL))");
			displaySQLQuery("insert into pointQueryTable values(2,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE(xCoor+,+yCoor+,NULL),NULL,NULL))");
			
			String sql="SELECT as_position,as_radius from asystems,pointQueryTable where SDO_NN(as_position,coordinates,'sdo_num_res=1')='TRUE' and pt_id=2" ;
			displaySQLQuery(sql);
			ResultSet myResultSet=statement.executeQuery(sql);
			while(myResultSet.next())
			{
				 /* Coordinates of Announcement Systems */
				 struct = (Struct)myResultSet.getObject("as_position");
				
				ResultSetMetaData meta = myResultSet.getMetaData();
				for( int col=1; col<=meta.getColumnCount(); col++)
				{
		   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
		   			{
		   				radius=Integer.parseInt(myResultSet.getString(col));
		   			}
				}
				
			 }
			
			graphics2d.setColor(Color.green);
			PreparedStatement prepStatement=mainConnection.prepareStatement("SELECT std_position from students where SDO_WITHIN_DISTANCE(std_position,?,'distance="+radius+"')='TRUE'");
			displaySQLQuery("SELECT std_position from students where SDO_WITHIN_DISTANCE(std_position,struct,'distance="+radius+"')='TRUE'");
			prepStatement.setObject(1, struct);
			ResultSet myResultSet1=prepStatement.executeQuery();
			while(myResultSet1.next())
			{
				Struct struct1 = (Struct)myResultSet1.getObject("std_position");	//b_conf
				JGeometry jgeometry = JGeometry.loadJS(struct1);
				double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_ORDINATES_ARRAY
				int x = (new Double(ordinates[0])).intValue();
				int y = (new Double(ordinates[1])).intValue();
				//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
				polygon=new Polygon();
				
				polygon.addPoint(x-5, y-5);
				polygon.addPoint(x+5, y-5);
				polygon.addPoint(x+5, y+5);
				polygon.addPoint(x-5, y+5);
				
				graphics2d.fillPolygon(polygon);
			}
			
			
			
			
		}catch(SQLException e)
		{
			System.out.println("Error in runSurrStudQuery():"+e.toString());
			
		}
	}
	
	public void runEmergencyQuery(Graphics2D graphics2d)
	{	
		Polygon polygonStudent=null;
		Polygon polygonAnnSystem=null;
		Struct struct=null;
		int radius=0;
		int secondNearestAnnSystemRadius=0;
		String ann_id=null;
		String secondNearestAnnSystem=null;
		graphics2d.setColor(Color.RED);
		int x=0,y=0;
		int x1=0,y1=0;
	//	List<Struct> studentPosition=new ArrayList<Struct>();
		
		try
		{
			Statement statement=mainConnection.createStatement();
			statement.executeUpdate("delete from pointQueryTable where pt_id=2");
			displaySQLQuery("delete from pointQueryTable where pt_id=2");
			
			statement.executeUpdate("insert into pointQueryTable values(2,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+xCoor+","+yCoor+",NULL),NULL,NULL))");
			displaySQLQuery("insert into pointQueryTable values(2,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+xCoor+","+yCoor+",NULL),NULL,NULL))");
			
			String sql="SELECT as_position,as_radius,as_id from asystems,pointQueryTable where SDO_NN(as_position,coordinates,'sdo_num_res=1')='TRUE' and pt_id=2" ;
			displaySQLQuery(sql);
			ResultSet myResultSet=statement.executeQuery(sql);
			while(myResultSet.next())
			{
				struct = (Struct)myResultSet.getObject("as_position");
				
				ResultSetMetaData meta = myResultSet.getMetaData();
				for( int col=1; col<=meta.getColumnCount(); col++)
				{
		   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
		   			{
		   				radius=Integer.parseInt(myResultSet.getString(col));	//announcement system radius
		   			}
		   			if(meta.getColumnName(col).equalsIgnoreCase("as_id"))
		   			{
		   				ann_id=myResultSet.getString(col);		//announcement system id
		   			}
		   			
				}
			}
			
			//graphics2d.setColor(Color.GREEN);
			PreparedStatement prepStatement=mainConnection.prepareStatement("SELECT std_position from students where SDO_WITHIN_DISTANCE(std_position,?,'distance="+radius+"')='TRUE'");
			displaySQLQuery("SELECT std_position from students where SDO_WITHIN_DISTANCE(std_position,?,'distance="+radius+"')='TRUE'");
			prepStatement.setObject(1, struct);
			ResultSet myResultSet1=prepStatement.executeQuery();
			while(myResultSet1.next())
			{
				Struct struct1 = (Struct)myResultSet1.getObject("std_position");	//b_conf
				JGeometry jgeometry = JGeometry.loadJS(struct1);
				double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_ORDINATES_ARRAY
				 x = (new Double(ordinates[0])).intValue();
				 y = (new Double(ordinates[1])).intValue();
				//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
				
				//studentPosition.add(struct1);
				
			
																					
				PreparedStatement prepStatement2=mainConnection.prepareStatement("SELECT as_position,as_id,as_radius from asystems where SDO_NN(as_position,?,'sdo_num_res=2')='TRUE' and as_id<>?");
				displaySQLQuery("SELECT as_position,as_id,as_radius from asystems where SDO_NN(as_position,?,'sdo_num_res=2')='TRUE' and as_id<>?");
				prepStatement2.setObject(1, struct1);
				prepStatement2.setString(2, ann_id);
				ResultSet myResultSet2=prepStatement2.executeQuery();
				while(myResultSet2.next())
				{
					Struct struct2 = (Struct)myResultSet2.getObject("as_position");	
					JGeometry jgeometry1 = JGeometry.loadJS(struct2);
					double[] ordinates1 = jgeometry1.getPoint();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
					 x1 = (new Double(ordinates1[0])).intValue();
					 y1 = (new Double(ordinates1[1])).intValue();
					
					ResultSetMetaData meta = myResultSet2.getMetaData();
					for( int col=1; col<=meta.getColumnCount(); col++)
					{
			   			if(meta.getColumnName(col).equalsIgnoreCase("as_id"))
			   			{
			   				secondNearestAnnSystem=myResultSet2.getString(col);	//2nd nearest announcement system
			   			}
			   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
			   			{
			   				secondNearestAnnSystemRadius=myResultSet2.getInt(col);
			   			}
			   		}
					if(secondNearestAnnSystem.equalsIgnoreCase("a1psa"))
					{
						graphics2d.setColor(Color.BLUE);
					}
					else if(secondNearestAnnSystem.equalsIgnoreCase("a2ohe"))
					{
						graphics2d.setColor(Color.CYAN);
					}
					else if(secondNearestAnnSystem.equalsIgnoreCase("a3sgm"))
					{
						graphics2d.setColor(Color.GREEN);
					}
					else if(secondNearestAnnSystem.equalsIgnoreCase("a4hnb"))
					{
						graphics2d.setColor(Color.MAGENTA);
					}
					else if(secondNearestAnnSystem.equalsIgnoreCase("a5vhe"))
					{
						graphics2d.setColor(Color.WHITE);
					}
					else if(secondNearestAnnSystem.equalsIgnoreCase("a6ssc"))
					{
						graphics2d.setColor(Color.PINK);
					}
					else 												//announcement system=a7helen
					{
						graphics2d.setColor(Color.RED);
					}
					
						
					
					//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
					 
				}
				polygonStudent=new Polygon();
				
				polygonStudent.addPoint(x-5, y-5);
				polygonStudent.addPoint(x+5, y-5);
				polygonStudent.addPoint(x+5, y+5);
				polygonStudent.addPoint(x-5, y+5);
				graphics2d.fillPolygon(polygonStudent);
				
				polygonAnnSystem=new Polygon();
				
				polygonAnnSystem.addPoint(x1-7, y1-7);
				polygonAnnSystem.addPoint(x1+8, y1-7);
				polygonAnnSystem.addPoint(x1+8, y1+8);
				polygonAnnSystem.addPoint(x1-7, y1+8);
				
				graphics2d.fillPolygon(polygonAnnSystem);
				graphics2d.drawOval(x1-secondNearestAnnSystemRadius, y1-secondNearestAnnSystemRadius, 2*secondNearestAnnSystemRadius, 2*secondNearestAnnSystemRadius);
			}
		

		//	ResultSet resultSet2=sta
			
			
		}catch(Exception e)
		{
			System.out.println("Error in runEmergencyQuery(): "+e.toString());
		}
	}
	
	public void showPointOnMap(Graphics2D graphics2d)
	{
		
		graphics2d.setColor(Color.RED);
		Polygon polygon=new Polygon();
		polygon.addPoint(xCoor-2, yCoor-2);
		polygon.addPoint(xCoor+3,yCoor-2);
		polygon.addPoint(xCoor+3, yCoor+3);
		polygon.addPoint(xCoor-2, yCoor+3);
		graphics2d.fillPolygon(polygon);
		graphics2d.drawOval(xCoor-50, yCoor-50, 2*50, 2*50);
		try
		{
			Statement stmt=mainConnection.createStatement();
			stmt.executeUpdate("delete from pointQueryTable where pt_id=1");
			stmt.executeUpdate("insert into pointQueryTable values(1,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+xCoor+","+yCoor+",NULL),NULL,NULL))");
			
		}catch(SQLException e)
		{
			System.out.println("Error: "+e.toString());
		} 
		
	}
	
	public void drawPolygon(Graphics2D graphics2d)
	{
		graphics2d.setColor(Color.GREEN);
		//pointList.add(new Point(xCoor,yCoor));
		
		
		for(Point point: pointList)
		{	
			Polygon pointPoly=new Polygon();
			pointPoly.addPoint(point.x-2, point.y-2);
			pointPoly.addPoint(point.x+3,point.y-2);
			pointPoly.addPoint(point.x+3,point.y+3);
			pointPoly.addPoint(point.x-2,point.y+3);
			graphics2d.fillPolygon(pointPoly);
		}
	}
	
	public void query3JoinPolygon(Graphics2D graphics2d)
	{
		int tempx,tempy,counter=0;
		try
		{
			Statement statement=mainConnection.createStatement();
			statement.executeUpdate("delete from drawPolygonTable");
			String sqlStatement="insert into drawPolygonTable values(1,SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(" ;
			for(Point point: pointList)
			{
				tempx=point.x;
				tempy=point.y;
				sqlStatement=sqlStatement + tempx + "," + tempy;
				if(pointList.size()-counter==1)
					break;
				else
				{
					sqlStatement=sqlStatement + ",";
					counter++;
				}
				
			 }
			sqlStatement= sqlStatement + ")))";
			statement.executeUpdate(sqlStatement);
			/* Retrieve that polygon back */
			int pointCounter=0;
			String sql="SELECT coordinates from drawPolygonTable where poly_id=1";				
			ResultSet myResultSet=statement.executeQuery(sql);
			graphics2d.setColor(Color.RED);
			while(myResultSet.next())
			{
				int[] x=new int[20];
				int[] y=new int[20];
				Struct struct = (Struct)myResultSet.getObject("coordinates");	//b_conf
				JGeometry jgeometry = JGeometry.loadJS(struct);
				double[] ordinates = jgeometry.getOrdinatesArray();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
				for(int i=0;i<ordinates.length;i=i+2)
				{
					//System.out.print("("+ordinates[i]+","+ordinates[i+1]+")");
					x[pointCounter]=(new Double(ordinates[i])).intValue();
					y[pointCounter]=(new Double(ordinates[i+1])).intValue();
					pointCounter++;
			     }
				graphics2d.drawPolygon(x, y, pointCounter);
				//counter=0;
			} 
			
		}catch(SQLException e)
		{
			System.out.println("Error in function Query3JoinPloygon(): "+e.toString());
		}
		//query3Flag=false;
		
	}
	
	
	public void query4FindClosestAnnSystem(Graphics2D graphics2d)
	{
		Polygon polygon=null;
		
		graphics2d.setColor(Color.RED);
		try
		{
			Statement statement=mainConnection.createStatement();
			statement.executeUpdate("delete from pointQueryTable where pt_id=2");
			statement.executeUpdate("insert into pointQueryTable values(2,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+xCoor+","+yCoor+",NULL),NULL,NULL))");
			String sql="SELECT as_position,as_radius from asystems,pointQueryTable where SDO_NN(as_position,coordinates,'sdo_num_res=1')='TRUE' and pt_id=2" ;
			ResultSet myResultSet=statement.executeQuery(sql);
			while(myResultSet.next())
			{
				 /* Coordinates of Announcement Systems */
				Struct struct = (Struct)myResultSet.getObject("as_position");	
				JGeometry jgeometry = JGeometry.loadJS(struct);
				double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
				int x = (new Double(ordinates[0])).intValue();
				int y = (new Double(ordinates[1])).intValue();
				//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
				polygon=new Polygon();
				
				polygon.addPoint(x-7, y-7);
				polygon.addPoint(x+8, y-7);
				polygon.addPoint(x+8, y+8);
				polygon.addPoint(x-7, y+8);
				
				graphics2d.fillPolygon(polygon); 
				/* Announcement systems coverage area */
				int radius;
				ResultSetMetaData meta = myResultSet.getMetaData();
				for( int col=1; col<=meta.getColumnCount(); col++)
				{
		   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
		   			{
		   				radius=Integer.parseInt(myResultSet.getString(col));
		   				graphics2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
		   			}
				}
			}
		}catch(SQLException e)
		{
			System.out.println("Error in query4ClosestAnnSystem(): "+e.toString());
		}
	}
	
	public void query5BrokenAnnSystem(Graphics2D graphics2d)
	{
		Polygon polygon=null;
		
		graphics2d.setColor(Color.RED);
		try
		{
			Statement statement=mainConnection.createStatement();
			statement.executeUpdate("delete from pointQueryTable where pt_id=2");
			statement.executeUpdate("insert into pointQueryTable values(2,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+xCoor+","+yCoor+",NULL),NULL,NULL))");
			String sql="SELECT as_position,as_radius from asystems,pointQueryTable where SDO_NN(as_position,coordinates,'sdo_num_res=1')='TRUE' and pt_id=2" ;
			ResultSet myResultSet=statement.executeQuery(sql);
			while(myResultSet.next())
			{
				 /* Coordinates of Announcement Systems */
				Struct struct = (Struct)myResultSet.getObject("as_position");	
				JGeometry jgeometry = JGeometry.loadJS(struct);
				double[] ordinates = jgeometry.getPoint();		//jgeometry.getOrdinatesArray for SDO_POINT_TYPE
				int x = (new Double(ordinates[0])).intValue();
				int y = (new Double(ordinates[1])).intValue();
				//System.out.println( "(X = " + x + ", Y = " + y + ")" ); 
				polygon=new Polygon();
				
				polygon.addPoint(x-7, y-7);
				polygon.addPoint(x+8, y-7);
				polygon.addPoint(x+8, y+8);
				polygon.addPoint(x-7, y+8);
				
				graphics2d.fillPolygon(polygon); 
				/* Announcement systems coverage area */
				int radius;
				ResultSetMetaData meta = myResultSet.getMetaData();
				for( int col=1; col<=meta.getColumnCount(); col++)
				{
		   			if(meta.getColumnName(col).equalsIgnoreCase("as_radius"))
		   			{
		   				radius=Integer.parseInt(myResultSet.getString(col));
		   				graphics2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
		   			}
				}
			}
		}catch(SQLException e)
		{
			System.out.println("Error in query5BrokenAnnSystem(): "+e.toString());
		}
	}
	
	
	public void connectToDB()
	{
		
			try{
				System.out.print("Looking for Oracle's jdbc-odbc driver ... ");
			
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				System.out.println("Loaded");
			
				String URL="jdbc:oracle:thin:@localhost:1521:orcl2";
				String userName="sys as sysdba";
				String password="Albert007";
			
				System.out.print("Connecting to DB...");
				mainConnection = DriverManager.getConnection(URL, userName, password);
				System.out.println(", Connected!");

			mainStatement = mainConnection.createStatement();
			}catch(Exception e)
			{
				System.out.println( "Error while connecting to DB: "+ e.toString() );
	     		e.printStackTrace();
	     		System.exit(-1);
			}
			
			
		}
	
	public void displaySQLQuery(String sqlStatement)
	{
		sqlStatement="\n Query "+queryCounter+": "+sqlStatement ;
		queryArea.append(sqlStatement);
		queryCounter++;
		
	}
	
public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
            public void run() 
			{
                hw2 demo = new hw2();
                demo.setVisible(true);
			}
		});

	}

}

