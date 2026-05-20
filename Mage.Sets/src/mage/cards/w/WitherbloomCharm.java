package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitherbloomCharm extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public WitherbloomCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // Choose one --
        // * You may sacrifice a permanent. If you do, draw two cards.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT)
        ));

        // * You gain 5 life.
        this.getSpellAbility().addMode(new Mode(new GainLifeEffect(5)));

        // * Destroy target nonland permanent with mana value 2 or less.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));
    }

    private WitherbloomCharm(final WitherbloomCharm card) {
        super(card);
    }

    @Override
    public WitherbloomCharm copy() {
        return new WitherbloomCharm(this);
    }
}
