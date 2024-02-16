package mage.choices;

import mage.constants.SubType;

import java.util.stream.Collectors;

/**
 * @author tre3qwerty
 */
public class ChoiceLandType extends ChoiceImpl {

    public ChoiceLandType() {
        super(true);
        this.setChoices(SubType.getLandTypes().stream().map(SubType::toString).collect(Collectors.toSet()));
        this.message = "Choose a land type";
    }

    protected ChoiceLandType(final ChoiceLandType choice) {
        super(choice);
    }

    @Override
    public ChoiceLandType copy() {
        return new ChoiceLandType(this);
    }

}
