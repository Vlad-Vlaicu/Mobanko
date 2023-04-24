package com.example.mobanko.activities.onboardingFragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mobanko.R;
import com.example.mobanko.databinding.FragmentOnboardingPersonalDataBinding;


public class OnboardingPersonalData extends Fragment {
    int permissionCounter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        var view =  inflater.inflate
                (R.layout.fragment_onboarding_personal_data,
                        container, false);
        var conditionCheck1 = (CheckBox) view.findViewById(R.id.conditioncheckBox1);
        var conditionCheck2 = (CheckBox) view.findViewById(R.id.conditioncheckBox2);
        var continueButton = (TextView) view.findViewById(R.id.continueTextView);
        var continueDisabledButton = (TextView) view.findViewById(R.id.continueTextViewDisabled);
        var editText = (EditText) view.findViewById(R.id.editTextPersonNameOnboarding);
        var warningMessage = (TextView) view.findViewById(R.id.warningTextView);

        conditionCheck1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(conditionCheck1.isChecked()){
                    permissionCounter++;
                } else {
                    permissionCounter--;
                }

                if(permissionCounter == 2){
                    continueButton.setVisibility(VISIBLE);
                    continueDisabledButton.setVisibility(GONE);
                } else {
                    continueButton.setVisibility(GONE);
                    continueDisabledButton.setVisibility(VISIBLE);
                }
            }
        });

        conditionCheck2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(conditionCheck2.isChecked()){
                    permissionCounter++;
                } else {
                    permissionCounter--;
                }

                if(permissionCounter == 2){
                    continueButton.setVisibility(VISIBLE);
                    continueDisabledButton.setVisibility(GONE);
                } else {
                    continueButton.setVisibility(GONE);
                    continueDisabledButton.setVisibility(VISIBLE);
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var name = editText.getText().toString().trim();

                if(name.isEmpty()){
                    warningMessage.setText("Campul de nume nu poate fi liber!");
                    warningMessage.setVisibility(VISIBLE);
                    return;
                }

                var bundle = new Bundle();
                bundle.putString("userName", name);


                var nextFragment = new OnboardingEmail();
                nextFragment.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView2, nextFragment)
                        .commit();
            }
        });

        return view;
    }
}