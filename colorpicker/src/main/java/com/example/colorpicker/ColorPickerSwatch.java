package com.example.colorpicker;

import static ohos.multimodalinput.event.TouchEvent.POINT_MOVE;
import static ohos.multimodalinput.event.TouchEvent.PRIMARY_POINT_DOWN;
import ohos.agp.colors.HsvColor;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.StackLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.app.Context;

/**
 * class for creating a color picker swatch.
 */
public class ColorPickerSwatch extends StackLayout implements Component.ClickedListener {
    private final Color mColor;
    private final OnColorSelectedListener mOnColorSelectedListener;
    private final Image mSwatchImage;

    /**
     * initialize the swatch.
     *
     * @param paramContext context.
     * @param color color of the swatch.
     * @param checked true if the current color is checked, otherwise false.
     * @param onColorSelectedListener listener for the selected color.
     */
    public ColorPickerSwatch(Context paramContext, Color color, boolean checked,
                             OnColorSelectedListener onColorSelectedListener) {
        super(paramContext);
        this.mColor = color;
        this.mOnColorSelectedListener = onColorSelectedListener;
        Component component = LayoutScatter.getInstance(paramContext).parse(ResourceTable
                .Layout_color_picker_swatch, this, true);
        this.mSwatchImage = ((Image) component.findComponentById(ResourceTable.Id_color_picker_swatch1));
        setColor(color, checked);
        setClickedListener(this);
    }

    @Override
    public void onClick(Component component) {
        if (this.mOnColorSelectedListener != null) {
            this.mOnColorSelectedListener.onColorSelected(this.mColor);
        }
    }

    protected void setColor(Color color, boolean checked) {
        ShapeElement drawables = new ShapeElement();
        drawables.setShape(ShapeElement.OVAL);
        this.mSwatchImage.setTouchEventListener((component, touchEvent) -> {
            int currentAction = touchEvent.getAction();
            if (currentAction == PRIMARY_POINT_DOWN || currentAction == POINT_MOVE) {
                int resColor = getPressedColor(color);
                drawables.setRgbColor(RgbColor.fromArgbInt(resColor));
                setChecked(drawables, checked);
            } else {
                drawables.setRgbColor(RgbColor.fromArgbInt(color.getValue()));
                setChecked(drawables, checked);
            }
            return currentAction == PRIMARY_POINT_DOWN || currentAction == POINT_MOVE;
        });
        drawables.setRgbColor(RgbColor.fromArgbInt(color.getValue()));
        setChecked(drawables, checked);
    }

    private int getPressedColor(Color color) {
        HsvColor hsvColor = HsvColor.toHSV(color.getValue());
        float value = hsvColor.getValue();
        value *= 0.7;
        return HsvColor.toColor(hsvColor.getAlpha(), hsvColor.getHue(), hsvColor.getSaturation(), value);
    }

    private void setChecked(ShapeElement drawables, boolean checked) {
        if (checked) {
            this.mSwatchImage.setPixelMap(ResourceTable.Media_check2);
            this.mSwatchImage.setBackground(drawables);
        } else {
            this.mSwatchImage.setImageElement(drawables);
        }
    }

    /**
     * listener for selected color.
     */
    public abstract static interface OnColorSelectedListener {
        public abstract void onColorSelected(Color color);
    }
}