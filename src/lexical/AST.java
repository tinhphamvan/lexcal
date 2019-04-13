package lexical;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import java.util.Random;

public class AST extends JFrame  {
    private static class student {
        private int id;
        private String hoten;
        private String ngaysinh;
        private float diemTB;
        private int sotinchi;


        public  student(int id, String hoten, String ngaysinh, float diemTB, int sotinchi) {
            this.id = id;
            this.hoten = hoten;
            this.ngaysinh = ngaysinh;
            this.diemTB = diemTB;
            this.sotinchi = sotinchi;
        }
    }
    private static class Node {
        private student stu;
        private Node left, right, parent;
        private int height;
        public Node(student stu) {
            this.stu = stu;
            left = null;
            right = null;
            height = 0;
        }
    }
    
    
  //String to contain traversal
    String lnr = ""; 
    String lrn = ""; 
    String nlr = ""; 
    String rnl = ""; 
    String nrl = ""; 
    String rln = ""; 
    //String to contain find result
    String list1 = "";//id
    String list2 = "";// hoten...
    String list3 = "";
    String list4 = "";
    String list5 = "";
    String list6 = "";
    String list7 = "";
    //String for edit
    String id;
    String hoten;
    String ngaysinh;
    String diemTB;
    String sotinchi;
    
    //String for random name
    String[] ho =  new String[4];
	String[] ten = new String[4];
	String[] lot = new String[4];
	
    public Node root = null; 

    int[][] toaDo = new int[500][3]; //luu toa do x,y,mssv cua 1 node
    int counter = 0;// bien dem cho toaDo

    boolean treePainted = false; //cay da duoc ve hay chua?

    

    //text fields
    private JTextField fSearch = new JTextField(15);//for search
    private JTextArea fArea = new JTextArea(300, 300);// show result
    private JTextField f4a = new JTextField(15);//for update and add
    private JTextField f4b = new JTextField(15);
    private JTextField f4c = new JTextField(15);
    private JTextField f4d = new JTextField(15);
    private JTextField f4e = new JTextField(15);
    private JTextField fDelete = new JTextField(15);//for delete


    //Panel contains the buttons

    Font font = new Font("Verdana", Font.BOLD, 12);

    //Panel to display the tree
    private Tree view = new Tree();

    public AST() {
        //Constructor to create the GUI of the class
        setTitle("AST - Trần Thanh Trúc - 51603341");
        setBackground(Color.white);

        setLayout(new GridLayout(1, 2));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        
        //Adding the tree view panel to the frame, setting border and size
        add(view);
        view.setBorder(new TitledBorder("Tree View"));
        view.setPreferredSize(new Dimension(800, 800));
        view.setVisible(true);
        view.setBackground(Color.white);

        //Adding panels containing the buttons and textfields
    
        
        //adding textarea
        fArea.setFont(fArea.getFont().deriveFont(16f));
        JScrollPane scroll = new JScrollPane(fArea);
        scroll.setBounds(30, 650, 760, 380);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        
        
       
        //adding radio
      

        //Adding ActionListeners to all the buttons
       


        //Finishing up
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void draw(String[] args) {
        ho[0]="Lê";ho[1]="Trần";ho[2]="Nguyễn";ho[3] = "Đinh";
        lot[0]="Văn";lot[1]="Thị";lot[2]="Anh";lot[3] = "Ngọc";
        ten[0]="Long";ten[1]="Nhi";ten[2]="Việt";ten[3] = "Nam";
        	Random rnd = new Random();
        	int n = 10 + rnd.nextInt(4);
            int[] arr = new int[n];
                for (int i = 0; i < n; i++) {
                    arr[i] = 100 + rnd.nextInt(899);
                }
                student x = new student(0, "Tên chưa cập nhật", "Ngày sinh chưa cập nhật", 0, 0);
                x.id = arr[0];
                x.hoten = ho[rnd.nextInt(4)]+" "+lot[rnd.nextInt(4)]+" "+ten[rnd.nextInt(4)];
                x.ngaysinh = Integer.toString(1+rnd.nextInt(28))+"/"+ Integer.toString(1+rnd.nextInt(11))+"/"+ Integer.toString(2000+rnd.nextInt(5));
                x.diemTB = rnd.nextInt(10);
                x.sotinchi = 50+rnd.nextInt(100);
                
                Node temp = new Node(x);
                root = insert(temp,x);
                for (int i = 1; i < n; i++) {
                    student y = new student(0, "Tên chưa cập nhật", "Ngày sinh chưa cập nhật", 0, 0);
                    y.id = arr[i];
                    y.hoten = ho[rnd.nextInt(4)]+" "+lot[rnd.nextInt(4)]+" "+ten[rnd.nextInt(4)];
                    y.ngaysinh = Integer.toString(1+rnd.nextInt(28))+"/"+ Integer.toString(1+rnd.nextInt(11))+"/"+ Integer.toString(2000+rnd.nextInt(5));
                    y.diemTB = rnd.nextInt(10);
                    y.sotinchi = 50+rnd.nextInt(100);
                    root = insert(root, y);
                
            }
            view.paintTree();
            //cay da ve ->> set true
            treePainted = true;
    }


    ///////////////////////////////////////////       AVL code			/////////////////////////////////////////////

    public int height(Node x) {
        if (x == null)
            return 0;
        return x.height;
    }
    public int height() {
        return height(root);
    }
    
    private int checkBalance(Node x) {
        return height(x.left) - height(x.right);
    }
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }
    private Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right)); 
        return y;
    }
    private Node balance(Node x) {
        if (checkBalance(x) < -1) {
            if (checkBalance(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (checkBalance(x) > 1) {
            if (checkBalance(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }
    
    public Node insert(Node x, student y) {
        if (x == null)
            x = new Node(y);
        else {
            if (y.id < x.stu.id)
                x.left = insert(x.left, y);
            else if (y.id > x.stu.id)
                x.right = insert(x.right, y);
            else
                x.stu = y;
            
        }
        return x;
    }
    private Node deleteMin(Node x) {
    	if (x.left == null)
			return x.right;
		x.left = deleteMin(x.left);	
    	return x;
    }
    public void deleteMin(){
    	root=deleteMin(root);
    }

    public Node max(Node node) {
    	drawYellowNode(node);
    	if (node.right == null) {  		
    		drawGreenNode(node);
            return node;
    	}			
    	else 
    		return max(node.right);    
    }
    public Node min(Node node) {
    	drawYellowNode(node);
    	if (node.left == null) {
    		drawGreenNode(node);
    		return node;
    	}
    	else 
    		return min(node.left);
    }
    	//////////////////////////////////////////		end BST code		/////////////////////////////////////////////////
    private int[] timToaDo(int x) {
        //Search for the coordinates of the passed value
        //used to find the node coordinates for passed data
        for (int i = 0; i < toaDo.length; i++) {
            if (x == toaDo[i][2]) {
                int[] temp = {
                    toaDo[i][0],
                    toaDo[i][1]
                };
                return temp;
            }
        }
        return new int[2];
    }
    private void drawYellowNode(Node node) {
    	Graphics g = getGraphics();
    	try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AST.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    	int[] temp = timToaDo(node.stu.id);
        if (temp[0] != 0 && temp[1] != 0) {
        	//g.fillOval(temp[0] + 3, temp[1] + 26, 30, 30);
            //g.drawString(node.stu.id + "",temp[0] + 10, temp[1] + 45);
            g.setColor(Color.yellow);
            g.fillOval(temp[0] + 3, temp[1] + 26, 40, 40);
            g.setColor(Color.black);   
            g.drawString(node.stu.id + "",temp[0] + 13, temp[1] + 50);
        }
    }
    private void drawGreenNode(Node node) {
    	Graphics g = getGraphics();
    	try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AST.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    	int[] temp = timToaDo(node.stu.id);
        if (temp[0] != 0 && temp[1] != 0) {
        	g.setColor(Color.green);
            g.fillOval(temp[0] + 3, temp[1] + 26, 40, 40);
            g.setColor(Color.black);   
            g.drawString(node.stu.id + "",temp[0] + 13, temp[1] + 50);
        }
    }

    class Tree extends JPanel {
        private int circleRadius = 20; //ban kinh Node
        private int khoangCachDoc = 120; //khoang cach giua 2 dinh tren, duoi

        protected void paintTree() {
            Graphics g = getGraphics();
            if (root != null) {
            	g.setColor(Color.white);
            	g.fillRect(30,30,getWidth(),getWidth());
            	//view.repaint();
                displayTree(g, root, getWidth() / 2, 40, getWidth() / 4);
            }
        }
    private void displayTree(Graphics g, Node node, int x, int y, int khoangCachNgang) {
            //ve node       
            g.setColor(Color.CYAN);
            g.fillOval(x - circleRadius, y - circleRadius, 2 * circleRadius, 2 * circleRadius);

            //luu lai toa do cua Node vua ve
            toaDo[counter][0] = x - circleRadius;
            toaDo[counter][1] = y - circleRadius;
            toaDo[counter][2] = node.stu.id;
            counter++;

            //ghi ra Data cua Node(mssv)
            g.setColor(Color.black);
            g.drawString(node.stu.id + "", x - 10, y + 4);

            if (node.left != null) {
                //ve Line ben trai'
                if (!treePainted) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AST.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //ve line
                drawLineBetween2Circles(g, x - khoangCachNgang, y + khoangCachDoc, x, y);

                //ve node moi-ben trai
                displayTree(g, node.left, x - khoangCachNgang, y + khoangCachDoc, khoangCachNgang / 2);
            }
            if (node.right != null) {
                //ve Line ben phai
                if (!treePainted) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AST.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //ve line
                drawLineBetween2Circles(g, x + khoangCachNgang, y + khoangCachDoc, x, y);
                displayTree(g, node.right, x + khoangCachNgang, y + khoangCachDoc, khoangCachNgang / 2);
            }
        }

        private void drawLineBetween2Circles(Graphics g, int x1, int y1, int x2, int y2) {
            //ham ve Line giua 2 diem 
            //tim 2 toa do moi
            double d = Math.sqrt(khoangCachDoc * khoangCachDoc + (x2 - x1) * (x2 - x1));
            int xAdjusted = (int)(x1 - circleRadius * (x1 - x2) / d);
            int yAdjusted = (int)(y1 - circleRadius * (y1 - y2) / d);
            int xAdjusted1 = (int)(x2 + circleRadius * (x1 - x2) / d);
            int yAdjusted1 = (int)(y2 + circleRadius * (y1 - y2) / d);
            // ve Line voi 2 toa do moi
            g.drawLine(xAdjusted, yAdjusted, xAdjusted1, yAdjusted1);
        }
    }
}