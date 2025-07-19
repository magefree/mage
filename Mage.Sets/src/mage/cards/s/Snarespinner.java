package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Snarespinner extends CardImpl {

    public Snarespinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Snarespinner blocks a creature with flying, Snarespinner gets +2/+0 until end of turn.
        this.addAbility(new BlocksCreatureTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), StaticFilters.FILTER_CREATURE_FLYING, false));
    }

    private Snarespinner(final Snarespinner card) {
        super(card);
    }

    @Override
    public Snarespinner copy() {
        return new Snarespinner(this);
    }
}
