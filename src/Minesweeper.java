import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Minesweeper extends JPanel {
    JFrame frame;
    JMenuBar menuBar;
    JMenu game, icons, controls;
    JMenuItem beginner, intermediate, expert;
    ImageIcon mine, flag;
    ImageIcon [] numbers = {
            new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\one.png"),
            new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\two.png"),
            new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\three.png"),
            new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\four.png"),
            new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\five.png"),
            new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\six.png"),
            new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\seven.png"),
            new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\eight.png")
    };

    JPanel panel;
    JToggleButton[][] toggleButtons;
    int dimR = 9, dimC=9 , mineCount = 10;
    String [][]  mineLocs = new String[dimR][dimC];
    Boolean [][]  flagLocs = new Boolean[dimR][dimC];
    Boolean Click = true;
    Boolean GamePlay = true;
    public Minesweeper(){
        frame = new JFrame("Minesweeper");
        frame.add(this);
        frame.setSize(800,500);

        menuBar = new JMenuBar();
        game = new JMenu("Game");
        icons = new JMenu("Icons");
        controls = new JMenu("Controls");
        beginner = new JMenuItem("Beginner");
        intermediate = new JMenuItem("Intermediate");
        expert = new JMenuItem("Expert");
        game.add(beginner);
        game.add(intermediate);
        game.add(expert);
        menuBar.add(game);
        menuBar.add(icons);
        menuBar.add(controls);
        beginner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimC = 9;
                dimR = 9;
                mineCount = 10;
                Click = true;
                frame.remove(panel);
                reValidate();
            }
        });
        intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimC = 16;
                dimR = 16;
                mineCount = 40;
                Click = true;
                frame.remove(panel);
                reValidate();
            }
        });
        expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimC = 16;
                dimR = 20;
                mineCount = 99;
                Click = true;
                frame.remove(panel);
                reValidate();
            }
        });
        frame.add(menuBar, BorderLayout.NORTH);

        mine = new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\mine.jpg");
        flag = new ImageIcon("D:\\Akash's Stuff\\Computer Science\\Data Structures\\Minesweeper\\src\\Pictures\\flag.png");
        reValidate();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    void reValidate(){
        toggleButtons = new JToggleButton[dimR][dimC];
        mineLocs = new String[dimR][dimC];
        flagLocs = new Boolean[dimR][dimC];

        mine = new ImageIcon(mine.getImage().getScaledInstance(frame.getWidth()/dimC, frame.getHeight()/dimR, Image.SCALE_SMOOTH));
        flag = new ImageIcon(flag.getImage().getScaledInstance(frame.getWidth()/dimC, frame.getHeight()/dimR, Image.SCALE_SMOOTH));

        for(int x = 0; x<8; x++){
            numbers[x] = new ImageIcon(numbers[x].getImage().getScaledInstance(frame.getWidth()/dimC, frame.getHeight()/dimR, Image.SCALE_SMOOTH));
        }
        panel = new JPanel();
        panel.setLayout(new GridLayout(dimR, dimC));

        for(int i = 0; i<dimR; i++){
            for(int j=0; j<dimC; j++){
                final int x = i;
                final int y = j;
                toggleButtons[x][y] = new JToggleButton();
                toggleButtons[x][y].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(e.getButton() == MouseEvent.BUTTON1) {
                            if(Click){
                                setBoard(x, y);
                            }
                            if(!GamePlay){
                                toggleButtons[x][y].setSelected(true);
                            }
                            if (mineLocs[x][y].equals("m") && GamePlay) {
                                toggleButtons[x][y].setIcon(mine);
                                toggleButtons[x][y].setSelected(true);
                                showEveryting();
                                GamePlay = false;
                            }
                            try {
                                if(GamePlay) {
                                    int num = Integer.parseInt(mineLocs[x][y]);
                                    toggleButtons[x][y].setIcon(numbers[num]);
                                    toggleButtons[x][y].setSelected(true);
                                }
                            }catch (NumberFormatException n){}



                        }
                        if(e.getButton() == MouseEvent.BUTTON3 && GamePlay){
                            if(!flagLocs[x][y]) {
                                toggleButtons[x][y].setIcon(flag);
                                toggleButtons[x][y].setSelected(true);
                                flagLocs[x][y] = true;
                            }else {
                                toggleButtons[x][y].setIcon(null);
                                toggleButtons[x][y].setSelected(false);
                                flagLocs[x][y] = false;
                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
                panel.add(toggleButtons[x][y]);
                frame.revalidate();
            }
        }
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
    }


    void setBoard(int h, int v){
        Click = false;
        for(int x = 0; x<dimR; x++) {
            for (int y = 0; y < dimC; y++) {
                mineLocs[x][y] = "";
                flagLocs[x][y] = false;
            }
        }
        mineLocs[h-1][v-1] = "-";
        mineLocs[h][v-1] = "-";
        mineLocs[h+1][v-1] = "-";
        mineLocs[h-1][v] = "-";
        mineLocs[h][v] = "-";
        mineLocs[h+1][v] = "-";
        mineLocs[h-1][v+1] = "-";
        mineLocs[h][v+1] = "-";
        mineLocs[h+1][v+1] = "-";
        int count = 0;
        while (count<mineCount){
            int y = (int)(Math.random()*dimC);
            int x = (int)(Math.random()*dimR);
            if(!mineLocs[x][y].equals("m") && !mineLocs[x][y].equals("-") ) {
                mineLocs[x][y] = "m";
                count++;
            }
        }

        for(int x = 0; x<dimR; x++) {
            for (int y = 0; y < dimC; y++) {
                int num = 0;
                if (!mineLocs[x][y].equals("m")) {
                    try {
                        if (mineLocs[x - 1][y - 1].equals("m")) {
                            num++;
                        }
                    } catch (Exception a) {
                    }
                    try {
                        if (mineLocs[x][y - 1].equals("m")) {
                            num++;
                        }
                    } catch (Exception a) {
                    }
                    try {
                        if (mineLocs[x + 1][y - 1].equals("m")) {
                            num++;
                        }
                    } catch (Exception a) {
                    }
                    try {
                        if (mineLocs[x - 1][y].equals("m")) {
                            num++;
                        }
                    } catch (Exception a) {
                    }
                    try {
                        if (mineLocs[x + 1][y].equals("m")) {
                            num++;
                        }
                    } catch (Exception a) {
                    }
                    try {
                        if (mineLocs[x - 1][y + 1].equals("m")) {
                            num++;
                        }
                    } catch (Exception a) {
                    }
                    try {
                        if (mineLocs[x][y + 1].equals("m")) {
                            num++;
                        }
                    } catch (Exception a) {
                    }
                    try {
                        if (mineLocs[x + 1][y + 1].equals("m")) {
                            num++;
                        }
                    } catch (Exception a) {
                    }
                    if (num != 0) {
                        mineLocs[x][y] = Integer.toString(num-1);
                    }
                }
            }
        }

        frame.revalidate();
    }

    void showEveryting(){
        for(int x = 0; x<dimR; x++) {
            for (int y = 0; y < dimC; y++) {
                if(mineLocs[x][y].equals("m")) {
                    toggleButtons[x][y].setIcon(mine);
                    toggleButtons[x][y].setSelected(true);
                }
            }
        }
    }

    void expand(int h,int v){

    }

    
    public static void main(String [] args){
        Minesweeper app = new Minesweeper();

    }
}
