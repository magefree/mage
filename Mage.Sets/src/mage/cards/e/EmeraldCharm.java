
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author fireshoes
 */
public final class EmeraldCharm extends CardImpl {
    
    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("non-Aura enchantment");
    
    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.AURA)));
    }

    public EmeraldCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Choose one - Untap target permanent;
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

        // or destroy target non-Aura enchantment;
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetEnchantmentPermanent(filter));
        this.getSpellAbility().addMode(mode);

        // or target creature loses flying until end of turn.
        mode = new Mode();
        mode.getEffects().add(new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    public EmeraldCharm(final EmeraldCharm card) {
        super(card);
    }

    @Override
    public EmeraldCharm copy() {
        return new EmeraldCharm(this);
    }
}
