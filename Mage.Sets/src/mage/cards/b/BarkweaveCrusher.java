package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.EnlistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarkweaveCrusher extends CardImpl {

    public BarkweaveCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Enlist
        this.addAbility(new EnlistAbility());
    }

    private BarkweaveCrusher(final BarkweaveCrusher card) {
        super(card);
    }

    @Override
    public BarkweaveCrusher copy() {
        return new BarkweaveCrusher(this);
    }
}
