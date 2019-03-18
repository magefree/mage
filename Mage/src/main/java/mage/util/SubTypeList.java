package mage.util;

import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SubTypeList extends ArrayList<SubType> {

    public SubTypeList(SubType firstSubType) {
        super();
        this.add(firstSubType);
    }

    public SubTypeList(SubType... subTypesList) {
        super();
        Collections.addAll(this, subTypesList);
    }

    public boolean add(SubType... subTypes) {
        return Collections.addAll(this, subTypes);
    }

    public boolean removeAll(SubType... subTypes) {
        return super.removeAll(Arrays.stream(subTypes)
                .collect(Collectors.toList()));
    }
}
