
package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class VitalizingCascade extends CardImpl {

    public VitalizingCascade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{W}");

        // You gain X plus 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(VitalizingCascadeValue.instance));
    }

    private VitalizingCascade(final VitalizingCascade card) {
        super(card);
    }

    @Override
    public VitalizingCascade copy() {
        return new VitalizingCascade(this);
    }
}

enum VitalizingCascadeValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return sourceAbility.getManaCosts().getX() + 3;
    }

    @Override
    public VitalizingCascadeValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X plus 3";
    }
}

