package kk.techbytecare.androidpatternlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    String save_pattern_key = "pattern_code";

    String final_pattern = "";

    PatternLockView patternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);

        if (save_pattern != null && !save_pattern.equals("null"))   {

            setContentView(R.layout.pattern_screen);

            patternLockView = findViewById(R.id.pattern_lock_view);

            patternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);

                    if (final_pattern.equals(save_pattern)) {
                        Toast.makeText(MainActivity.this, "Password Correct!!!", Toast.LENGTH_SHORT).show();
                    }
                    else    {
                        Toast.makeText(MainActivity.this, "Password Incorrect!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCleared() {

                }
            });
        }

        else    {
            setContentView(R.layout.activity_main);
            patternLockView = findViewById(R.id.pattern_lock_view);

            patternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
                }

                @Override
                public void onCleared() {

                }
            });

            Button btnSetUp = findViewById(R.id.btnSetPattern);

            btnSetUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(save_pattern_key,final_pattern);
                    Toast.makeText(MainActivity.this, "Pattern Saved Successful!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }


    }
}
