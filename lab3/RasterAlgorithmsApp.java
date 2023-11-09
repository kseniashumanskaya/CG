import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RasterAlgorithmsApp extends JFrame {
    private JTextField x1Field, y1Field, x2Field, y2Field;
    private JButton stepByStepButton, bresenhamButton;
    private final DrawingPanel drawingPanel;
    private JTextArea resultTextArea;

    public RasterAlgorithmsApp() {
        setTitle("Raster Algorithms Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        x1Field = new JTextField(5);
        y1Field = new JTextField(5);
        x2Field = new JTextField(5);
        y2Field = new JTextField(5);

        stepByStepButton = new JButton("Step-by-Step Algorithm");
        bresenhamButton = new JButton("Bresenham Algorithm");

        drawingPanel = new DrawingPanel();
        resultTextArea = new JTextArea(10, 20);
        resultTextArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Point 1:"));
        inputPanel.add(new JLabel("x1:"));
        inputPanel.add(x1Field);
        inputPanel.add(new JLabel("y1:"));
        inputPanel.add(y1Field);

        inputPanel.add(new JLabel("Point 2:"));
        inputPanel.add(new JLabel("x2:"));
        inputPanel.add(x2Field);
        inputPanel.add(new JLabel("y2:"));
        inputPanel.add(y2Field);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(stepByStepButton);
        buttonPanel.add(bresenhamButton);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(inputPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.add(drawingPanel, BorderLayout.CENTER);
        contentPanel.add(resultTextArea, BorderLayout.EAST);

        add(contentPanel);

        stepByStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x1 = Integer.parseInt(x1Field.getText());
                int y1 = Integer.parseInt(y1Field.getText());
                int x2 = Integer.parseInt(x2Field.getText());
                int y2 = Integer.parseInt(y2Field.getText());


                long startTime = System.nanoTime();
                List<Point> points = stepByStepLineAlgorithm(x1, y1, x2, y2);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                drawingPanel.setPoints(points);
                drawingPanel.repaint();
                showResult(points, duration);
            }
        });

        bresenhamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x1 = Integer.parseInt(x1Field.getText());
                int y1 = Integer.parseInt(y1Field.getText());
                int x2 = Integer.parseInt(x2Field.getText());
                int y2 = Integer.parseInt(y2Field.getText());

                long startTime = System.nanoTime();
                List<Point> points = bresenhamLineAlgorithm(x1, y1, x2, y2);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                drawingPanel.setPoints(points);
                drawingPanel.repaint();
                showResult(points, duration);
            }
        });
    }

    private List<Point> stepByStepLineAlgorithm(int x1, int y1, int x2, int y2) {
        List<Point> points = new ArrayList<>();
        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            points.add(new Point(Math.round(x), Math.round(y)));
            x += xIncrement;
            y += yIncrement;
            drawingPanel.repaint();
        }
        return points;
    }

    private List<Point> bresenhamLineAlgorithm(int x1, int y1, int x2, int y2) {
        List<Point> points = new ArrayList<>();
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            points.add(new Point(x1, y1));
            int err2 = 2 * err;
            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        points.add(new Point(x2, y2));
        drawingPanel.repaint();
        return points;
    }

    private void showResult(List<Point> points, long duration) {
        StringBuilder sb = new StringBuilder();
        sb.append("Time: ").append(duration).append(" nanoseconds\n");
        for (Point point : points) {
            sb.append("(").append(point.x).append(", ").append(point.y).append(")\n");
        }
        resultTextArea.setText(sb.toString());
    }

    private class DrawingPanel extends JPanel {
        private List<Point> points;
        private Graphics graphics;

        public void setPoints(List<Point> points) {
            this.points = points;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            graphics = g;
            // Отрисовка системы координат, осей и сетки
            int width = getWidth();
            int height = getHeight();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            g.setColor(Color.BLACK);
            g.drawLine(0, height / 2, width, height / 2);
            g.drawLine(width / 2, 0, width / 2, height);

            int gridSize = 10;
            int halfGridSize = gridSize / 2;
            for (int i = 0; i < width / 2; i += gridSize) {
                g.drawLine(width / 2 + i, height / 2 - halfGridSize, width / 2 + i, height / 2 + halfGridSize);
            }
            for (int i = 0; i < height / 2; i += gridSize) {
                g.drawLine(width / 2 - halfGridSize, height / 2 + i, width / 2 + halfGridSize, height / 2 + i);
            }
            for (int i = 0; i > -width / 2; i -= gridSize) {
                g.drawLine(width / 2 + i, height / 2 - halfGridSize, width / 2 + i, height / 2 + halfGridSize);
            }
            for (int i = 0; i >- height / 2; i -= gridSize) {
                g.drawLine(width / 2 - halfGridSize, height / 2 + i, width / 2 + halfGridSize, height / 2 + i);
            }
            g.drawString("0", width / 2 - 3, height / 2 + 10);
            g.drawString("1", width / 2 + 9, height / 2 + 10);
            g.drawString("x", width - 15, height / 2 + 10);
            g.drawString("y", width / 2 + 9 , 10);
            if (points != null) {
                g.setColor(Color.RED);
//                for (Point point : points) {
//                    int x = width / 2 + point.x * gridSize;
//                    int y = height / 2 - point.y * gridSize - gridSize;
//                    g.fillRect(x, y, gridSize, gridSize);
//                }
                g.setColor(Color.RED);
                for (int i = 0; i < points.size() - 1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);
                    if (p2.y < p1.y){
                        if(p2.x < p1.x){
                            int x = width / 2 + p1.x * gridSize   - gridSize;
                            int y = height / 2 - p1.y * gridSize;
                            g.fillRect(x, y, gridSize, gridSize);
                        }
                        else{
                            int x = width / 2 + p1.x * gridSize;
                            int y = height / 2 - p1.y * gridSize;
                            g.fillRect(x, y, gridSize, gridSize);
                        }
                    }
                    else{
                        if(p2.x < p1.x){
                            int x = width / 2 + p1.x * gridSize - gridSize;
                            int y = height / 2 - p1.y * gridSize - gridSize;
                            g.fillRect(x, y, gridSize, gridSize);
                        }
                        else{
                            int x = width / 2 + p1.x * gridSize;
                            int y = height / 2 - p1.y * gridSize  - gridSize;
                            g.fillRect(x, y, gridSize, gridSize);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RasterAlgorithmsApp app = new RasterAlgorithmsApp();
                app.setVisible(true);
            }
        });
    }
}