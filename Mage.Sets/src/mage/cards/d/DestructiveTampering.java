
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author spjspj
 */
public final class DestructiveTampering extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures without flying");
    
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public DestructiveTampering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Choose one
        // * Destroy target artifact
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Creatures without flying can't block this turn.
        Mode mode = new Mode(new CantBlockAllEffect(filter, Duration.EndOfTurn));
        this.getSpellAbility().addMode(mode);
    }

    private DestructiveTampering(final DestructiveTampering card) {
        super(card);
    }

    @Override
    public DestructiveTampering copy() {
        return new DestructiveTampering(this);
    }
}
