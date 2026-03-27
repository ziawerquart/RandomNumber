package com.example.randomnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupGenerateSection();
        setupPickSection();
        setupShuffleSection();
    }

    private void setupGenerateSection() {
        EditText editMin = findViewById(R.id.editMin);
        EditText editMax = findViewById(R.id.editMax);
        EditText editCount = findViewById(R.id.editCount);
        TextView textGenerateResult = findViewById(R.id.textGenerateResult);
        Button btnGenerate = findViewById(R.id.btnGenerate);

        btnGenerate.setOnClickListener(v -> {
            Integer min = parseInteger(editMin.getText().toString());
            Integer max = parseInteger(editMax.getText().toString());
            Integer count = parseInteger(editCount.getText().toString());

            if (min == null || max == null || count == null) {
                showToast("请完整输入最小值、最大值和生成个数");
                return;
            }
            if (min > max) {
                showToast("最小值不能大于最大值");
                return;
            }
            if (count <= 0) {
                showToast("生成个数必须大于 0");
                return;
            }

            List<Integer> results = new ArrayList<>();
            int bound = max - min + 1;
            for (int i = 0; i < count; i++) {
                results.add(random.nextInt(bound) + min);
            }

            textGenerateResult.setText(String.format(Locale.getDefault(), "共生成 %d 个：%s", count, results));
        });
    }

    private void setupPickSection() {
        EditText editPickItems = findViewById(R.id.editPickItems);
        EditText editPickCount = findViewById(R.id.editPickCount);
        TextView textPickResult = findViewById(R.id.textPickResult);
        Button btnPick = findViewById(R.id.btnPick);

        btnPick.setOnClickListener(v -> {
            List<String> items = parseItems(editPickItems.getText().toString());
            Integer pickCount = parseInteger(editPickCount.getText().toString());

            if (items.size() < 2) {
                showToast("请至少输入两个项目");
                return;
            }
            if (pickCount == null || pickCount <= 0) {
                showToast("抽取数量必须大于 0");
                return;
            }
            if (pickCount > items.size()) {
                showToast("抽取数量不能大于项目总数");
                return;
            }

            List<String> shuffled = new ArrayList<>(items);
            Collections.shuffle(shuffled, random);
            List<String> picked = shuffled.subList(0, pickCount);
            textPickResult.setText(String.format(Locale.getDefault(), "抽取结果（%d/%d）：%s", pickCount, items.size(), picked));
        });
    }

    private void setupShuffleSection() {
        EditText editShuffleItems = findViewById(R.id.editShuffleItems);
        TextView textShuffleResult = findViewById(R.id.textShuffleResult);
        Button btnShuffle = findViewById(R.id.btnShuffle);

        btnShuffle.setOnClickListener(v -> {
            List<String> items = parseItems(editShuffleItems.getText().toString());
            if (items.size() < 2) {
                showToast("请至少输入两个项目");
                return;
            }

            Collections.shuffle(items, random);
            textShuffleResult.setText("打乱后顺序：" + items);
        });
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private List<String> parseItems(String rawText) {
        List<String> items = new ArrayList<>();
        String[] lines = rawText.split("\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                items.add(trimmed);
            }
        }
        return items;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
