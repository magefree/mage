package mage.client.util.comparators;

import mage.view.CardView;

import java.util.Comparator;

/**
 * @author JayDi85
 */
public interface CardViewComparator extends Comparator<CardView> {

    String getCategoryName(CardView sample);

}
