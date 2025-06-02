package mage.cards.s;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SundialDawnTyrant extends CardImpl {

    public SundialDawnTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private SundialDawnTyrant(final SundialDawnTyrant card) {
        super(card);
    }

    @Override
    public SundialDawnTyrant copy() {
        return new SundialDawnTyrant(this);
    }
}
