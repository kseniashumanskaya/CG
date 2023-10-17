import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CMYKSwatch extends JPanel implements Observer {
    ColorPanel colorArea;
    static final int MAX_VALUE = 100;
    static final int MIN_VALUE = 100;
    Color own_color;
    JSlider CyanComponent;
    JSlider MagentaComponent;
    JSlider YellowComponent;
    JSlider KeyComponent;

    JTextField cyan_value;
    JTextField magenta_value;
    JTextField yellow_value;
    JTextField key_value;


    public CMYKSwatch(Color color, ColorPanel colorpanel) {
        super();

        own_color = color;
        colorArea = colorpanel;
        double R = color.getRed();
        double B = color.getBlue();
        double G = color.getGreen();
        double K = Math.min(1 - R/255, Math.min(1 - G/ 255, 1 - B/255)) * 100;
        double C = (1 - R/255 - K/100) / (1 - K/100)  * 100;
        double M = (1 - G/255 - K/100) / (1 - K/100) * 100;
        double Y = (1 - B/255 - K/100) / (1 - K/100) * 100;

        CyanComponent = new JSlider(JSlider.HORIZONTAL,MAX_VALUE, MIN_VALUE);
        cyan_value = new JTextField(3);
        CyanComponent.setValue((int)C);
        cyan_value.setText("" +(int) C);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        MagentaComponent = new JSlider(JSlider.HORIZONTAL, MAX_VALUE, MIN_VALUE);
        magenta_value = new JTextField(3);
        MagentaComponent.setValue((int)M);
        magenta_value.setText("" + (int)M);

        YellowComponent = new JSlider(JSlider.HORIZONTAL,MAX_VALUE, MIN_VALUE);
        yellow_value = new JTextField(3);
        yellow_value.setText("" + (int)Y);
        YellowComponent.setValue((int)Y);

        KeyComponent = new JSlider(JSlider.HORIZONTAL,MAX_VALUE, MIN_VALUE);
        key_value = new JTextField(3);
        key_value.setText("" + (int)K);
        KeyComponent.setValue((int)K);
        JPanel row0 = new JPanel();
        row0.setLayout(new BoxLayout(row0, BoxLayout.X_AXIS));
        JLabel name = new JLabel("SMYK:");
        row0.add(name);


        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        JLabel cyan_label = new JLabel("Cyan");
        row1.add(cyan_label);
        row1.add(cyan_value);
        row1.add(CyanComponent);
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        JLabel magenta_label = new JLabel("Magenta");
        row2.add(magenta_label);
        row2.add(magenta_value);
        row2.add(MagentaComponent);
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        JLabel yellow_label = new JLabel("Yellow");
        row3.add(yellow_label);
        row3.add(yellow_value);
        row3.add(YellowComponent);
        JPanel row4 = new JPanel();
        JLabel key_label = new JLabel("Key");
        row4.add(key_label);
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
        row4.add(key_value);
        row4.add(KeyComponent);
        add(row0);
        add(row1);
        add(row2);
        add(row3);
        add(row4);


        CyanComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cyan_value.setText("" + CyanComponent.getValue());

                update_color();
            }
        });

        MagentaComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                magenta_value.setText("" + MagentaComponent.getValue());
                update_color();
            }
        });

        YellowComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                yellow_value.setText("" + YellowComponent.getValue());
                update_color();
            }
        });
        KeyComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                key_value.setText("" + KeyComponent.getValue());
                update_color();
            }
        });

        cyan_value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CyanComponent.setValue(Integer.parseInt(cyan_value.getText()));
                update_color();
            }
        });

        magenta_value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MagentaComponent.setValue(Integer.parseInt(magenta_value.getText()));
                update_color();
            }
        });

        yellow_value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                YellowComponent.setValue(Integer.parseInt(yellow_value.getText()));
                update_color();
            }
        });
        key_value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyComponent.setValue(Integer.parseInt(key_value.getText()));
                update_color();
            }
        });
    }

    @Override
    public void update(Color color) {
        own_color = color;
        double R = color.getRed();
        double B = color.getBlue();
        double G = color.getGreen();
        double K =Math.min(1 - R/255, Math.min(1 - G/ 255, 1 - B/255)) * 100;
        double C = ((1 - R/255 - K/100) / (1 - K/100)) * 100;
        double M = ((1 - G/255 - K/100) / (1 - K/100)) * 100;
        double Y = ((1 - B/255 -K/100) / (1 - K/100)) * 100;
        colorArea.removeObserver(this);
        CyanComponent.setValue((int)C);
        cyan_value.setText("" + (int)C);

        MagentaComponent.setValue((int)M);
        magenta_value.setText("" + (int)M);

        YellowComponent.setValue((int)Y);
        yellow_value.setText("" + (int)Y);

        KeyComponent.setValue((int)K);
        key_value.setText("" + (int)K);
        colorArea.registerObserver(this);


    }

    private void update_color () {
        double C = CyanComponent.getValue();
        double M = MagentaComponent.getValue();
        double Y = YellowComponent.getValue();
        double K = KeyComponent.getValue();

        double R = (255 * (1 - C / 100) * (1 - K/100));
        double G = (255 * (1 - M/100) * (1 - K/100));
        double B =  (255 * (1 - Y/100) * (1 - K/100));

        own_color = new Color((int)R, (int)G, (int)B);
        colorArea.removeObserver(this);
        colorArea.setColor(own_color);
        colorArea.repaint();
        colorArea.registerObserver(this);
    }
}

