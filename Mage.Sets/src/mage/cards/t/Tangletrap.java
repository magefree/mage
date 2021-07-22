package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Tangletrap extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Tangletrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose one —
        // • Tangletrap deals 5 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // • Destroy target artifact.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private Tangletrap(final Tangletrap card) {
        super(card);
    }

    @Override
    public Tangletrap copy() {
        return new Tangletrap(this);
    }
}
