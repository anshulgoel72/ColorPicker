package com.example.colorpicker;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.TableLayout;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.ResourceManager;
import ohos.global.resource.WrongTypeException;
import com.example.colorpicker.util.LogUtil;
import java.io.IOException;

/**
 *  Class for creating a color picker palette.
 */
public class ColorPickerPalette extends TableLayout {
    private String mDescription;
    private String mDescriptionSelected;
    private int mMarginSize;
    private int mNumColumns;
    private ColorPickerSwatch.OnColorSelectedListener mOnColorSelectedListener;
    private int mSwatchLength;

    public ColorPickerPalette(Context context) {
        super(context);
    }

    public ColorPickerPalette(Context context, AttrSet attributeSet) {
        super(context, attributeSet);
    }

    private void addSwatchToRow(DirectionalLayout tableRow, Component view, int line) {
        if (line % 2 == 0) {
            tableRow.addComponent(view);
            return;
        }
        tableRow.addComponent(view, 0);
    }

    private Image createBlankSpace() {
        Image imageView = new Image(getContext());
        DirectionalLayout.LayoutConfig layoutParams = new DirectionalLayout
                .LayoutConfig(this.mSwatchLength, this.mSwatchLength);
        layoutParams.setMargins(this.mMarginSize, this.mMarginSize, this.mMarginSize, this.mMarginSize);
        imageView.setLayoutConfig(layoutParams);
        return imageView;
    }

    private ColorPickerSwatch createColorSwatch(Color color, Color selectedColor) {
        ColorPickerSwatch colorPickerSwatch = new ColorPickerSwatch(getContext(),
                color, color == selectedColor, this.mOnColorSelectedListener);
        DirectionalLayout.LayoutConfig layoutParams = new DirectionalLayout
                .LayoutConfig(this.mSwatchLength, this.mSwatchLength);
        layoutParams.setMargins(this.mMarginSize, this.mMarginSize, this.mMarginSize, this.mMarginSize);
        colorPickerSwatch.setLayoutConfig(layoutParams);
        return colorPickerSwatch;
    }

    private DirectionalLayout createTableRow() {
        DirectionalLayout localTableRow = new DirectionalLayout(getContext());
        localTableRow.setOrientation(Component.HORIZONTAL);
        localTableRow.setLayoutConfig(new ComponentContainer.LayoutConfig(-2, -2));
        return localTableRow;
    }

    private void setSwatchDescription(int rowNumber, int index, int rowElements, boolean selected,
                                      Component swatch) {
        int accessibilityIndex;
        if (rowNumber % 2 == 0) {
            // We're in a regular-ordered row
            accessibilityIndex = index;
        } else {
            // We're in a backwards-ordered row.
            int rowMax = ((rowNumber + 1) * mNumColumns);
            accessibilityIndex = rowMax - rowElements;
        }

        String description;
        if (selected) {
            description = String.format(mDescriptionSelected, accessibilityIndex);
        } else {
            description = String.format(mDescription, accessibilityIndex);
        }
        swatch.setAccessibilityDescription(description);
    }

    /**
     * To draw the palette.
     *
     * @param colors list of colors to be drawn on palette.
     * @param selectedColor selected color.
     */
    public void drawPalette(Color[] colors, Color selectedColor) {
        if (colors == null) {
            return;
        }
        this.removeAllComponents();
        int tableElements = 0;
        int rowElements = 0;
        int rowNumber = 0;
        // Fills the table with swatches based on the array of colors.
        DirectionalLayout row = createTableRow();
        for (Color color : colors) {
            tableElements++;
            Component colorSwatch = createColorSwatch(color, selectedColor);
            setSwatchDescription(rowNumber, tableElements, rowElements, color == selectedColor,
                    colorSwatch);
            addSwatchToRow(row, colorSwatch, rowNumber);

            rowElements++;
            if (rowElements == mNumColumns) {
                addComponent(row);
                row = createTableRow();
                rowElements = 0;
                rowNumber++;
            }
        }
        // Create blank views to fill the row if the last row has not been filled.
        if (rowElements > 0) {
            while (rowElements != mNumColumns) {
                addSwatchToRow(row, createBlankSpace(), rowNumber);
                rowElements++;
            }
            addComponent(row);
        }
    }

    /**
     * initialize the palette.
     *
     * @param size 1 for large and 2 for small size.
     * @param numColumns number of columns in the palette.
     * @param onColorSelectedListener listener when a color is selected.
     */
    public void init(int size, int numColumns, ColorPickerSwatch.OnColorSelectedListener onColorSelectedListener) {
        this.mNumColumns = numColumns;
        ResourceManager resources = getResourceManager();
        try {
            if (size == 1) {
                this.mSwatchLength = resources.getElement(ResourceTable.Float_color_swatch_large).getInteger();
                this.mMarginSize = resources.getElement(ResourceTable.Float_color_swatch_margins_large).getInteger();
            } else {
                this.mSwatchLength = resources.getElement(ResourceTable.Float_color_swatch_small).getInteger();
                this.mMarginSize = resources.getElement(ResourceTable.Float_color_swatch_margins_small).getInteger();
            }
            this.mOnColorSelectedListener = onColorSelectedListener;
            this.mDescription = resources.getElement(ResourceTable.String_color_swatch_description).getString();
            this.mDescriptionSelected = resources.getElement(ResourceTable.String_color_swatch_description_selected)
                    .getString();
        } catch (NotExistException | WrongTypeException | IOException e) {
            LogUtil.error("ColorPickerPalette init", "NotExistException | WrongTypeException | IOException");
        }
    }
}