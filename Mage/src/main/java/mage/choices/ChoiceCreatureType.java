package mage.choices;

import mage.MageObject;
import mage.constants.SubType;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class ChoiceCreatureType extends ChoiceImpl {

    private static final String DEFAULT_MESSAGE = "Choose a creature type";

    public ChoiceCreatureType() {
        this(true, DEFAULT_MESSAGE, null);
    }

    public ChoiceCreatureType(MageObject source) {
        this(true, DEFAULT_MESSAGE, source);
    }

    public ChoiceCreatureType(String chooseMessage, MageObject source) {
        this(true, chooseMessage, source);
    }

    public ChoiceCreatureType(boolean required, String chooseMessage, MageObject source) {
        super(required);
        this.setChoices(SubType.getCreatureTypes().stream().map(SubType::toString).collect(Collectors.toCollection(LinkedHashSet::new)));
        this.setMessage(chooseMessage);
        if (source != null) {
            this.setSubMessage(source.getIdName());
        }
        this.setSearchEnabled(true);
    }

    protected ChoiceCreatureType(final ChoiceCreatureType choice) {
        super(choice);
    }

    @Override
    public ChoiceCreatureType copy() {
        return new ChoiceCreatureType(this);
    }
}
