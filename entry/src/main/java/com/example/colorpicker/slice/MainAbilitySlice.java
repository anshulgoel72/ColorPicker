package com.example.colorpicker.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;
import com.example.colorpicker.ColorPickerDialog;
import com.example.colorpicker.ResourceTable;

/**
 * Main Ability Slice.
 */
public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        final ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this);
        colorPickerDialog.initialize(ResourceTable.String_dialog_title, new Color[] { Color.CYAN, Color.WHITE, Color
                .LTGRAY, Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.GRAY, Color
                .YELLOW }, Color.YELLOW, 3, 2);
        colorPickerDialog.setOnColorSelectedListener(color -> new ToastDialog(MainAbilitySlice.this)
                .setText("selectedColor : " + color.getValue()).show());

        Button showbtn = (Button) findComponentById(ResourceTable.Id_button1);
        showbtn.setClickedListener(component -> colorPickerDialog.show());
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
