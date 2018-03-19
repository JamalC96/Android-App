package com.example.a20075085.ifitness;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.a20075085.ifitness.R.id.report_Button;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private static final String KEY_DAILY_TOTAL = "daily_total";
    private DailyTotal mDailyTotal;
    private DailyTotalStack mDailyTotalStack;

    private EditText mCalorieField;
    private EditText mFatField;
    private EditText mCarbField;
    private EditText mProteinField;

    private Button mAddButton;
    private Button reportButton;

    private TextView mCalTotalTextView;
    private TextView mFatTotalTextView;
    private TextView mCarbTotalTextView;
    private TextView mProteinTotalTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDailyTotalStack = DailyTotalStack.get();
        if (savedInstanceState != null) {
            double[] values = savedInstanceState.getDoubleArray(KEY_DAILY_TOTAL);
            mDailyTotal = new DailyTotal(values[0], values[1], values[2], values[3]);
        } else mDailyTotal = new DailyTotal();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        mCalorieField = v.findViewById(R.id.calories_edit_text);
        mFatField = v.findViewById(R.id.fat_edit_text);
        mCarbField = v.findViewById(R.id.carbs_edit_text);
        mProteinField = v.findViewById(R.id.protein_edit_text);

        mCalTotalTextView = v.findViewById(R.id.calorie_total_label);
        mFatTotalTextView = v.findViewById(R.id.fat_total_label);
        mCarbTotalTextView =  v.findViewById(R.id.carb_total_label);
        mProteinTotalTextView = v.findViewById(R.id.protein_total_label);

        mAddButton = v.findViewById(R.id.add_button);
        reportButton =  v.findViewById(report_Button);
        mAddButton.setOnClickListener(new View.OnClickListener() {

            /**
             * pushes data from user to update text views which overwrites
             * from the data entered
             */
            @Override
            public void onClick(View v) {
                fixBlankInput();
                mDailyTotalStack.push(new DailyTotal(mDailyTotal));
                mDailyTotal.addCalories(Double.valueOf(mCalorieField.getText().toString()));
                mDailyTotal.addFat(Double.valueOf(mFatField.getText().toString()));
                mDailyTotal.addCarbs(Double.valueOf(mCarbField.getText().toString()));
                mDailyTotal.addProtein(Double.valueOf(mProteinField.getText().toString()));
                updateTextViews();
                clearFields();
            }
        });
        updateTextViews();
        return v;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }



    @Override
    /**
     * returns top item from redo stack and also adds it to undo stack
     * @return top item from redo stack
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_reset:
                createDialog();
                return true;
            case R.id.menu_item_undo:
                if (!mDailyTotalStack.canUndo()) {
                    Toast.makeText(getActivity(), R.string.cant_undo, Toast.LENGTH_SHORT).show();
                } else {
                    mDailyTotal = mDailyTotalStack.undo(mDailyTotal);
                    updateTextViews();
                }
                return true;
            case R.id.menu_item_redo:
                if (!mDailyTotalStack.canRedo()) {
                    Toast.makeText(getActivity(), R.string.cant_redo, Toast.LENGTH_SHORT).show();
                } else {
                    mDailyTotal = mDailyTotalStack.redo(mDailyTotal);
                    updateTextViews();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
        if (mDailyTotal != null) {
            double[] values = {mDailyTotal.getCalories(), mDailyTotal.getFat(),
                    mDailyTotal.getCarbs(), mDailyTotal.getProtein()};
            onSavedInstanceState.putDoubleArray(KEY_DAILY_TOTAL, values);
        }
    }

    /**
     * Updates
     */
    private void updateTextViews() {
        mCalTotalTextView.setText(getString(R.string.calories_label) + " " + mDailyTotal.getCalories());
        mFatTotalTextView.setText(getString(R.string.fat_label) + " " + mDailyTotal.getFat());
        mCarbTotalTextView.setText(getString(R.string.carbs_label) + " " + mDailyTotal.getCarbs());
        mProteinTotalTextView.setText(getString(R.string.protein_label) + " " + mDailyTotal.getProtein());
    }
    /**
     * returns top item from redo stack and also adds it to undo stack
     */
    private void clearFields() {
        mCalorieField.setText("", TextView.BufferType.EDITABLE);
        mFatField.setText("", TextView.BufferType.EDITABLE);
        mCarbField.setText("", TextView.BufferType.EDITABLE);
        mProteinField.setText("", TextView.BufferType.EDITABLE);
    }

    private void fixBlankInput() {
        if (mCalorieField.getText().toString().equals("")) mCalorieField.setText("0");
        if (mFatField.getText().toString().equals("")) mFatField.setText("0");
        if (mCarbField.getText().toString().equals("")) mCarbField.setText("0");
        if (mProteinField.getText().toString().equals("")) mProteinField.setText("0");
    }

    private void createDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(R.string.alert_dialog);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mDailyTotalStack.clear();
                        mDailyTotal.clear();
                        clearFields();
                        updateTextViews();
                        dialog.cancel();

                    }
                });

        builder1.setNegativeButton(
                R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder1.create();
        alert.show();
    }



}
