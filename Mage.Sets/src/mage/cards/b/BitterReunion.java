package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BitterReunion extends CardImpl {

    public BitterReunion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // When Bitter Reunion enters the battlefield, you may discard a card. If you do, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2), new DiscardCardCost()
        )));

        // {1}, Sacrifice Bitter Reunion: Creatures you control gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BitterReunion(final BitterReunion card) {
        super(card);
    }

    @Override
    public BitterReunion copy() {
        return new BitterReunion(this);
    }
}
