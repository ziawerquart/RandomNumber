package com.example.randomnumber;

import android.os.Bundle;
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

public class ShuffleFragment extends Fragment {

    private EditText editShuffleItems;
    private TextView textShuffleResult;
    private boolean hasShuffledResult;

    public ShuffleFragment() {
        super(R.layout.fragment_shuffle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shuffle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editShuffleItems = view.findViewById(R.id.editShuffleItems);
        textShuffleResult = view.findViewById(R.id.textShuffleResult);
        MaterialButton btnShuffle = view.findViewById(R.id.btnShuffle);

        btnShuffle.setOnClickListener(v -> {
            List<String> items = parseItems(editShuffleItems.getText().toString());
            if (items.size() < 2) {
                showToast("请至少输入两个项目");
                return;
            }

            Collections.shuffle(items);
            StringBuilder output = new StringBuilder();
            output.append("随机顺序如下：\n\n");
            for (int i = 0; i < items.size(); i++) {
                output.append(i + 1).append(". ").append(items.get(i));
                if (i != items.size() - 1) {
                    output.append("\n");
                }
            }
            textShuffleResult.setText(output.toString());
            hasShuffledResult = true;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hasShuffledResult) {
            clearInputs();
            hasShuffledResult = false;
        }
    }

    private void clearInputs() {
        if (editShuffleItems != null) {
            editShuffleItems.setText("");
        }
        if (textShuffleResult != null) {
            textShuffleResult.setText("请先输入项目并执行打乱");
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
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
