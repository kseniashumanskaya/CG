import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Arrays;

public class NonlinearFilter {
    public static BufferedImage medianFilter(BufferedImage image, int maskSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        int halfMaskSize = maskSize / 2;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] redValues = new int[maskSize * maskSize];
                int[] greenValues = new int[maskSize * maskSize];
                int[] blueValues = new int[maskSize * maskSize];
                int index = 0;

                for (int j = -halfMaskSize; j <= halfMaskSize; j++) {
                    for (int i = -halfMaskSize; i <= halfMaskSize; i++) {
                        int offsetX = x + i;
                        int offsetY = y + j;

                        if (offsetX >= 0 && offsetX < width && offsetY >= 0 && offsetY < height) {
                            Color color = new Color(image.getRGB(offsetX, offsetY));
                            redValues[index] = color.getRed();
                            greenValues[index] = color.getGreen();
                            blueValues[index] = color.getBlue();
                        } else {
                            redValues[index] = 0;
                            greenValues[index] = 0;
                            blueValues[index] = 0;
                        }

                        index++;
                    }
                }

                int medianRed = getMedian(redValues);
                int medianGreen = getMedian(greenValues);
                int medianBlue = getMedian(blueValues);

                Color medianColor = new Color(medianRed, medianGreen, medianBlue);
                result.setRGB(x, y, medianColor.getRGB());
            }
        }
        return result;
    }

    private static int getMedian(int[] values) {
        Arrays.sort(values);
        int middleIndex = values.length / 2;
        return values[middleIndex];
    }
}