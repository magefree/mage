package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.CardType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;

/**
 *
 * @author weirddan455
 */
public class SharesCardTypePredicate implements Predicate<MageObject> {

    private final LinkedHashSet<CardType> cardTypes;

    public SharesCardTypePredicate(Collection<CardType> cardTypes) {
        this.cardTypes = new LinkedHashSet<>(cardTypes);
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        for (CardType type : input.getCardType(game)) {
            if (cardTypes.contains(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        Iterator<CardType> it = cardTypes.iterator();
        switch(cardTypes.size()) {
            case 0:
                return "";
            case 1:
                return it.next().toString().toLowerCase(Locale.ENGLISH);
            case 2:
                return it.next().toString().toLowerCase(Locale.ENGLISH) + " or " + it.next().toString().toLowerCase(Locale.ENGLISH);
            default: {
                StringBuilder sb = new StringBuilder();
                sb.append(it.next().toString().toLowerCase(Locale.ENGLISH));
                while (it.hasNext()) {
                    CardType type = it.next();
                    sb.append(", ");
                    if (!it.hasNext()) {
                        sb.append("or ");
                    }
                    sb.append(type.toString().toLowerCase(Locale.ENGLISH));
                }
                return sb.toString();
            }
        }
    }
}
