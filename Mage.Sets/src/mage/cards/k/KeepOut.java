package mage.cards.k;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeepOut extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public KeepOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one --
        // * Keep Out deals 4 damage to target tapped creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Destroy target enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));
    }

    private KeepOut(final KeepOut card) {
        super(card);
    }

    @Override
    public KeepOut copy() {
        return new KeepOut(this);
    }
}
