package mage.cards.m;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MurasaBrute extends CardImpl {

    public MurasaBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private MurasaBrute(final MurasaBrute card) {
        super(card);
    }

    @Override
    public MurasaBrute copy() {
        return new MurasaBrute(this);
    }
}
