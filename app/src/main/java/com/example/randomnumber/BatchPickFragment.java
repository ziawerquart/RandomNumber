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
import java.util.Collections;
import java.util.List;

public class BatchPickFragment extends Fragment {

    private EditText editPickItems;
    private EditText editPickCount;
    private TextView textPickResult;
    private boolean hasPickedResult;

    public BatchPickFragment() {
        super(R.layout.fragment_batch_pick);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_batch_pick, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editPickItems = view.findViewById(R.id.editPickItems);
        editPickCount = view.findViewById(R.id.editPickCount);
        textPickResult = view.findViewById(R.id.textPickResult);
        MaterialButton btnPick = view.findViewById(R.id.btnPick);

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
            Collections.shuffle(shuffled);
            List<String> picked = shuffled.subList(0, pickCount);

            StringBuilder output = new StringBuilder();
            output.append("共 ").append(items.size()).append(" 个项目，抽取 ").append(pickCount).append(" 个\n\n");
            for (int i = 0; i < picked.size(); i++) {
                output.append(i + 1).append(". ").append(picked.get(i));
                if (i != picked.size() - 1) {
                    output.append("\n");
                }
            }
            textPickResult.setText(output.toString());
            hasPickedResult = true;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hasPickedResult) {
            clearInputs();
            hasPickedResult = false;
        }
    }

    private void clearInputs() {
        if (editPickItems != null) {
            editPickItems.setText("");
        }
        if (editPickCount != null) {
            editPickCount.setText("");
        }
        if (textPickResult != null) {
            textPickResult.setText("请先输入项目并开始抽取");
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
