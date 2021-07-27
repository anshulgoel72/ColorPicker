# ColorPicker
HarmonyOS library that allow us to pick a color from a palette.

# Source
This library is inspired by [ColorPicker](https://github.com/flavienlaurent/colorpicker) library.

# Features
This library allows us to select a color from the palette. We can also add different colors to the palette.

# Dependency
1. For using ColorPicker module in sample app, include the source code and add the below dependencies in entry/build.gradle to generate hap/colorpicker.har.
``` java
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar', '*.har'])
    testImplementation 'junit:junit:4.13'
    implementation project(':colorpicker')
}
```
2. For using ColorPicker in separate application using har file, add the har file in the entry/libs folder and add the dependencies in entry/build.gradle file.
``` java
dependencies {
	implementation fileTree(dir: 'libs', include: ['*.har'])
	testImplementation 'junit:junit:4.13'
}
```

# Usage
The usage of this library is very simple. In the main ability slice, we just have to create an object of `ColorPickerDialog`. Then we have to initialize it by passing colors(that we want to have in our palette), the selected color, number of columns of the palette and the size of the swatch(which can either take value 1 for larger size and 2 for smaller size).

Then we have to set the listener `setOnColorSelectedListener` which will display a toast message whenever a color swatch is selected from the palette.

Finally we have to set the listener for the button on the homepage of our app that will display us the color picker dialog. 

```java
public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        final ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this);
        colorPickerDialog.initialize(ResourceTable.String_dialog_title, new Color[] { Color.CYAN, Color.WHITE, Color.LTGRAY, Color
                .BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.GRAY, Color.YELLOW },
                Color.YELLOW, 3, 2);
        colorPickerDialog.setOnColorSelectedListener(color -> new ToastDialog(MainAbilitySlice.this)
                .setText("selectedColor : " + color.getValue()).show());
        Button showbtn = (Button) findComponentById(ResourceTable.Id_button1);
        showbtn.setClickedListener(component -> colorPickerDialog.show());
}
```

# License
