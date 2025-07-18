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
public final class Skystinger extends CardImpl {

    public Skystinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever this creature blocks a creature with flying, this creature gets +5/+0 until end of turn.
        this.addAbility(new BlocksCreatureTriggeredAbility(
                new BoostSourceEffect(5, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_CREATURE_FLYING, false
        ));
    }

    private Skystinger(final Skystinger card) {
        super(card);
    }

    @Override
    public Skystinger copy() {
        return new Skystinger(this);
    }
}
