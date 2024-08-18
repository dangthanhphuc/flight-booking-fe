package com.example.flightbooking;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightbooking.databinding.ActivityAccountDetailBinding;

import java.text.Normalizer;
import java.util.Calendar;
import java.util.regex.Pattern;

public class ActivityAccountDetail extends AppCompatActivity {

    ActivityAccountDetailBinding binding;
    String[] titles = {"Ông", "Bà", "Cô/Chị"};
    String[] genders = {"Nam", "Nữ"};
    boolean isTextUpdating = false;
    boolean isChecked = false;
    EditText edtDateOfBirthday;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    AutoCompleteTextView autoCompleteTitle;
    AutoCompleteTextView autoCompleteGender;

    ArrayAdapter<String> titleAdapter;
    ArrayAdapter<String> genderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupSpannableText();
        setupCheckboxImage();
        setupAutoCompleteViews();
        setupDatePicker();
        setupTextWatchers();

        addEvents();
    }

    private void setupSpannableText() {
        TextView txtAgree = binding.txtAgree;
        String text1 = "Bằng việc bấm Cập nhật, Tôi đã đọc và đồng ý với ";
        String text2 = "chính sách bảo mật thông tin, quyền riêng tư";
        String text3 = " của Happy Flight";
        SpannableString spannable = new SpannableString(text1 + text2 + text3);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFCC00")),
                text1.length(), text1.length() + text2.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new UnderlineSpan(),
                text1.length(), text1.length() + text2.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtAgree.setText(spannable);
    }

    private void setupCheckboxImage() {
        ImageView checkboxImage = binding.checkboxImage;
        checkboxImage.setOnClickListener(v -> {
            if (isChecked) {
                checkboxImage.setImageResource(R.drawable.baseline_check_box_outline_blank_24);
            } else {
                checkboxImage.setImageResource(R.drawable.baseline_check_box_24);
            }
            isChecked = !isChecked;
        });
    }

    private void setupAutoCompleteViews() {
        autoCompleteTitle = binding.autoCompleteTitle;
        autoCompleteGender = binding.autoCompleteGender;

        titleAdapter = new ArrayAdapter<>(this, R.layout.title_item, titles);
        autoCompleteTitle.setAdapter(titleAdapter);

        genderAdapter = new ArrayAdapter<>(this, R.layout.title_item, genders);
        autoCompleteGender.setAdapter(genderAdapter);
    }

    private void setupDatePicker() {
        edtDateOfBirthday = binding.edtDateOfBirth;
        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            if (edtDateOfBirthday != null) {
                edtDateOfBirthday.setText(date);
            }
        };
    }

    private void setupTextWatchers() {
        binding.edtSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextUpdating) {
                    isTextUpdating = true;
                    String upperCaseText = s.toString().toUpperCase();
                    binding.edtSurname.setText(upperCaseText);
                    binding.edtSurname.setSelection(upperCaseText.length());
                    binding.edtSurnameEN.setText(removeDiacritics(upperCaseText));
                    isTextUpdating = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.edtMiddleFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextUpdating) {
                    isTextUpdating = true;
                    String upperCaseText = s.toString().toUpperCase();
                    binding.edtMiddleFirstName.setText(upperCaseText);
                    binding.edtMiddleFirstName.setSelection(upperCaseText.length());
                    binding.edtMiddleFirstNameEN.setText(removeDiacritics(upperCaseText));
                    isTextUpdating = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void addEvents() {
        binding.autoCompleteTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedTitle = adapterView.getItemAtPosition(i).toString();
                String gender = "";

                switch (selectedTitle) {
                    case "Ông":
                        gender = "Nam";
                        break;
                    case "Bà":
                    case "Cô/Chị":
                        gender = "Nữ";
                        break;
                }
                if (autoCompleteGender != null) {
                    autoCompleteGender.setText(gender);
                }
            }
        });

        binding.edtDateOfBirth.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(ActivityAccountDetail.this, mDateSetListener, year, month, day);
            dialog.show();
        });
    }

    public static String removeDiacritics(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
