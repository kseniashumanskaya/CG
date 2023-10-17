import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HSLSwatch extends JPanel implements Observer {
    JSlider HueComponent;
    JSlider SaturationComponent;
    JSlider LightnessComponent;

    JTextField hue;
    JTextField saturation;
    JTextField lightness;

    ColorPanel ownColorPanel;
    Color ownColor;


    static final int MAX_VALUE = 100;
    static final int MIN_VALUE = 100;

    public HSLSwatch(Color color, ColorPanel colorPanel) {
        super();
        ownColor = color;
        ownColorPanel = colorPanel;

        HueComponent = new JSlider(JSlider.HORIZONTAL, 360, 0);
        SaturationComponent = new JSlider(JSlider.HORIZONTAL, MAX_VALUE, MIN_VALUE);
        LightnessComponent = new JSlider(JSlider.HORIZONTAL, MAX_VALUE, MIN_VALUE);

        hue = new JTextField(3);
        saturation = new JTextField(3);
        lightness = new JTextField(3);
        update(color);


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel row0 = new JPanel();
        row0.setLayout(new BoxLayout(row0, BoxLayout.X_AXIS));
        JLabel name = new JLabel("HSL:");
        row0.add(name);
        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        JLabel hue_label = new JLabel("Hue");
        row1.add(hue_label);
        row1.add(hue);
        row1.add(HueComponent);
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        JLabel saturation_label = new JLabel("Saturation");
        row2.add(saturation_label);
        row2.add(saturation);
        row2.add(SaturationComponent);
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        JLabel value_label = new JLabel("Lightness");
        row3.add(value_label);
        row3.add(lightness);
        row3.add(LightnessComponent);
        add(row0);
        add(row1);
        add(row2);
        add(row3);

        HueComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hue.setText("" + HueComponent.getValue());

                update_color();

            }
        });
        SaturationComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                saturation.setText("" + SaturationComponent.getValue());
                update_color();

            }
        });

        LightnessComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lightness.setText("" + LightnessComponent.getValue());
                update_color();

            }
        });

        hue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HueComponent.setValue(Integer.parseInt(hue.getText()));
                update_color();

            }
        });

        saturation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaturationComponent.setValue(Integer.parseInt(saturation.getText()));
                update_color();

            }
        });

        lightness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LightnessComponent.setValue(Integer.parseInt(lightness.getText()));
                update_color();

            }
        });


    }

    @Override
    public void update(Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        // Вычисление значений HSL
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        float h, s, l;

        l = (max + min) / 2; // Вычисление значения яркости

        if (max == min) {
            h = 0; // Если все значения RGB одинаковые, оттенок равен 0
            s = 0; // Если все значения RGB одинаковые, насыщенность равна 0
        } else {
            float d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min); // Вычисление значения насыщенности

            if (max == r) {
                h = (g - b) / d + (g < b ? 6 : 0);
            } else if (max == g) {
                h = (b - r) / d + 2;
            } else {
                h = (r - g) / d + 4;
            }

            h *= 60; // Перевод значения оттенка в градусы
        }

        // Приведение значений HSL в диапазон от 0 до 100
        int hueValue = (int) Math.round(h);
        int saturationValue = (int) Math.round(s * 100);
        int lightnessValue = (int) Math.round(l * 100);

        hue.setText(Integer.toString(hueValue));
        saturation.setText(Integer.toString(saturationValue));
        lightness.setText(Integer.toString(lightnessValue));

        HueComponent.setValue(hueValue);
        SaturationComponent.setValue(saturationValue);
        LightnessComponent.setValue(lightnessValue);
    }

    private void update_color() {
        int h = HueComponent.getValue();
        int s = SaturationComponent.getValue();
        int l = LightnessComponent.getValue();

        // Приведение значений HSL в диапазон от 0 до 1
        float hueValue = h / 360f;
        float saturationValue = s / 100f;
        float lightnessValue = l / 100f;

        if (saturationValue == 0) {
            // Если насыщенность равна 0, то все значения RGB равны значению яркости
            int rgbValue = Math.round(lightnessValue * 255);
            ownColorPanel.removeObserver(this);
            ownColor = new Color(rgbValue, rgbValue, rgbValue);
        } else {
            float q = lightnessValue < 0.5f ? lightnessValue * (1 + saturationValue) : lightnessValue + saturationValue - lightnessValue * saturationValue;
            float p = 2 * lightnessValue - q;
            float[] rgb = new float[3];
            rgb[0] = hueToRGB(p, q, hueValue + 1f / 3f);
            rgb[1] = hueToRGB(p, q, hueValue);
            rgb[2] = hueToRGB(p, q, hueValue - 1f / 3f);

            // Приведение значений RGB в диапазон от 0 до 255
            int red = Math.round(rgb[0] * 255);
            int green = Math.round(rgb[1] * 255);
            int blue = Math.round(rgb[2] * 255);

            ownColorPanel.removeObserver(this);
            ownColor = new Color(red, green, blue);
        }

        ownColorPanel.setColor(ownColor);
        ownColorPanel.repaint();
        ownColorPanel.registerObserver(this);
    }

    private float hueToRGB(float p, float q, float t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1f / 6f) return p + (q - p) * 6f * t;
        if (t < 1f / 2f) return q;
        if (t < 2f / 3f) return p + (q - p) * (2f / 3f - t) * 6f;
        return p;
    }
}