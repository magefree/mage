
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class ReturnToHandChosenControlledPermanentEffect extends ReturnToHandChosenPermanentEffect {

    public ReturnToHandChosenControlledPermanentEffect(FilterControlledPermanent filter) {
        super(filter);
    }

    public ReturnToHandChosenControlledPermanentEffect(FilterControlledPermanent filter, int number) {
        super(filter, number);
    }

    public ReturnToHandChosenControlledPermanentEffect(ReturnToHandChosenControlledPermanentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        this.targetPointer = new FixedTarget(source.getControllerId());
        return super.apply(game, source);
    }

    @Override
    public ReturnToHandChosenControlledPermanentEffect copy() {
        return new ReturnToHandChosenControlledPermanentEffect(this);
    }

    @Override
    protected String getText() {
        StringBuilder sb = new StringBuilder("return");
        if (!filter.getMessage().startsWith("another")) {
            sb.append(' ');
            if(filter.getMessage().startsWith("a")){
                sb.append("an");
            }
            else {
                sb.append(CardUtil.numberToText(number, "a"));
            }
        }
        sb.append(' ').append(filter.getMessage());
        if (number > 1) {
            sb.append(" to their owner's hand");
        } else {
            sb.append(" to its owner's hand");
        }
        return sb.toString();
    }

}
