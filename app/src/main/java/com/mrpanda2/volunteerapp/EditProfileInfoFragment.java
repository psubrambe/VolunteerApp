package com.mrpanda2.volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileInfoFragment extends Fragment {
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private TextView mName;
    private TextView mPassword;
    private TextView mEmail;
    private Button mShowEventButton;
    private Button mUpdateButton;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit_profile,container, false);

        mName = v.findViewById(R.id.OrgName);

        mPassword = v.findViewById(R.id.OrgPassword);
        SharedPreferences sharedPref = EditProfileInfoFragment.this.getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String userType = sharedPref.getString(getString(R.string.typeid), "default");

        if (userType.equals("vol")) {
            final DatabaseReference rootRef = mDatabase.getRef();

            mName.setText(mUser.getDisplayName());
            final String mString = mUser.getUid();
            mUpdateButton = v.findViewById(R.id.UpdateButton);
            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name = mName.getText().toString();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    mUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                    rootRef.child("volunteers").child(mUser.getUid()).child("name").setValue(name);

                    Toast.makeText(EditProfileInfoFragment.this.getActivity(), "Name Updated For Next Session", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileInfoFragment.this.getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (userType.equals("org")) {
            final DatabaseReference rootRef = mDatabase.getRef();
            mName.setText(mUser.getDisplayName());

            mUpdateButton = v.findViewById(R.id.UpdateButton);
            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name = mName.getText().toString();
                    rootRef.child("volunteers").child(mUser.getUid()).child("name").setValue(name);
                    Toast.makeText(EditProfileInfoFragment.this.getActivity(), "Name Updated For Next Session", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileInfoFragment.this.getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        return v;

    }
}
