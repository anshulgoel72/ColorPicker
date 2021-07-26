package com.example.colorpicker;

import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ProgressBar;
import ohos.agp.components.Text;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import com.example.colorpicker.util.LogUtil;
import java.io.IOException;

/**
 * Class for creating a Color Picker Dialog.
 */
public class ColorPickerDialog extends CommonDialog implements ColorPickerSwatch.OnColorSelectedListener {
    protected Color[] mColors = null;
    protected int mColumns;
    protected ColorPickerSwatch.OnColorSelectedListener mListener;
    private ColorPickerPalette mPalette;
    private ProgressBar mProgress;
    protected Color mSelectedColor;
    protected int mSize;
    private final Context mContext;
    protected int mTitleResId = ResourceTable.String_color_picker_default_title;

    public ColorPickerDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    private void refreshPalette() {
        if ((this.mPalette != null) && (this.mColors != null)) {
            this.mPalette.drawPalette(this.mColors, this.mSelectedColor);
        }
    }

    /**
     * initialize all the parameters of the dialog.
     *
     * @param titleId of the dialog title.
     * @param colors list of color to be displayed in the dialog.
     * @param selectedColor selected color.
     * @param columns number of columns in our palette.
     * @param size takes two values: 1 for large and 2 for small size.
     */
    public void initialize(int titleId, Color[] colors, Color selectedColor, int columns, int size) {
        this.mTitleResId = titleId;
        this.mColors = colors;
        this.mColumns = columns;
        this.mSelectedColor = selectedColor;
        this.mSize = size;
        setColors(colors, selectedColor);
    }

    /**
     * Redraw the palette when a color is selected.
     *
     * @param selectedColor selected color.
     */
    public void onColorSelected(Color selectedColor) {
        if (this.mListener != null) {
            this.mListener.onColorSelected(selectedColor);
        }
        if (selectedColor != this.mSelectedColor) {
            this.mSelectedColor = selectedColor;
            this.mPalette.drawPalette(this.mColors, this.mSelectedColor);
        }
        hide();
    }

    @Override
    public void onCreate() {
        Component component = LayoutScatter.getInstance(mContext)
                .parse(ResourceTable.Layout_color_picker_dialog, null, false);
        DirectionalLayout titleComponent = (DirectionalLayout) LayoutScatter.getInstance(mContext)
                .parse(ResourceTable.Layout_color_picker_dialog_title, null, false);
        Text title = (Text) titleComponent.findComponentById(ResourceTable.Id_dialog_title);
        this.mProgress = ((ProgressBar) component.findComponentById(ResourceTable.Id_progress));
        this.mPalette = ((ColorPickerPalette) component.findComponentById(ResourceTable.Id_color_picker));
        try {
            this.mPalette.init(this.mSize, this.mColumns, this);
            title.setText(mContext.getResourceManager().getElement(mTitleResId).getString());
            setTitleCustomComponent(titleComponent);
        } catch (NotExistException | WrongTypeException | IOException e) {
            LogUtil.error("ColorPickerDialog onCreate", "NotExistException | WrongTypeException | IOException");
        }
        if (this.mColors != null) {
            showPaletteView();
        }
        setContentCustomComponent(component);
        setAutoClosable(true);
    }

    /**
     * to set the colors of the palette.
     *
     * @param colors list of colors to be displayed in the palette.
     * @param selected selected color.
     */
    public void setColors(Color[] colors, Color selected) {
        if ((this.mColors != colors) || (this.mSelectedColor != selected)) {
            this.mColors = colors;
            this.mSelectedColor = selected;
            refreshPalette();
        }
    }

    public void setOnColorSelectedListener(ColorPickerSwatch.OnColorSelectedListener onColorSelectedListener) {
        this.mListener = onColorSelectedListener;
    }

    /**
     * to show the palette in dialog.
     */
    public void showPaletteView() {
        if ((this.mProgress != null) && (this.mPalette != null)) {
            this.mProgress.setVisibility(Component.HIDE);
            refreshPalette();
            this.mPalette.setVisibility(Component.VISIBLE);
        }
    }

    /**
     * To show the progress bar.
     */
    public void showProgressBarView() {
        if ((this.mProgress != null) && (this.mPalette != null)) {
            this.mProgress.setVisibility(Component.VISIBLE);
            this.mPalette.setVisibility(Component.HIDE);
        }
    }
}