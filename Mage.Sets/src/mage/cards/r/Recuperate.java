
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 * @author DimitarK
 */
public final class Recuperate extends CardImpl {

    public Recuperate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // Choose one - You gain 6 life;
        this.getSpellAbility().addEffect(new GainLifeEffect(6));
        // or prevent the next 6 damage that would be dealt to target creature this turn.
        Mode mode = new Mode(new PreventDamageToTargetEffect(Duration.EndOfTurn, 6));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private Recuperate(final Recuperate card) {
        super(card);
    }

    @Override
    public Recuperate copy() {
        return new Recuperate(this);
    }
}
