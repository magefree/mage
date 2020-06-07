package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class AdherentOfHope extends CardImpl {

    public AdherentOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control a Basri planeswalker, put a +1/+1 counter on Adherent of Hope.
    }

    private AdherentOfHope(final AdherentOfHope card) {
        super(card);
    }

    @Override
    public AdherentOfHope copy() {
        return new AdherentOfHope(this);
    }
}
