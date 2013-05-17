package signalbrook.core;

/**
 * Handy methods that can be used to generate probability distributions that can then be used to generate a signal.
 * 
 * DISCLAIMER: The last math class I took was in 1999.
 */
public class Random {
    private static final double FUDGE = 1E-14;
    private static java.util.Random rand;
    
    public static final double[] DISCRETE_BIMODAL_500 = generateBimodalDistributionCoefficiences(500, -200.0d, +200.0d, +5.0d, +5.0d);
    
    static { reSeed(System.currentTimeMillis()); }
            
    public static void reSeed(long s) {
        rand = new java.util.Random(s);    
    }
    
    public static double[] genDiscreteCoefficients(int len, double mean, double stddev) {
        double[] res = new double[len];
        double sum = 0;
        for (int i = 0; i < len; i++) {
            double x = -len/2 + i;
            double y = (1 / (stddev * Math.sqrt(2 * Math.PI))) * 
                       (Math.pow(Math.E, -1d * ( Math.pow(x-mean, 2) / (2 * Math.pow(stddev, 2)))));
            sum += y;
            res[i] = y;
        }
        return res;
    }
    
    public static double[] generateBimodalDistributionCoefficiences(int len, double μa, double μb, double σa, double σb) {
        double[] a = genDiscreteCoefficients(len, μa, σa);
        double[] b = genDiscreteCoefficients(len, μb, σb);
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = (a[i] + b[i]) / 2d;
        }
        // ideally sum(c) roughly = 1.0
        return c;
    }
    
    public static int discrete(double[] a) {
        validateDiscrete(a);
        while (true) {
            double r = rand.nextDouble();
            double sum = 0d;
            for (int i = 0; i < a.length; i++) {
                sum += a[i];
                if (sum > r)
                    return i;
            }
        }
    }
    
    private static void validateDiscrete(double[] a) throws IllegalArgumentException {
        double sum = 0d;
        for (double d : a) {
            if (d < 0) throw new IllegalArgumentException("array element < 0");
            sum += d;
        }
        if (sum > 1d + FUDGE || sum < 1d - FUDGE) throw new IllegalArgumentException("0 > sum(a) > 1");
    }
}
