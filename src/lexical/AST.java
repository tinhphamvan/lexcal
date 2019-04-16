package lexical;


import java.awt.BorderLayout;
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

import java.util.ArrayList;
import java.util.Random;

public class AST extends JFrame implements ActionListener {
  
    private class Node {
        private String token;
        private Node left, right, parent;
        private int height;
        public Node(String token) {
            this.token = token;
            left = null;
            right = null;
            height = 0;
        }
    }
     
    public Node root = null; 

    int[][] toaDo = new int[500][3]; //luu toa do x,y,mssv cua 1 node
    
    int counter = 0;// bien dem cho toaDo

    boolean treePainted = false; //cay da duoc ve hay chua?

    private JButton random = new JButton("BUILD TREE");


    Font font = new Font("Verdana", Font.BOLD, 20);

    //Panel to display the tree
    private Tree view = new Tree();

    public AST() {
        //Constructor to create the GUI of the class
        setTitle("Abstact Syntax Tree");
        setBackground(Color.white);

        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        
        //Adding the tree view panel to the frame, setting border and size
        add(view);
        view.setBorder(new TitledBorder("Tree View"));
        view.setPreferredSize(new Dimension(800, 800));
        view.setVisible(true);
        view.setBackground(Color.white);

        //adding button
        
        random.setBackground(Color.LIGHT_GRAY);
        random.setBounds(500, 900, 200, 80);
        random.setLayout(null);
        random.setAlignmentX(JButton.LEFT_ALIGNMENT);
        random.addActionListener(this);
        add(random,BorderLayout.SOUTH);
        //Finishing up
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void buildAST(TreeProperty tree) {
        
        String dec = "";
        root = new Node("PROGRAM | "+tree.getProgram().tokenString);
        
        //PRINT DECLARE 
        if(tree.getDeclare() == null){
        	root.left = new Node("DummyNODE");
        }else {
        	root.left = new Node("DECLARE(var)");
            for(int i = 0; i < tree.getDeclare().size() -1; i++) {
            	dec = " "+dec + tree.getDeclare().get(i) + " ";
            }
            root.left.left = new Node(dec);
            root.left.right = new Node("  "+tree.getDeclare().get(tree.getDeclare().size() - 1));
        }
        
        
        //
        
        root.right = new Node("BLOCK");
        
        //WRITELN
        if(tree.getPrintln() == null){
        	root.right.left = new Node("DummyNODE");
        }else {
        	root.right.left = new Node("    WRITELN");
            root.right.left.left = new Node("    " + tree.getPrintln().tokenString);
            root.right.left.right = new Node(tree.getPrintln().tokenType.toString());
        }
        
        
        
        
        //Statement
        if(tree.getExpression1().size() == 0) {
        	root.right.right = new Node("DummyNODE");
        }else {
        	root.right.right = new Node("STATEMENT");
        	//expression 1
            root.right.right.left = new Node("     " + tree.getExpression1().get(1));
            root.right.right.left.left = new Node("     " + tree.getExpression1().get(0));
            if(tree.getExpression1().size() < 5) {
            	root.right.right.left.right = new Node("   " + tree.getExpression1().get(2));
            }else {
            	root.right.right.left.right = new Node("   " + tree.getExpression1().get(3));
            	root.right.right.left.right.left = new Node("   " + tree.getExpression1().get(2));
            	root.right.right.left.right.right = new Node("   " + tree.getExpression1().get(4));
            }
            //expression 2
            root.right.right.right = new Node("     " + tree.getExpression2().get(1));
            root.right.right.right.left = new Node("     " + tree.getExpression2().get(0));
            if(tree.getExpression2().size() < 5) {
            	root.right.right.right.right = new Node("   " + tree.getExpression2().get(2));
            }else {
            	root.right.right.right.right = new Node("   " + tree.getExpression2().get(3));
            	root.right.right.right.right.left = new Node("   " + tree.getExpression2().get(2));
            	root.right.right.right.right.right = new Node("   " + tree.getExpression2().get(4));
            }
        }
        
    }



	    public void actionPerformed(ActionEvent e) {
        //ActionListener 

        if (e.getSource() == random) {// 
            view.paintTree();
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
        private int circleRadius = 40; //ban kinh Node
        private int khoangCachDoc = 60; //khoang cach giua 2 dinh tren, duoi
        
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
            g.fillRect(x - circleRadius, y - circleRadius + 15, 2 * circleRadius, circleRadius);

            //luu lai toa do cua Node vua ve
            toaDo[counter][0] = x - circleRadius;
            toaDo[counter][1] = y - circleRadius;
//            toaDo[counter][2] = node.token.tokenString;
            counter++;

            //ghi ra Data cua Node(mssv)
            g.setColor(Color.black);
            g.drawString(node.token, x - circleRadius + 3 , y  );

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