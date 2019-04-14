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

public class AST extends JFrame implements ActionListener {
    private class student {
        private int id;
        private String hoten;
        private String ngaysinh;
        private float diemTB;
        private int sotinchi;


        public student(int id, String hoten, String ngaysinh, float diemTB, int sotinchi) {
            this.id = id;
            this.hoten = hoten;
            this.ngaysinh = ngaysinh;
            this.diemTB = diemTB;
            this.sotinchi = sotinchi;
        }
    }
    private class Node {
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

    // khoi tao cac button, cac lable
    private JButton random = new JButton("BUILD TREE");
    
    //text fields


    //Panel contains the buttons
    private JPanel buttonPanel = new JPanel();

    Font font = new Font("Verdana", Font.BOLD, 12);

    //Panel to display the tree
    private Tree view = new Tree();

    public AST() {
        //Constructor to create the GUI of the class
        setTitle("Abstact Syntax Tree");
        setBackground(Color.white);

        setLayout(new GridLayout(1, 2));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        
        //Adding the tree view panel to the frame, setting border and size
        add(view);
        view.setBorder(new TitledBorder("Tree View"));
        view.setPreferredSize(new Dimension(1000, 1000));
        view.setVisible(true);
        view.setBackground(Color.white);

        //adding button
        random.setBackground(Color.LIGHT_GRAY);
        random.setBounds(500, 900, 200, 80);
        view.add(random);
        //Adding ActionListeners to all the buttons
        random.addActionListener(this);
        //Finishing up
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void buildAST() {
        new AST();
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

    private Node insert(Node x, student y) {
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

	    public void actionPerformed(ActionEvent e) {
        //ActionListener 

        if (e.getSource() == random) {// 
        	ho[0]="Lê";ho[1]="Trần";ho[2]="Nguyễn";ho[3] = "Đinh";
        	lot[0]="Văn";lot[1]="Thị";lot[2]="Anh";lot[3] = "Ngọc";
        	ten[0]="Long";ten[1]="Nhi";ten[2]="Việt";ten[3] = "Nam";
        	Random rnd = new Random();
        	int n = 3 + rnd.nextInt(4);
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
                    student y = new student(0, "Tên chưa cập nhật", "Ngày sinh chưa cập nhật", 0, 0);
                    y.id = arr[1];
                    y.hoten = ho[1]+" "+lot[1]+" "+ten[1];
                    y.ngaysinh = Integer.toString(1+rnd.nextInt(28))+"/"+ Integer.toString(1+rnd.nextInt(11))+"/"+ Integer.toString(2000+rnd.nextInt(5));
                    y.diemTB = rnd.nextInt(10);
                    y.sotinchi = 50+rnd.nextInt(100);
                    Node t = new Node(y);
//                    t.stu = y;
                    root.left = t;
                    Node h = new Node(x);
                    root.left.right = h;
                
            
            //ve cay len view
            view.paintTree();
            //cay da ve ->> set true
            treePainted = true;
        }

    }

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


    class Tree extends JPanel {
        private int circleRadius = 35; //ban kinh Node
        private int khoangCachDoc = 80; //khoang cach giua 2 dinh tren, duoi

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