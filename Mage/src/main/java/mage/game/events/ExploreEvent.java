package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author Grath
 */
public class ExploreEvent extends GameEvent {

    private int jump = 1;

    public ExploreEvent(Permanent permanent, Ability source, int amount, boolean scryFirst) {
        super(EventType.EXPLORE, permanent.getId(), source, permanent.getControllerId(), amount, scryFirst);
    }

    public void DoubleExplores() {
        if (flag) {
            jump = CardUtil.overflowMultiply(jump, 2);
        }
        else {
            setAmount(CardUtil.overflowMultiply(amount, 2));
        }
    }

    public int getJump() {
        return jump;
    }
}
