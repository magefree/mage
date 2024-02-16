
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ChaosCharm extends CardImpl {

   private static final FilterPermanent filter = new FilterPermanent("Wall");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public ChaosCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Choose one - Destroy target Wall
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        // or Chaos Charm deals 1 damage to target creature
        Mode mode = new Mode(new DamageTargetEffect(1));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or target creature gains haste until end of turn.
        mode = new Mode(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private ChaosCharm(final ChaosCharm card) {
        super(card);
    }

    @Override
    public ChaosCharm copy() {
        return new ChaosCharm(this);
    }
}
