
package mage.cards.h;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HurlyBurly extends CardImpl {

    private static final FilterCreaturePermanent filterWithoutFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterWithoutFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public HurlyBurly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        this.getSpellAbility().addEffect(new DamageAllEffect(1, StaticFilters.FILTER_CREATURE_FLYING));
        Mode mode = new Mode(new DamageAllEffect(1, filterWithoutFlying));
        this.getSpellAbility().addMode(mode);
    }

    private HurlyBurly(final HurlyBurly card) {
        super(card);
    }

    @Override
    public HurlyBurly copy() {
        return new HurlyBurly(this);
    }
}
