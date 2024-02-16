

package mage.choices;

/**
 * @author LevelX2
 */
public class ChoiceBasicLandType extends ChoiceImpl {

    public ChoiceBasicLandType() {
        super(true);
        this.choices.add("Forest");
        this.choices.add("Island");
        this.choices.add("Mountain");
        this.choices.add("Plains");
        this.choices.add("Swamp");
        this.message = "Choose a basic land type";
    }

    protected ChoiceBasicLandType(final ChoiceBasicLandType choice) {
        super(choice);
    }

    @Override
    public ChoiceBasicLandType copy() {
        return new ChoiceBasicLandType(this);
    }

}
