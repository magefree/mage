package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrderOfTheAlabasterHost extends CardImpl {

    public OrderOfTheAlabasterHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Whenever Order of the Alabaster Host becomes blocked by a creature, that creature gets -1/-1 until end of turn.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new BoostTargetEffect(-1, -1), false));
    }

    private OrderOfTheAlabasterHost(final OrderOfTheAlabasterHost card) {
        super(card);
    }

    @Override
    public OrderOfTheAlabasterHost copy() {
        return new OrderOfTheAlabasterHost(this);
    }
}
