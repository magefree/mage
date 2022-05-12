
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class FeverCharm extends CardImpl {

   private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wizard creature");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

   public FeverCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Choose one - Target creature gains haste until end of turn
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // or target creature gets +2/+0 until end of turn
        Mode mode = new Mode(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or Fever Charm deals 3 damage to target Wizard creature.
        mode = new Mode(new DamageTargetEffect(3));
        mode.addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private FeverCharm(final FeverCharm card) {
        super(card);
    }

    @Override
    public FeverCharm copy() {
        return new FeverCharm(this);
    }
}
