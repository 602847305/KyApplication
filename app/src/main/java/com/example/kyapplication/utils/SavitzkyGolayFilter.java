package com.example.kyapplication.utils;

import java.util.Arrays;

/**
 * Savitzky–Golay 滤波法
 */
public class SavitzkyGolayFilter {
public static float[] savitzkyGolay(float[] input, int windowSize, int polynomialOrder) {
            if (windowSize % 2 == 0) {
                throw new IllegalArgumentException("Window size must be odd.");
            }
            if (windowSize <= polynomialOrder) {
                throw new IllegalArgumentException("Window size must be larger than polynomial order.");
            }

            int leftSize = windowSize / 2;
            int rightSize = windowSize - leftSize - 1;

            float[] output = new float[input.length];F.d("windowSize```"+windowSize);
            for (int i = 0; i < input.length; i++) {
                float[] data = new float[windowSize];

                for (int j = -leftSize; j <= rightSize; j++) {
                    int index = i + j;
                    if (index < 0) {
                        index = Math.abs(index) - 1;
                    } else if (index >= input.length) {
                        index = input.length - (index - input.length) - 1;
                    }
                    data[j + leftSize] = input[index];
                }

                float[] coefficients = calculateCoefficients(polynomialOrder);
                float sum = 0;
                F.d("coefficients..length:"+coefficients.length);
                for (int k = 0; k < windowSize; k++) {
                    sum += coefficients[k] * data[k];
                }

                output[i] = sum;
            }

            return output;
        }

        private static float[] calculateCoefficients(int polynomialOrder) {
            int m = polynomialOrder + 1;
            float[] coefficients = new float[m];

            for (int i = 0; i < m; i++) {
                coefficients[i] = calculateCoefficient(i, m);
            }

            return coefficients;
        }

        private static float calculateCoefficient(int k, int m) {
            if (k == 0 || k == m - 1) {
                return 1.0f / ((m - 1) * (m - 1));
            } else if (k % 2 == 0) {
                return -1.0f / ((m - 1) * (m - 1) * (m - 3));
            } else {
                return 0;
            }
        }

//        public static void main(String[] args) {
//            float[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//            int windowSize = 5;
//            int polynomialOrder = 2;
//
//            float[] output = savitzkyGolay(input, windowSize, polynomialOrder);
//
//            System.out.println("Input:  " + Arrays.toString(input));
//            System.out.println("Output: " + Arrays.toString(output));
//        }



}
