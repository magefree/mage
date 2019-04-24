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

    @Deprecated
    public boolean addAll(List<String> subtypes) {
        return addAll(subtypes.stream()
                .map(SubType::byDescription)
                .collect(Collectors.toList()));
    }

    @Deprecated
    public boolean removeAll(List<String> subtypes) {
        return removeAll(subtypes.stream()
                .map(SubType::byDescription)
                .collect(Collectors.toList()));
    }

    public boolean add(SubType... subTypes) {
        return Collections.addAll(this, subTypes);
    }

    public boolean removeAll(SubType... subTypes) {
        return super.removeAll(Arrays.stream(subTypes)
                .collect(Collectors.toList()));
    }

    @Deprecated
    public boolean add(String s) {
        SubType subType = SubType.byDescription(s);
        if (subType != null) {
            return add(subType);
        }
        return false;
    }

    @Deprecated
    public boolean contains(String s) {
        return contains(SubType.byDescription(s));
    }
}
