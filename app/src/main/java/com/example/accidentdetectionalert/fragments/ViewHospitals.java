package com.example.accidentdetectionalert.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.HospitalAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Hospital;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewHospitals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewHospitals extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button createHospitalButton;

    private RecyclerView recyclerView;
    private HospitalAdapter adapter;
    private List<Hospital> hospitalList;
    private DatabaseHelper databaseHelper;

    public ViewHospitals() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewHospitals.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewHospitals newInstance(String param1, String param2) {
        ViewHospitals fragment = new ViewHospitals();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_hospitals, container, false);

        createHospitalButton = view.findViewById(R.id.createHospitalButtonAdmin);
        recyclerView = view.findViewById(R.id.adminHospitalsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(getActivity());
        hospitalList = databaseHelper.getAllHospitals();
        adapter = new HospitalAdapter(hospitalList, getActivity());
        recyclerView.setAdapter(adapter);

        createHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateHospital();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    void CreateHospital()
    {
        Fragment createHospital = new CreateHospital();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.adminFrameLayout, createHospital).addToBackStack(null).commit();
    }
}