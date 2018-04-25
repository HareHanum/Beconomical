package com.apps.harel.beconomical.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.apps.harel.beconomical.R;
import com.apps.harel.beconomical.activities.AddTransaction;
import com.apps.harel.beconomical.objects.MyEventDay;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class PickDateFragment extends Fragment {

    GetEvent callBack;
    MyEventDay myEventDay;

    //INTERFACE
    public interface GetEvent {
        void getEventDay(EventDay eventDay);
    }

    public PickDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pick_date, container, false);

        final CalendarView datePicker = v.findViewById(R.id.datePicker);
        FloatingActionButton button = v.findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTransaction.class);
                Date d =datePicker.getSelectedDate().getTime();
                intent.putExtra("date" , d.toString());
                intent.putExtra("fromPickDate",1);
                startActivity(intent);
//                myEventDay = new MyEventDay(datePicker.getSelectedDate(),
//                        R.drawable.circle,"" );
//                sendEventDay();
//                switchToCalendarFragment();
            }
        });

        Toast.makeText(getContext(), "Pick date", Toast.LENGTH_SHORT).show();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callBack = (GetEvent) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement GetEvent");
        }
    }

    public void sendEventDay() {
        callBack.getEventDay(myEventDay);
    }

    @Override
    public void onDetach() {
        callBack = null; // => avoid leaking
        super.onDetach();
    }

    public void switchToCalendarFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).commit();
    }
}
