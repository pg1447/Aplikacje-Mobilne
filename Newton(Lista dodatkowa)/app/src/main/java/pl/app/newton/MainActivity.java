package pl.app.newton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.app.newton.data.ApiResponse;
import pl.app.newton.data.NewtonApiClient;

public class MainActivity extends AppCompatActivity {

    private final NewtonApiClient apiClient = new NewtonApiClient();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private EditText expressionInput;
    private TextView resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expressionInput = findViewById(R.id.expressionInput);
        resultTxt = findViewById(R.id.result);
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        resultTxt.setText("");
        String expression = expressionInput.getText().toString();
        String operation = (String) view.getTag(); // pobiera operacje, ktora sie ma wykona

        compositeDisposable.add(
                apiClient.getNewtonApi().performOperation(operation, expression)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::showResult, this::showError));

    }

    private void showError(Throwable throwable) {
        Toast.makeText(this, "Error occured: " + throwable, Toast.LENGTH_SHORT).show();
    }

    private void showResult(ApiResponse result) {
        resultTxt.setText(result.getResult());
    }
}