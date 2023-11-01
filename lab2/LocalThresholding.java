import java.awt.image.BufferedImage;
import java.awt.Color;

public class LocalThresholding {

    public static BufferedImage bernsenThresholding(BufferedImage image, int windowSize, int contrastThreshold) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        BufferedImage grayImage = convertToGrayscale(image);

        int halfWindowSize = windowSize / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int minX = Math.max(0, x - halfWindowSize);
                int maxX = Math.min(width - 1, x + halfWindowSize);
                int minY = Math.max(0, y - halfWindowSize);
                int maxY = Math.min(height - 1, y + halfWindowSize);
                int minPixelValue = 255;
                int maxPixelValue = 0;
                for (int j = minY; j <= maxY; j++) {
                    for (int i = minX; i <= maxX; i++) {
                        Color color = new Color(grayImage.getRGB(i, j));
                        int pixelValue = color.getRed();
                        minPixelValue = Math.min(minPixelValue, pixelValue);
                        maxPixelValue = Math.max(maxPixelValue, pixelValue);
                    }
                }
                int threshold = (minPixelValue + maxPixelValue) / 2;
                Color color = new Color(grayImage.getRGB(x, y));
                int pixelValue = color.getRed();
                int newPixelValue;
                if (maxPixelValue - minPixelValue > contrastThreshold) {
                    newPixelValue = pixelValue > threshold ? 255 : 0;
                } else {
                    newPixelValue = 0;
                }
                Color newColor = new Color(newPixelValue, newPixelValue, newPixelValue);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

        return result;
    }

    public static BufferedImage niblackThresholding(BufferedImage image, int windowSize, double k) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage grayImage = convertToGrayscale(image);
        int halfWindowSize = windowSize / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int minX = Math.max(0, x - halfWindowSize);
                int maxX = Math.min(width - 1, x + halfWindowSize);
                int minY = Math.max(0, y - halfWindowSize);
                int maxY = Math.min(height - 1, y + halfWindowSize);

                double sum = 0.0;
                double sumSquared = 0.0;
                int count = 0;
                for (int j = minY; j <= maxY; j++) {
                    for (int i = minX; i <= maxX; i++) {
                        Color color = new Color(grayImage.getRGB(i, j));
                        int pixelValue = color.getRed();
                        sum += pixelValue;
                        sumSquared += pixelValue * pixelValue;
                        count++;
                    }
                }
                double mean = sum / count;
                double variance = (sumSquared / count) - (mean * mean);
                double threshold = mean + (k * Math.sqrt(variance));

                Color color = new Color(grayImage.getRGB(x, y));
                int pixelValue = color.getRed();
                int newPixelValue = pixelValue > threshold ? 255 : 0;

                Color newColor = new Color(newPixelValue, newPixelValue, newPixelValue);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
}

    private static BufferedImage convertToGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                grayImage.setRGB(x, y, rgb);
            }
        }
        return grayImage;
    }
}