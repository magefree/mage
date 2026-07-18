package mage.designations;

/**
 * @author muz
 */
public class EnduringStory extends Designation {

    public EnduringStory() {
        super(DesignationType.ENDURING_STORY);
    }

    private EnduringStory(final EnduringStory card) {
        super(card);
    }

    @Override
    public EnduringStory copy() {
        return new EnduringStory(this);
    }
}
