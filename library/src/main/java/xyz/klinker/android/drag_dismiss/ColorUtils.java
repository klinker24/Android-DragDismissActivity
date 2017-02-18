package xyz.klinker.android.drag_dismiss;

import android.graphics.Color;

/**
 * Class used to help with the Material Design Color palette, which is created
 * by lightening and darkening base colors.
 */
public class ColorUtils {

    /**
     * When converting a material design primary color, to its darker version, we darken by 12.
     *
     * @param color the primary color.
     * @return the darker version of the primary color.
     */
    public static int darkenPrimaryColor(int color) {
        return darken(color, 12);
    }

    /**
     * Darkens a given color.
     *
     * @param base base color
     * @param amount amount between 0 and 100
     * @return darken color
     */
    private static int darken(int base, int amount) {
        float[] hsv = new float[3];
        Color.colorToHSV(base, hsv);

        float[] hsl = hsv2hsl(hsv);
        hsl[2] -= amount / 100f;

        if (hsl[2] < 0)
            hsl[2] = 0f;

        hsv = hsl2hsv(hsl);
        return Color.HSVToColor(hsv);
    }

    /**
     * Converts HSV (Hue, Saturation, Value) color to HSL (Hue, Saturation, Lightness)
     * https://gist.github.com/xpansive/1337890
     *
     * @param hsv HSV color array
     * @return hsl
     */
    private static float[] hsv2hsl(float[] hsv) {
        float hue = hsv[0];
        float sat = hsv[1];
        float val = hsv[2];

        float nhue = (2f - sat) * val;
        float nsat = sat * val / (nhue < 1f ? nhue : 2f - nhue);
        if (nsat > 1f)
            nsat = 1f;

        return new float[] {
                hue,  nsat, nhue / 2f
        };
    }

    /**
     * Reverses hsv2hsl
     * https://gist.github.com/xpansive/1337890
     *
     * @param hsl HSL color array
     * @return hsv color array
     */
    private static float[] hsl2hsv(float[] hsl) {
        float hue = hsl[0];
        float sat = hsl[1];
        float light = hsl[2];

        sat *= light < .5 ? light : 1 - light;

        return new float[] {
                hue, 2f * sat / (light + sat), light + sat
        };
    }
}