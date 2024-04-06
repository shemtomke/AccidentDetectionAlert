package com.example.accidentdetectionalert.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.AccidentAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Accident;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewAllAccidents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAllAccidents extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private AccidentAdapter adapter;
    private List<Accident> accidentList;
    private DatabaseHelper databaseHelper;
    // Add a boolean flag to determine if the adapter should show all accidents or only those assigned to the hospital
    private boolean showAllAccidents;
    public ViewAllAccidents() {
        // Required empty public constructor
    }
    public ViewAllAccidents(boolean showAllAccidents) {
        this.showAllAccidents = showAllAccidents;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAllAccidents.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAllAccidents newInstance(String param1, String param2) {
        ViewAllAccidents fragment = new ViewAllAccidents();
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
        View view = inflater.inflate(R.layout.fragment_view_all_accidents, container, false);

        recyclerView = view.findViewById(R.id.adminAccidentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(getActivity());
        if (showAllAccidents) {
            accidentList = databaseHelper.getAllAccidentsWithUserDetails();
        } else {
            // Assuming you have a way to get the current user's hospital ID
            String hospitalId = getCurrentUserHospitalId();
            accidentList = databaseHelper.getAccidentsForHospital(hospitalId);
        }

        adapter = new AccidentAdapter(accidentList, getActivity());
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
    private String getCurrentUserHospitalId() {
        // Implement your logic to get the current user's hospital ID
        return "your_hospital_id_here";
    }
}