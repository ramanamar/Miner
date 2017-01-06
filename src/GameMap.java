import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Created by Raman on 06/01/2017.
 */
public class GameMap extends JPanel {
    private final int SIZE = 500;
    private final int FIELD_SIZE = 20;
    private final int CELL_SIZE = SIZE / FIELD_SIZE;
    private Random rand = new Random();
    private final int MINES_COUNT = 60;
    private final Font myFont = new Font("Helvetica", Font.BOLD, 24);
    private boolean gameOver;

    private int[][] minen;
    private int[][] arroundMinen;
    private boolean[][] visible;

    public GameMap() {
        setBackground(Color.lightGray);
        minen = new int[FIELD_SIZE][FIELD_SIZE];
        arroundMinen = new int[FIELD_SIZE][FIELD_SIZE];
        visible = new boolean[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < MINES_COUNT; i++) {
            int x, y;
            do {
                x = rand.nextInt(FIELD_SIZE);
                y = rand.nextInt(FIELD_SIZE);
            } while (minen[x][y] != 0);
            minen[x][y] = 1;
        }
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                arroundMinen[i][j] = getMinenCount(i, j);
            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;
                if (minen[x][y] == 1) gameOver = true;
                if (minen[x][y] == 0) {
                    if (arroundMinen[x][y] > 0) visible[x][y] = true;
                }
                repaint();
            }
        });
    }

    public int getMinenCount(int x, int y) {
        int counter = 0;
        int x1 = x - 1;
        if (x1 < 0) x1 = 0;
        int y1 = y - 1;
        if (y1 < 0) y1 = 0;
        int x2 = x + 1;
        if (x2 > FIELD_SIZE - 1) x2 = FIELD_SIZE - 1;
        int y2 = y + 1;
        if (y2 > FIELD_SIZE - 1) y2 = FIELD_SIZE - 1;
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                counter += minen[i][j];
            }
        }
        return counter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(myFont);
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {

                if (visible[i][j] && minen[i][j] == 0 && arroundMinen[i][j] > 0) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawString(String.valueOf(arroundMinen[i][j]), i * CELL_SIZE + 6, j * CELL_SIZE + 22);
                } else {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                if (gameOver && minen[i][j] == 1) {
                    g.setColor(Color.black);
                    g.fillOval(i * CELL_SIZE + 1, j * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
                }
            }
        }
        for (int i = 0; i < FIELD_SIZE; i++) {
            g.setColor(Color.black);
            g.drawLine(0, i * CELL_SIZE, SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SIZE);
        }

        g.drawRect(0, 0, SIZE - 1, SIZE - 1);
//        if (gameOver) {
//            g.setColor(Color.RED);
//            g.fillRect(0, 0, CELL_SIZE, CELL_SIZE);
//        }
    }
}
