package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KickoffCelebrations extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures and Vehicles");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public KickoffCelebrations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this enchantment enters, you may discard a card. If you do, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardCardCost())
        ));

        // Max speed -- Sacrifice this enchantment: Creatures and Vehicles you control gain haste until end of turn.
        this.addAbility(new MaxSpeedAbility(new SimpleActivatedAbility(
                new GainAbilityControlledEffect(
                        HasteAbility.getInstance(), Duration.EndOfTurn, filter
                ), new SacrificeSourceCost()
        )));
    }

    private KickoffCelebrations(final KickoffCelebrations card) {
        super(card);
    }

    @Override
    public KickoffCelebrations copy() {
        return new KickoffCelebrations(this);
    }
}
