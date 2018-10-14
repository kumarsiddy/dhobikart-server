package com.freakydevs.kolkatalocal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.freakydevs.kolkatalocal.R;
import com.freakydevs.kolkatalocal.customview.MyToast;
import com.freakydevs.kolkatalocal.utils.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.khirr.android.privacypolicy.PrivacyPolicyDialog;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    private static final long TIMEFORSPLASH = 800;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        showPrivacyPolicyDialog();
    }

    private void showPrivacyPolicyDialog() {
        final PrivacyPolicyDialog privacyPolicyDialog = new PrivacyPolicyDialog(this,
                Constants.TERMS_CONDITION_URL,
                Constants.PRIVACY_POLICY_URL);

        addPoliciesToDialog(privacyPolicyDialog);

        final Intent intent = new Intent(this, MainActivity.class);

        privacyPolicyDialog.setOnClickListener(new PrivacyPolicyDialog.OnClickListener() {
            @Override
            public void onAccept(boolean isFirstTime) {
                if (isFirstTime) {
                    startActivity(intent);
                    finish();
                } else {
                    handleSplashProperty();
                }
            }

            @Override
            public void onCancel() {
                MyToast.showToast(SplashActivity.this, getString(R.string.accept_terms_condition));
                privacyPolicyDialog.show();
            }
        });
        privacyPolicyDialog.show();
    }

    private void addPoliciesToDialog(PrivacyPolicyDialog privacyPolicyDialog) {
        for (int policyId : Arrays.asList(
                R.string.policy_1,
                R.string.policy_2,
                R.string.policy_3,
                R.string.policy_4)) {
            privacyPolicyDialog.addPoliceLine(getString(policyId));
        }
    }

    private void handleSplashProperty() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, TIMEFORSPLASH);
    }
}
