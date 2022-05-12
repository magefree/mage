
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class EnergyBolt extends CardImpl {

    public EnergyBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{W}");

        // Choose one - Energy Bolt deals X damage to target player; or target player gains X life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        Mode mode = new Mode(new GainLifeTargetEffect(ManacostVariableValue.REGULAR));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private EnergyBolt(final EnergyBolt card) {
        super(card);
    }

    @Override
    public EnergyBolt copy() {
        return new EnergyBolt(this);
    }
}
