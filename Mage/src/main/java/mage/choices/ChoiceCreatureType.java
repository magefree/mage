package mage.choices;

import mage.constants.SubType;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class ChoiceCreatureType extends ChoiceImpl {

    public ChoiceCreatureType() {
        super(true);
        this.setChoices(SubType.getCreatureTypes(false).stream().map(SubType::toString).collect(Collectors.toCollection(LinkedHashSet::new)));
        this.message = "Choose a creature type:";
    }

    public ChoiceCreatureType(final ChoiceCreatureType choice) {
        super(choice);
    }

    @Override
    public ChoiceCreatureType copy() {
        return new ChoiceCreatureType(this);
    }
}
