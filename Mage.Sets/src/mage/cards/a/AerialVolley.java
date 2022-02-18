package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public final class AerialVolley extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public AerialVolley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Aerial Volley deals 3 damage divided as you choose among one, two, or three target creatures with flying.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(3, filter));
    }

    private AerialVolley(final AerialVolley card) {
        super(card);
    }

    @Override
    public AerialVolley copy() {
        return new AerialVolley(this);
    }
}
