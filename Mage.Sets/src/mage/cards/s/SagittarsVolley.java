package mage.cards.s;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SagittarsVolley extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with flying");
    private static final FilterPermanent filter2
            = new FilterOpponentsCreaturePermanent("creature with flying your opponents control");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SagittarsVolley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Destroy target creature with flying. Sagittars' Volley deals 1 damage to each creature with flying your opponents control.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter2));
    }

    private SagittarsVolley(final SagittarsVolley card) {
        super(card);
    }

    @Override
    public SagittarsVolley copy() {
        return new SagittarsVolley(this);
    }
}
