package com.example.lr_11;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInBackground();
            }
        });
    }

    private void calculateInBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                double[] array = {1.0, 0.0, -2.5, 3.4, 0.0, 5.6, -2.5, 7.8};

                // Количество элементов массива, равных нулю
                final int[] countZeroes = {0};
                for (double value : array) {
                    if (value == 0.0) {
                        countZeroes[0]++;
                    }
                }

                // Сумма элементов массива, расположенных после минимального элемента
                double minElement = Double.MAX_VALUE;
                int minIndex = -1;
                for (int i = 0; i < array.length; i++) {
                    if (array[i] < minElement) {
                        minElement = array[i];
                        minIndex = i;
                    }
                }

                final double[] sumAfterMin = {0};
                if (minIndex != -1 && minIndex < array.length - 1) {
                    for (int i = minIndex + 1; i < array.length; i++) {
                        sumAfterMin[0] += array[i];
                    }
                }

                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("Количество элементов массива, равных нулю: " + countZeroes[0] + "\nСумма элементов массива, расположенных после\n" +
                                "минимального элемента: " + sumAfterMin[0]);
                    }
                });
            }
        }).start();
    }
}