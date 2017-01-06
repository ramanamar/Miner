import javax.swing.*;
import java.awt.*;

/**
 * Created by Raman on 06/01/2017.
 */
public class Window extends JFrame{
    public Window() {
        setBounds(500, 300, 508, 527);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Miner");

        GameMap gm = new GameMap();
        add(gm, BorderLayout.CENTER);

        setVisible(true);
        System.out.println(gm.getWidth()+ "/"+ gm.getHeight());
    }
}
