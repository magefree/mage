package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class RaphaelTheNightwatcher extends CardImpl {

    public RaphaelTheNightwatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Sneak {1}{R}{R}
        this.addAbility(new SneakAbility(this, "{1}{R}{R}"));

        // Attacking creatures you control have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_ATTACKING_CREATURES
        )));
    }

    private RaphaelTheNightwatcher(final RaphaelTheNightwatcher card) {
        super(card);
    }

    @Override
    public RaphaelTheNightwatcher copy() {
        return new RaphaelTheNightwatcher(this);
    }
}
