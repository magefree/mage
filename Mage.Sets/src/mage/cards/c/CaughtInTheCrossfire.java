package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.OutlawPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaughtInTheCrossfire extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("outlaw creature");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("non-outlaw creature");

    static {
        filter.add(OutlawPredicate.instance);
        filter.add(Predicates.not(OutlawPredicate.instance));
    }

    public CaughtInTheCrossfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- Caught in the Crossfire deals 2 damage to each outlaw creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {1} -- Caught in the Crossfire deals 2 damage to each non-outlaw creature.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(2, filter2))
                .withCost(new GenericManaCost(1)));
    }

    private CaughtInTheCrossfire(final CaughtInTheCrossfire card) {
        super(card);
    }

    @Override
    public CaughtInTheCrossfire copy() {
        return new CaughtInTheCrossfire(this);
    }
}
