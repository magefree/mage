package mage.util;

import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class SubTypes extends ArrayList<SubType> {

    public SubTypes(SubType firstSubType) {
        super();
        this.add(firstSubType);
    }

    public SubTypes(SubType... subTypesList) {
        super();
        Collections.addAll(this, subTypesList);
    }

    public SubTypes(final SubTypes list) {
        this.addAll(list);
    }

    public boolean add(SubType... subTypes) {
        return Collections.addAll(this, subTypes);
    }

    public boolean removeAll(SubType... subTypes) {
        return super.removeAll(Arrays.stream(subTypes)
                .collect(Collectors.toList()));
    }
}
