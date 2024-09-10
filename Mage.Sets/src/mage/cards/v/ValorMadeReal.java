
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ValorMadeReal extends CardImpl {

    public ValorMadeReal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target creature can block any number of creatures this turn.
        Ability gainedAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(0));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(gainedAbility, Duration.EndOfTurn).setText("target creature can block any number of creatures this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ValorMadeReal(final ValorMadeReal card) {
        super(card);
    }

    @Override
    public ValorMadeReal copy() {
        return new ValorMadeReal(this);
    }
}
