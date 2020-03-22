package com.aquarian.drivers.ui.jobs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aquarian.drivers.R;
import com.aquarian.drivers.util.SaveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JobsFragment extends Fragment {

    public static ArrayList<Job> jobList = new ArrayList<Job>();
    public static RecyclerView mRecyclerView;
    public static JobsFragment newInstance() {
        return new JobsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.jobs_fragment, null);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.rViewJobs);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), llm.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        jobList.clear();

        loadData();

        return fragmentView;
    }


    private void loadData()
    {
        //Read JSON string from internal storage.
        SaveData sd = new SaveData();
        sd.read("jobsFile", getActivity().getApplicationContext());
        //Parse JSON string and create objects
        try {
            JSONArray response = new JSONArray(sd.content); //Parse JSON Array
            for(int i=(response.length()-1); i > -1; i--) {
                JSONObject jsonobject = response.getJSONObject(i); //Parse JSON object

                // Get content from the parsed object using variables name
                String jobId = jsonobject.getString("JobID");
                String status = jsonobject.getString("Status");
                String parcelType = jsonobject.getString("ParcelType");
                String parcelSize = jsonobject.getString("ParcelSize");
                String parcelWeight = jsonobject.getString("ParcelWeight");

                //Create News Object and add it to an array of News objects
                Job j = new Job(jobId, status, parcelType, parcelSize, parcelWeight);
                jobList.add(j);
            }
            //Pass the list of News Object to the NewsAdapter and inflate view in each element of the recyclerview
            JobsAdapter nAdapter = new JobsAdapter(jobList);
            mRecyclerView.setAdapter(nAdapter);
        } catch (JSONException ex) {
            Log.e("App", "Failure", ex);
        }
    }
}
