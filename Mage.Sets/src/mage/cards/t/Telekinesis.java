
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Telekinesis extends CardImpl {

    public Telekinesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}");

        // Tap target creature. Prevent all combat damage that would be dealt by that creature this turn. It doesn't untap during its controller's next two untap steps.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new PreventCombatDamageBySourceEffect(Duration.EndOfTurn).setText("Prevent all combat damage that would be dealt by that creature this turn"));
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("It", true, null));
    }

    private Telekinesis(final Telekinesis card) {
        super(card);
    }

    @Override
    public Telekinesis copy() {
        return new Telekinesis(this);
    }
}
