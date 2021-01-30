package mage.client.util.comparators;

import mage.constants.CardType;
import mage.view.CardView;

import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class CardViewCardTypeComparator implements CardViewComparator {

    @Override
    public int compare(CardView o1, CardView o2) {
        return o1.getCardTypes().toString().compareTo(o2.getCardTypes().toString());
    }

    @Override
    public String getCategoryName(CardView sample) {
        return sample.getCardTypes()
                .stream()
                .map(CardType::toString)
                .collect(Collectors.joining(", "));
    }
}
