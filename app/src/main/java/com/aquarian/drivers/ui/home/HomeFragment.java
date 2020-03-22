package com.aquarian.drivers.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.aquarian.drivers.R;
import com.aquarian.drivers.util.SaveData;

import org.json.JSONArray;
import org.json.JSONException;

import static com.google.android.gms.internal.zzagr.runOnUiThread;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView dashDate = root.findViewById(R.id.dashDate);
        final TextView dashJobs = root.findViewById(R.id.dashJobs);
        final TextView dashLoc = root.findViewById(R.id.dashLoc);
        dashDate.setText("Working day\n" + getDate());
        dashJobs.setText(numberOfJobs() + " jobs left today");
        //getLocation();
        //dashLoc.setText(getLoc());
        scheduleUpdateLocation(dashLoc);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dashLoc.setText(getLoc());
                            }
                        });
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
        return root;
    }

    private int numberOfJobs() {
        //Read JSON string from internal storage.
        SaveData sd = new SaveData();
        sd.read("jobsFile", getActivity().getApplicationContext());
        //Parse JSON string and create objects
        try {
            JSONArray response = new JSONArray(sd.content); //Parse JSON Array
            return response.length();
        } catch (JSONException ex) {
            Log.e("App", "Failure", ex);
            return 0;
        }
    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM");
        try {
            Date date = Calendar.getInstance().getTime();
            String dateTime = dateFormat.format(date);
            return dateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getLoc(){
        SaveData sd = new SaveData();
        sd.read("loc", this.getContext());
        return sd.content;
    }

    public Handler handler = new Handler();
    private final int FIVE_SECONDS = 5000;
    private Runnable runnableCode;

    public void scheduleUpdateLocation(TextView t) {
        handler.postDelayed(runnableCode = new Runnable() {
            public void run() {
                t.setText(getLoc());          // this method will contain your almost-finished HTTP calls
                //handler.postDelayed(this, FIVE_SECONDS);
            }
        }, FIVE_SECONDS);
    }

    public void removeHandler()
    {
        Log.i("Stop Handler ","Yes");
        handler.removeCallbacks(runnableCode);
    }
}
