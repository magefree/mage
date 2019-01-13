
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Winterflame extends CardImpl {

    public Winterflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{R}");


        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // *Tap target creature
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // *Winterflame deals 2 damage to target creature
        Mode mode = new Mode();
        mode.addEffect(new DamageTargetEffect(2));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

    }

    public Winterflame(final Winterflame card) {
        super(card);
    }

    @Override
    public Winterflame copy() {
        return new Winterflame(this);
    }
}
