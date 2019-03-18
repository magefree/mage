
package mage.abilities.text;

import mage.ObjectColor;

/**
 *
 * This implementation is not finished yet. There is no support to also change
 * the rules text of an object.
 *
 * @author LevelX2
 */
public class TextPartColor extends TextPartImpl<ObjectColor> {

    private final ObjectColor objectColorBase;
    private ObjectColor objectColorCurrent;

    public TextPartColor(ObjectColor objectColor) {
        this.objectColorBase = objectColor;
        this.objectColorCurrent = objectColor;
    }

    public TextPartColor(final TextPartColor textPartColor) {
        super();
        this.objectColorBase = textPartColor.objectColorBase;
        this.objectColorCurrent = textPartColor.objectColorCurrent;
    }

    @Override
    public String getText() {
        return objectColorCurrent.getDescription();
    }

    @Override
    public ObjectColor getCurrentValue() {
        return objectColorCurrent;
    }

    @Override
    public ObjectColor getBaseValue() {
        return objectColorBase;
    }

    @Override
    public void replaceWith(ObjectColor objectColor) {
        this.objectColorCurrent = objectColor;
    }

    @Override
    public void reset() {
        this.objectColorCurrent = this.objectColorBase;
    }

    @Override
    public TextPartColor copy() {
        return new TextPartColor(this);
    }

    @Override
    public String toString() {
        return objectColorCurrent.toString();
    }

}
