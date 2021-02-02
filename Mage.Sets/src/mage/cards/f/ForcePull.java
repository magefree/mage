
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class ForcePull extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with spaceflight");

    static {
        filter.add(new AbilityPredicate(SpaceflightAbility.class));
    }

    public ForcePull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Destroy targer creature with spaceflight.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Scry 3
        this.getSpellAbility().addEffect(new ScryEffect(3));
    }

    private ForcePull(final ForcePull card) {
        super(card);
    }

    @Override
    public ForcePull copy() {
        return new ForcePull(this);
    }
}
