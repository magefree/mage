package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisturbingMirth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another enchantment or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ENCHANTMENT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public DisturbingMirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{R}");

        // When Disturbing Mirth enters, you may sacrifice another enchantment or creature. If you do, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new SacrificeTargetCost(filter))
        ));

        // When you sacrifice Disturbing Mirth, manifest dread.
        this.addAbility(new SacrificeSourceTriggeredAbility(new ManifestDreadEffect()));
    }

    private DisturbingMirth(final DisturbingMirth card) {
        super(card);
    }

    @Override
    public DisturbingMirth copy() {
        return new DisturbingMirth(this);
    }
}
