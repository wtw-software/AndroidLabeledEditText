package no.wtw.android.labelededittext.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import no.wtw.android.labelededittext.R;

public class LabeledEditText extends EditText {

    private String label = "";
    private Paint labelPaint;
    private TextView labelView;
    private int labelMargin;

    public LabeledEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LabeledEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.LabeledEditText,
                    0, 0);
        } catch (NullPointerException e) {
        }

        int defaultMargin = isInEditMode() ? 16 : getContext().getResources().getDimensionPixelSize(R.dimen.label_margin_default);
        ColorStateList labelColor = null;

        if (a != null) {
            String labelAttribute = a.getString(R.styleable.LabeledEditText_label);
            if (labelAttribute == null || labelAttribute.trim().length() == 0) {
                CharSequence hint = getHint();
                if (hint != null) {
                    labelAttribute = getHint().toString().trim();
                    setHint("");
                }
            }
            setLabel(labelAttribute);

            labelMargin = a.getDimensionPixelSize(R.styleable.LabeledEditText_labelMargin, defaultMargin);
            labelColor = a.getColorStateList(R.styleable.LabeledEditText_labelColor);
            a.recycle();
        }

        setPadding(
                super.getPaddingLeft() + getLabelWidth(),
                super.getPaddingTop(),
                super.getPaddingRight(),
                super.getPaddingBottom());

        setContentDescription(getLabel());

        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setTextSize(getTextSize());
        labelPaint.setTypeface(getPaint().getTypeface());

        labelView = new TextView(getContext());
        labelView.setPadding(getPaddingLeft() - getLabelWidth(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        labelView.setEnabled(isEnabled());
        labelView.setText(label);
        labelView.setGravity(getGravity());
        labelView.setTextColor(labelColor == null ? getHintTextColors() : labelColor);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private int getLabelWidth() {
        return (int) getPaint().measureText(getLabel()) + labelMargin;
    }

    public String getLabel() {
        if (label == null)
            return "";
        return label.trim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(loadBitmapFromView(labelView), 0, 0, labelPaint);
    }

    public Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, getWidth(), getHeight());
        v.draw(c);
        return b;
    }
}
