
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.combat.CombatGroup;

/**
 *
 * @author North
 */
public final class BlessedReversal extends CardImpl {

    public BlessedReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // You gain 3 life for each creature attacking you.
        this.getSpellAbility().addEffect(new GainLifeEffect(new BlessedReversalCount()));
    }

    private BlessedReversal(final BlessedReversal card) {
        super(card);
    }

    @Override
    public BlessedReversal copy() {
        return new BlessedReversal(this);
    }
}

class BlessedReversalCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getDefenderId().equals(sourceAbility.getControllerId())) {
                count += combatGroup.getAttackers().size();
            }
        }
        return count * 3;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature attacking you";
    }

    @Override
    public String toString() {
        return "3";
    }
}
