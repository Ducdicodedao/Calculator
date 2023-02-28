package hcmute.edu.vn.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.media.VolumeShaper;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnDelete, btnNumber0, btnNumber1, btnNumber2, btnNumber3, btnNumber4, btnNumber5,
            btnNumber6, btnNumber7, btnNumber8, btnNumber9,
            btnAdd, btnSubstract, btnMultiply, btnDivide, btnEqual, btnComa, btnPercent, btnReverse;
    TextView txtViewResult, txtViewEquation;
    private Float a;
    private Float b;
    private String operator = "";
    private boolean isEqualClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDelete = findViewById(R.id.btnDelete);
        btnNumber0 = findViewById(R.id.btnNumber0);
        btnNumber1 = findViewById(R.id.btnNumber1);
        btnNumber2 = findViewById(R.id.btnNumber2);
        btnNumber3 = findViewById(R.id.btnNumber3);
        btnNumber4 = findViewById(R.id.btnNumber4);
        btnNumber5 = findViewById(R.id.btnNumber5);
        btnNumber6 = findViewById(R.id.btnNumber6);
        btnNumber7 = findViewById(R.id.btnNumber7);
        btnNumber8 = findViewById(R.id.btnNumber8);
        btnNumber9 = findViewById(R.id.btnNumber9);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubstract = findViewById(R.id.btnSubstract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnEqual = findViewById(R.id.btnEqual);
        btnComa = findViewById(R.id.btnComa);
        btnReverse =findViewById(R.id.btnReverse);
        btnPercent = findViewById(R.id.btnPercent);
        txtViewResult = findViewById(R.id.txtViewResult);
        txtViewEquation = findViewById(R.id.txtViewEquation);

        btnNumber0.setOnClickListener(handleNumberClick);
        btnNumber1.setOnClickListener(handleNumberClick);
        btnNumber2.setOnClickListener(handleNumberClick);
        btnNumber3.setOnClickListener(handleNumberClick);
        btnNumber4.setOnClickListener(handleNumberClick);
        btnNumber5.setOnClickListener(handleNumberClick);
        btnNumber6.setOnClickListener(handleNumberClick);
        btnNumber7.setOnClickListener(handleNumberClick);
        btnNumber8.setOnClickListener(handleNumberClick);
        btnNumber9.setOnClickListener(handleNumberClick);

        btnAdd.setOnClickListener(handleOperatorClick);
        btnSubstract.setOnClickListener(handleOperatorClick);
        btnMultiply.setOnClickListener(handleOperatorClick);
        btnDivide.setOnClickListener(handleOperatorClick);

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                v.startAnimation(fadein);
                equalsClick(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                v.startAnimation(fadein);
                deleteClick(v);
            }
        });

        btnComa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                v.startAnimation(fadein);
                commaClick(v);
            }
        });

        btnPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtViewResult.getText().toString().equals("")){
                    txtViewResult.setText(txtViewResult.getText().toString().replace(",", "."));
                    Float temp = Float.parseFloat(txtViewResult.getText().toString());
                    temp = temp / 100;
                    txtViewResult.setText(formatNumber(temp));
                    txtViewResult.setText(txtViewResult.getText().toString().replace(".", ","));
                }
                Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                v.startAnimation(fadein);
            }
        });
        btnReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtViewResult.getText().toString().equals("") && !txtViewResult.getText().toString().equals("0")){
                    if(txtViewResult.getText().toString().contains("-")){
                        txtViewResult.setText(txtViewResult.getText().toString().replace("-",""));
                    }
                    else{
                        txtViewResult.setText("-"+ txtViewResult.getText().toString());
                    }
                }
                Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                v.startAnimation(fadein);
            }
        });

    }

    public void numberClick(View view) {
        Button button = (Button) view;
        if (txtViewResult.getText().toString().trim().equals("0")) {
            txtViewResult.setText("");
        }
        if (isEqualClicked) {
            txtViewResult.setText("");
            isEqualClicked = false;
        }
        txtViewResult.append(button.getText().toString().trim());
        txtViewResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
    }

    public void operatorClick(View view) {
        Button button = (Button) view;
        String preOperator = operator;
        operator = button.getText().toString();
        txtViewResult.setText(txtViewResult.getText().toString().replace(",", "."));
        //Ngăn trường hợp chọn 2 dấu liên tiếp
        if (!txtViewResult.getText().toString().equals(""))
            if (a != null) {
                switch (preOperator) {
                    case "+":
                        a = a + Float.parseFloat(txtViewResult.getText().toString());
                        break;
                    case "–":
                        a = a - Float.parseFloat(txtViewResult.getText().toString());
                        break;
                    case "×":
                        a = a * Float.parseFloat(txtViewResult.getText().toString());
                        break;
                    case "÷":
                        a = a / Float.parseFloat(txtViewResult.getText().toString());
                        break;
                }
                txtViewEquation.setText(a.toString());
                txtViewEquation.setText(formatNumber(a));
                txtViewEquation.setText(txtViewEquation.getText().toString().replace(".", ","));
                //shirk textSize
                exceedLength();
            } else {
                a = Float.parseFloat(txtViewResult.getText().toString());
            }
        txtViewResult.setText("");
    }

    public void equalsClick(View view) {
        isEqualClicked = true;
        txtViewResult.setText(txtViewResult.getText().toString().replace(",", "."));
        // Ngăn trường hợp chưa nhập số B mà đã nhấn dấu bằng
        if (txtViewResult.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter Number B", Toast.LENGTH_SHORT).show();
            return;
        }
        b = Float.parseFloat(txtViewResult.getText().toString());
        switch (operator) {
            case "+":
                txtViewResult.setText(formatNumber(a+b));
                a = null;
                break;
            case "–":
                txtViewResult.setText(formatNumber(a-b));
                a = null;
                break;
            case "×":
                txtViewResult.setText(formatNumber(a*b));
                a = null;
                break;
            case "÷":
                txtViewResult.setText(formatNumber(a/b));
                a = null;
                break;
        }
        txtViewResult.setText(txtViewResult.getText().toString().replace(".", ","));
        txtViewEquation.setText("");
        //UI
        btnAdd.setSelected(false);
        btnSubstract.setSelected(false);
        btnMultiply.setSelected(false);
        btnDivide.setSelected(false);

        //shirk textSize
        exceedLength();
    }

    public void deleteClick(View view) {
        txtViewResult.setText("0");
        txtViewEquation.setText("");
        a = null;
        b = null;
        operator = "";
        //UI
        btnAdd.setSelected(false);
        btnSubstract.setSelected(false);
        btnMultiply.setSelected(false);
        btnDivide.setSelected(false);
        txtViewResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
    }

    public void commaClick(View view) {
        if (!txtViewResult.getText().toString().contains(",")) {
            if (txtViewResult.getText().toString().equals(""))
                txtViewResult.append("0,");
            else
                txtViewResult.append(",");
        }
    }
    public View.OnClickListener handleOperatorClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
            v.startAnimation(fadein);
            v.setSelected(true);
            Button btnPressed = (Button) v;
            if (!btnAdd.getText().toString().equals(btnPressed.getText().toString())) {
                btnAdd.setSelected(false);
            }
            if (!btnSubstract.getText().toString().equals(btnPressed.getText().toString())) {
                btnSubstract.setSelected(false);
            }
            if (!btnMultiply.getText().toString().equals(btnPressed.getText().toString())) {
                btnMultiply.setSelected(false);
            }
            if (!btnDivide.getText().toString().equals(btnPressed.getText().toString())) {
                btnDivide.setSelected(false);
            }
            operatorClick(v);
        }
    };
    public View.OnClickListener handleNumberClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
            v.startAnimation(fadein);
            numberClick(v);
        }
    };
    private void exceedLength() {
        if (txtViewResult.getText().toString().contains("E")) {
            txtViewResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        }
    }
    private String formatNumber(float result) {
        if (result == (int) result) {
            return Integer.toString((int) result);
        } else {
            return Float.toString(result);
        }
    }
}

