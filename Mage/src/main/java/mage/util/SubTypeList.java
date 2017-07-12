package mage.util;

import mage.constants.SubType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubTypeList extends ArrayList<SubType> {


    public void add(int index, String s) {
        add(index, SubType.byDescription(s));
    }

    public void add(int index, SubType s) {
        super.add(index, s);
    }

    public boolean addAll(List<String> subtypes) {
        return addAll(subtypes.stream().map(SubType::byDescription).collect(Collectors.toList()));
    }

    public boolean removeAll(List<String> subtypes){
        return removeAll(subtypes.stream().map(SubType::byDescription).collect(Collectors.toList()));
    }

    public boolean add(SubType s) {
       return super.add(s);
    }

    public boolean add(String s) {
        return add(SubType.byDescription(s));
    }

    public boolean contains(String s) {
        return contains(SubType.byDescription(s));
    }
}
