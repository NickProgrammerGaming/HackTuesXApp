package telkadjiite.hacktuesx.prilojenie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class Settings extends Fragment {

    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button signOutButton = rootView.findViewById(R.id.button);
        sp = getActivity().getSharedPreferences("LoginState", Context.MODE_PRIVATE);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLoginState();
                SendToLogin();
            }
        });

        return rootView;
    }

    void SendToLogin() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    void clearLoginState() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("LoggedIn", false);
        editor.apply();
    }
}
