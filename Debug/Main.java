import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Objects;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            // alternatively, the following should load the default L&F for your system
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {}


        Layout l = new Layout(8, 8);
        l.drawGrid(true);
//        l.initialPosition();
        l.setSize(400, 400);
        l.setFocusable(true);// so it can listen for keyboard event
        l.setResizable((false));
        l.setLocationRelativeTo(null);

        l.setVisible(true);
    }
}

class Cords {
    private int x;
    private int y;

    public Cords(int x, int y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cords cords = (Cords) o;
        return x == cords.x && y == cords.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}

class Layout extends JFrame implements KeyListener{
    JFrame frame = new JFrame("Board Game");
    Container contents = frame.getContentPane();
    Cords origin;
    boolean win;
    ActionListener actionListener;
    JFrame messageframe = new JFrame("Message dialog box");

    private int cols;
    private int rows;
    private int x;
    private int y;

    Cords points = new Cords(x, y);

    HashSet<Cords> hs;
    int turn;
    private JButton[][] sqr;

    private Color grey = Color.lightGray;
    private Color white = Color.WHITE;
    private int xVal;
    private int yVal;

    private Color orange = Color.orange;//player 1
    private Color pink = Color.PINK;//player 2
    private Color red = Color.RED;//player 3
    ActionListener al;
    public Layout(int cols, int rows) {

        x = 0;
        y = 0;
        addKeyListener(this); //add key this which refer to key listener functions
        // keypress keytype keyrelease will be added

        this.cols = cols;
        this.rows = rows;
        win = false;
        turn = 1;
        hs = new HashSet<>();
        contents = getContentPane();//get the content pane which is the ui view content
        contents.setLayout(new GridLayout(cols, rows));// create grid layout
        sqr = new JButton[cols][rows]; //set sqr to a jbutton
        al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button clicked");
                Object source = e.getSource();
                for(int i = 0; i < cols; i++){
                    for(int j = 0; j < rows; j++){
                        if(source == sqr[i][j]){
                            setInitialPostion(i, j);
                        }
                        sqr[i][j].setFocusable(false);

                    }
                }
            }
        };

    }


    public void drawGrid(boolean firstTime){//draw the grids with buttons
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                sqr[i][j] = new JButton();
                if ((i + j) % 2 != 0) {
                    sqr[i][j].setBackground(grey);

                } else {
                    sqr[i][j].setBackground(white);
                }
				if(firstTime){
					contents.add(sqr[i][j]);
				}
                
                sqr[i][j].setFocusable(true);
                sqr[i][j].addActionListener(al);
                sqr[i][j].setBorderPainted(false);
            }
        }


    }
    public void reset(){

    }

    private void setInitialPostion(int i, int j) {
        y = j;
        x = i;
        xVal = x;
        yVal = y;
        System.out.println(x + " " + " " + y);
        origin = new Cords(x, y);
        hs.add(origin);
        hs.add(new Cords(x, y));
        sqr[x][y].setBackground(orange);
    }

//    public void initialPosition() {// starting position
//        x = 4;
//        y = 4;
//        origin = new Cords(4, 4);
//        hs.add(origin);
//        hs.add(new Cords(x, y));
//
//        sqr[x][y].setBackground(orange);
//
//
//
//    }

    public void keyPressed(KeyEvent e) {//keyboard event

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            y++;

            if(!hs.contains(new Cords(x, y))){
                hs.add(new Cords(x, y));

                if(turn == 1) {
                    sqr[x][y].setBackground(orange);
                    turn = 2;
                } else if(turn == 2){
                    sqr[x][y].setBackground(pink);
                    turn = 1;
                }
                System.out.println("Right key pressed");
                check();

            }else {
                if(x == xVal && y == yVal){
                    win = true;
                    messageDialog();
                }

                setFocusable(false);

            }


        }

        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            y--;
            if(!hs.contains(new Cords(x, y))){
                hs.add(new Cords(x, y));
                if(turn == 1) {
                    sqr[x][y].setBackground(orange);
                    turn = 2;
                } else if(turn == 2){
                    sqr[x][y].setBackground(pink);
                    turn = 1;
                }
                System.out.println("Left key pressed");
                check();
                //died

            }else {
                if(x == xVal && y == yVal){
                    win = true;
                    messageDialog();
                }
                setFocusable(false);
            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP) {

            x--;

            if(!hs.contains(new Cords(x , y))){
                hs.add(new Cords(x, y));
                if(turn == 1) {
                    sqr[x][y].setBackground(orange);
                    turn = 2;
                } else if(turn == 2){
                    sqr[x][y].setBackground(pink);
                    turn = 1;
                }
                System.out.println("up key pressed");
                check();

            }else {
                if(x == xVal && y == yVal){
                    win = true;
                    messageDialog();
                }
                setFocusable(false);
            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            x++;
            if(!hs.contains(new Cords(x, y))){
                hs.add(new Cords(x, y));
                if(turn == 1) {
                    sqr[x][y].setBackground(orange);
                    turn = 2;
                } else if(turn == 2){
                    sqr[x][y].setBackground(pink);
                    turn = 1;
                }
                System.out.println("down key pressed");
                check();


            }else {
                if(x == xVal && y == yVal){

                    win = true;
                    messageDialog();

                }
                setFocusable(false);
            }
        }
    }

    private void messageDialog() {// message box check win true false
        if(win){
            JOptionPane.showMessageDialog(messageframe, "You win");

        }else{

            JOptionPane.showMessageDialog(messageframe, "You died");
        }
        drawGrid(false);
    }

    private void check() {

    }

    //needed method but dont need actual functionality
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e){
    }
}



