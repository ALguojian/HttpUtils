package com.shuwtech.commonsdk.ui.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.NumberPicker;

import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.databinding.CommonDialogSelectWorkTimeBinding;

import java.util.Arrays;
import java.util.List;

/**
 * 选择工作时间dialog
 * */
public class SelectWorkTimeDialog extends CommonBottomAlertDialog {

    private final String[] TIMES = new String[]{
        "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00",
        "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
        "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"
    };

    private String selectStart;
    private String selectEnd;
    private CommonDialogSelectWorkTimeBinding binding;

    public SelectWorkTimeDialog(@NonNull Context context) {
        super(context);
        btnComplete.setText("确认");
        initPickers();
    }

    @Override
    protected View setContent() {
        binding = DataBindingUtil.inflate(getLayoutInflater(),
            R.layout.common_dialog_select_work_time, null, false);
        binding.setDialog(this);
        return binding.getRoot();
    }

    private void initPickers() {
        binding.pickerStart.setValue(0);
        binding.pickerStart.setMaxValue(TIMES.length - 1);
        binding.pickerStart.setWrapSelectorWheel(false);
        binding.pickerStart.setDisplayedValues(TIMES);

        binding.pickerEnd.setValue(0);
        binding.pickerEnd.setMaxValue(TIMES.length - 1);
        binding.pickerEnd.setWrapSelectorWheel(false);
        binding.pickerEnd.setDisplayedValues(TIMES);

        //默认选中10-10
    }

    public void setSelect(String start, String end) {
        List<String> list = Arrays.asList(TIMES);
        int startPoi = list.indexOf(start) == -1 ? 0 : list.indexOf(start);
        int endPoi = list.indexOf(end) == -1 ? 0 : list.indexOf(end);
        selectStart = TIMES[startPoi];
        selectEnd = TIMES[endPoi];
        binding.pickerStart.setValue(startPoi);
        binding.pickerEnd.setValue(endPoi);
    }

    public void onStartChange(NumberPicker picker, int oldVal, int newVal) {
        selectStart = TIMES[newVal];
    }

    public void onEndChange(NumberPicker picker, int oldVal, int newVal) {
        selectEnd = TIMES[newVal];
    }

    public String getSelectStart() {
        return selectStart;
    }

    public String getSelectEnd() {
        return selectEnd;
    }
}