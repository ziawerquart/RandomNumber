package com.example.randomnumber;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumberFragment extends Fragment {

    private final Random random = new Random();
    private EditText editMin;
    private EditText editMax;
    private EditText editCount;
    private TextView textGenerateResult;
    private boolean hasGeneratedResult;

    public RandomNumberFragment() {
        super(R.layout.fragment_random_number);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editMin = view.findViewById(R.id.editMin);
        editMax = view.findViewById(R.id.editMax);
        editCount = view.findViewById(R.id.editCount);
        textGenerateResult = view.findViewById(R.id.textGenerateResult);
        MaterialButton btnGenerate = view.findViewById(R.id.btnGenerate);

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

            StringBuilder output = new StringBuilder();
            output.append("已生成 ").append(count).append(" 个随机数\n\n");
            for (int i = 0; i < results.size(); i++) {
                output.append(i + 1).append(". ").append(results.get(i));
                if (i != results.size() - 1) {
                    output.append("\n");
                }
            }
            textGenerateResult.setText(output.toString());
            hasGeneratedResult = true;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hasGeneratedResult) {
            clearInputs();
            hasGeneratedResult = false;
        }
    }

    private void clearInputs() {
        if (editMin != null) {
            editMin.setText("");
        }
        if (editMax != null) {
            editMax.setText("");
        }
        if (editCount != null) {
            editCount.setText("");
        }
        if (textGenerateResult != null) {
            textGenerateResult.setText("请先输入参数并点击生成");
        }
    }

    private Integer parseInteger(String value) {
        if (TextUtils.isEmpty(value) || TextUtils.isEmpty(value.trim())) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
