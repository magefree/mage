
package mage.client.util;

import mage.cards.repository.CardInfo;

/**
 *
 * @author fwannmacher
 */
public class NaturalOrderCardNumberComparator extends NaturalOrderComparator {
    @Override
    public int compare(Object o1, Object o2) {
        CardInfo cardInfo1 = (CardInfo) o1;
        CardInfo cardInfo2 = (CardInfo) o2;

        return super.compare(cardInfo1.getCardNumber(), cardInfo2.getCardNumber());
    }
}
