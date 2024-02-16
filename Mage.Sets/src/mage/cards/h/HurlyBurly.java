
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Loki
 */
public final class HurlyBurly extends CardImpl {

    private static final FilterCreaturePermanent filterWithoutFlying = new FilterCreaturePermanent("creature without flying");
    private static final FilterCreaturePermanent filterWithFlying = new FilterCreaturePermanent("creature with flying");

    static {
        filterWithoutFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filterWithFlying.add(new AbilityPredicate(FlyingAbility.class));
    }

    public HurlyBurly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        this.getSpellAbility().addEffect(new DamageAllEffect(1, filterWithFlying));
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
