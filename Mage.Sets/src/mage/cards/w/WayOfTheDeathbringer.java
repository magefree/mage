package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Beast44TrampleToken;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WayOfTheDeathbringer extends CardImpl {

    public WayOfTheDeathbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Deathbringer enters, empower Jace 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(5)));

        // Planeswalkers you control have "[-2]: You may sacrifice a creature. If you do, create a 4/4 green Beast creature token with trample."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            new LoyaltyAbility(
                new DoIfCostPaid(
                    new CreateTokenEffect(new Beast44TrampleToken()),
                    new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE)
                ),
                -2
            ),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"-2: You may sacrifice a creature. If you do, create a 4/4 green Beast creature token with trample.\""));
        this.addAbility(ability);
    }

    private WayOfTheDeathbringer(final WayOfTheDeathbringer card) {
        super(card);
    }

    @Override
    public WayOfTheDeathbringer copy() {
        return new WayOfTheDeathbringer(this);
    }
}
