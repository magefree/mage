
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PathbreakerIbex extends CardImpl {

    public PathbreakerIbex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.GOAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Pathbreaker Ibex attacks, creatures you control gain trample and get +X/+X until end of turn, where X is the greatest power among creatures you control.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("creatures you control gain trample"), false);
        ability.addEffect(new BoostControlledEffect(
                GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES, GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES,
                Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ).setText("and get +X/+X until end of turn, where X is the greatest power among creatures you control"));
        ability.addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());
        this.addAbility(ability);
    }

    private PathbreakerIbex(final PathbreakerIbex card) {
        super(card);
    }

    @Override
    public PathbreakerIbex copy() {
        return new PathbreakerIbex(this);
    }
}