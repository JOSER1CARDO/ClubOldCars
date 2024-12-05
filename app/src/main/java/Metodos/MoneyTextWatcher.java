package Metodos;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyTextWatcher implements TextWatcher {
    private final EditText editText;
    private final NumberFormat numberFormat;

    public MoneyTextWatcher(EditText editText) {
        this.editText = editText;
        this.numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Não é necessário fazer nada aqui
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Não é necessário fazer nada aqui
    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);

        try {
            String originalString = s.toString();

            if (originalString.isEmpty()) {
                return;
            }

            // Remove caracteres de formatação
            String cleanString = originalString.replaceAll("[R$,.\\s]", "");

            // Parse para long e então formate para moeda
            long parsed = Long.parseLong(cleanString);
            String formatted = numberFormat.format((double) parsed / 100);

            // Atualiza o texto do EditText
            editText.setText(formatted);
            editText.setSelection(formatted.length());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        editText.addTextChangedListener(this);
    }
}

