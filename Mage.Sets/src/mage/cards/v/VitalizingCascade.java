
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public final class VitalizingCascade extends CardImpl {

    public VitalizingCascade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{G}{W}");

        // You gain X plus 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(new VitalizingCascadeValue()));
    }

    public VitalizingCascade(final VitalizingCascade card) {
        super(card);
    }

    @Override
    public VitalizingCascade copy() {
        return new VitalizingCascade(this);
    }
}

class VitalizingCascadeValue extends ManacostVariableValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return super.calculate(game, sourceAbility, effect) + 3;
    }

    @Override
    public VitalizingCascadeValue copy() {
        return new VitalizingCascadeValue();
    }

    @Override
    public String toString() {
        return "X plus 3";
    }
}

