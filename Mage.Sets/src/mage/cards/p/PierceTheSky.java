
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class PierceTheSky extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public PierceTheSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Pierce the Sky deals 7 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private PierceTheSky(final PierceTheSky card) {
        super(card);
    }

    @Override
    public PierceTheSky copy() {
        return new PierceTheSky(this);
    }
}
