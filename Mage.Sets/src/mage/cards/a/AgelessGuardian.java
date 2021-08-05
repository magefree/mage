package mage.cards.a;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgelessGuardian extends CardImpl {

    public AgelessGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
    }

    private AgelessGuardian(final AgelessGuardian card) {
        super(card);
    }

    @Override
    public AgelessGuardian copy() {
        return new AgelessGuardian(this);
    }
}
