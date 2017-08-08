package mage.util;

import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SubTypeList extends ArrayList<SubType> {

    public boolean addAll(List<String> subtypes) {
        return addAll(subtypes.stream().map(SubType::byDescription).collect(Collectors.toList()));
    }

    public boolean removeAll(List<String> subtypes){
        return removeAll(subtypes.stream().map(SubType::byDescription).collect(Collectors.toList()));
    }


    public boolean add(SubType... subTypes) {
        return Collections.addAll(this, subTypes);
    }

    public boolean add(String s) {
        return add(SubType.byDescription(s));
    }

    public boolean contains(String s) {
        return contains(SubType.byDescription(s));
    }
}
