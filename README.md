AndroidLabeledEditText
======================

```
<dependency>
  <groupId>no.wtw.android</groupId>
  <artifactId>android-labeled-edittext</artifactId>
  <type>apklib</type>
  <version>xxx</version>
</dependency>
```

## Usage

Add a new namespace attribute to your root layout container.

```
xmlns:app="http://schemas.android.com/apk/res-auto"
```

Add and use LabeledEditText as you would any other EditText widget, with the exception of three new attributes.

```
<no.wtw.android.labelededittext.widget.LabeledEditText
  ...
  app:label="The label:"
  app:labelMargin="16dp"
  app:labelColor="@color/my_label_color_or_color_statelist"
  ... 
  />
```
