import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RGBSwatch extends JPanel implements Observer{
    static final int MIN_VALUE = 0;
    static final int MAX_VALUE = 255;
    static Color own_color;
    static ColorPanel colorArea;
    JSlider RComponent;
    JTextField red_value;
    JSlider GComponent;
    JTextField green_value;
    JSlider BComponent;
    JTextField blue_value;

    public static void main(String[] args) {

    }



    public RGBSwatch(Color color, ColorPanel colorpanel) {
        super();
        own_color = color;
        colorArea = colorpanel;
        RComponent = new JSlider(JSlider.HORIZONTAL,MAX_VALUE, MIN_VALUE);
        red_value = new JTextField(3);
        RComponent.setValue(color.getRed());
        red_value.setText("" + color.getRed());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        GComponent = new JSlider(JSlider.HORIZONTAL, MAX_VALUE, MIN_VALUE);green_value = new JTextField(3);
        GComponent.setValue(color.getGreen());
        green_value.setText("" + color.getGreen());
        BComponent = new JSlider(JSlider.HORIZONTAL,MAX_VALUE, MIN_VALUE);
        blue_value = new JTextField(3);
        blue_value.setText("" + color.getBlue());
        BComponent.setValue(color.getBlue());
        Button change_color = new Button("change color");
        JPanel row0 = new JPanel();
        row0.setLayout(new BoxLayout(row0, BoxLayout.X_AXIS));
        JLabel name = new JLabel("RGB:");
        row0.add(change_color);
        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        row1.add(name);
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        JLabel red_label = new JLabel("Red");
        row2.add(red_label);
        row2.add(red_value);
        row2.add(RComponent);
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        JLabel green_label = new JLabel("Green");
        row3.add(green_label);
        row3.add(green_value);
        row3.add(GComponent);
        JPanel row4 = new JPanel();
        JLabel blue_label = new JLabel("Blue");
        row4.add(blue_label);
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
        row4.add(blue_value);
        row4.add(BComponent);
        add(row0);
        add(row1);
        add(row2);
        add(row3);
        add(row4);


        change_color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                own_color = JColorChooser.showDialog(null, "Select a color", own_color);
                update_color();

            }
        });

        GComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //green_value.setText("" + GComponent.getValue());
                own_color = new Color(own_color.getRed(), GComponent.getValue(), own_color.getBlue());
                //colorArea.setColor(own_color);
                update_color();
            }
        });
        RComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //red_value.setText("" + RComponent.getValue());
                own_color = new Color(RComponent.getValue(), own_color.getGreen(), own_color.getBlue());
                update_color();
            }
        });

        BComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //blue_value.setText("" + BComponent.getValue());
                own_color = new Color(own_color.getRed(), own_color.getGreen(), BComponent.getValue());
                update_color();
            }
        });

        red_value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // int value = Integer.parseInt(red_value.getText());
                //RComponent.setValue(value);
                own_color = new Color(Integer.parseInt(red_value.getText()), own_color.getGreen(), own_color.getBlue());
                update_color();

            }
        });

        green_value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(green_value.getText());
                // GComponent.setValue(value);
                own_color = new Color(own_color.getRed(), value, own_color.getBlue());
                update_color();
            }
        });

        blue_value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(blue_value.getText());
                //BComponent.setValue(value);
                own_color = new Color(own_color.getRed(), own_color.getGreen(), value);
                update_color();
            }
        });



    }

    @Override
    public void update(Color color) {
        //own_color = color;
        own_color = color;
        colorArea.removeObserver(this);
        RComponent.setValue(color.getRed());
        red_value.setText("" + color.getRed());


        GComponent.setValue(color.getGreen());
        green_value.setText("" + color.getGreen());


        blue_value.setText("" + color.getBlue());
        BComponent.setValue(color.getBlue());
        colorArea.registerObserver(this);

    }

    public void update_color() {
        colorArea.removeObserver(this);
        if(RComponent.getValue() != own_color.getRed())
            RComponent.setValue(own_color.getRed());
        if(GComponent.getValue() != own_color.getGreen())
            GComponent.setValue(own_color.getGreen());
        if(BComponent.getValue() != own_color.getBlue())
            BComponent.setValue(own_color.getBlue());
        if(Integer.parseInt(green_value.getText()) != own_color.getGreen())
            green_value.setText("" + own_color.getGreen());
        if(Integer.parseInt(red_value.getText()) != own_color.getRed())
            red_value.setText("" + own_color.getRed());
        if(Integer.parseInt(blue_value.getText()) != own_color.getBlue())
            blue_value.setText("" + own_color.getBlue());
        colorArea.setColor(own_color);
        colorArea.repaint();
        colorArea.registerObserver(this);
    }


}
