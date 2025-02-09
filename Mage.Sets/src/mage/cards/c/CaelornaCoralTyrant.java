package mage.cards.c;

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
public final class CaelornaCoralTyrant extends CardImpl {

    public CaelornaCoralTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(8);
    }

    private CaelornaCoralTyrant(final CaelornaCoralTyrant card) {
        super(card);
    }

    @Override
    public CaelornaCoralTyrant copy() {
        return new CaelornaCoralTyrant(this);
    }
}
