package com.example.colorpicker;

import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.ProgressBar;
import ohos.agp.utils.Color;
import ohos.app.Context;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColorPickerDialogTest {
    protected Color[] mColors = null;
    protected int mColumns;
    protected ColorPickerSwatch.OnColorSelectedListener mListener;
    private ColorPickerPalette mPalette;
    private ProgressBar mProgress;
    protected Color mSelectedColor;
    protected int mSize;
    private Context mContext;

    @Test
    public void refreshPaletteTest() {

    }

}