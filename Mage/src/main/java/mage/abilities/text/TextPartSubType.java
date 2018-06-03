
package mage.abilities.text;

import mage.constants.SubType;

/**
 * This implementation is not finished yet. There is no support to also change
 * the rules text of an object. Also all the cards that user subtypes in the
 * text have to be updated with the new elements.
 *
 * @author LevelX2
 */
public class TextPartSubType extends TextPartImpl<SubType> {

    private final SubType subTypeBase;
    private SubType subTypeCurrent;

    public TextPartSubType(SubType subType) {
        this.subTypeBase = subType;
        this.subTypeCurrent = subType;
    }

    public TextPartSubType(final TextPartSubType textPartSubType) {
        super();
        this.subTypeBase = textPartSubType.subTypeBase;
        this.subTypeCurrent = textPartSubType.subTypeCurrent;
    }

    @Override
    public String getText() {
        return subTypeCurrent.getDescription();
    }

    @Override
    public SubType getCurrentValue() {
        return subTypeCurrent;
    }

    @Override
    public SubType getBaseValue() {
        return subTypeBase;
    }

    @Override
    public void replaceWith(SubType subType) {
        this.subTypeCurrent = subType;
    }

    @Override
    public void reset() {
        this.subTypeCurrent = this.subTypeBase;
    }

    @Override
    public TextPartSubType copy() {
        return new TextPartSubType(this);
    }

    @Override
    public String toString() {
        return subTypeCurrent.toString();
    }

}
