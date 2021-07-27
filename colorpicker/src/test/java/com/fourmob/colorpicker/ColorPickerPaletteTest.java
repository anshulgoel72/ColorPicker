package com.fourmob.colorpicker;

import com.fourmob.colorpicker.util.LogUtil;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.ResourceManager;
import ohos.global.resource.WrongTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ColorPickerPaletteTest {

    @InjectMocks
    ColorPickerPalette palette;
    @Mock
    ResourceManager resourceManager;
    @Mock
    Context context;

    private static final String LABEL = "ColorPickerPalette init";
    private static final String MESSAGE = "IOException | NotExistException | WrongTypeException";

    @Test
    public void testgetPathForVerify() {
        palette = new ColorPickerPalette(context);
        ColorPickerSwatch.OnColorSelectedListener onColorSelectedListener = new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(Color color) {
                //not required
            }
        };
        when(context.getResourceManager()).thenReturn(resourceManager);
        palette.init(context,2,3,onColorSelectedListener);
        try {
            verify(resourceManager,atLeastOnce()).getElement(ResourceTable.Float_color_swatch_small);
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(LABEL, MESSAGE);
        }
    }
}
