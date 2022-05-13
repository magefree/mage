
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author Plopmans
 */
public class ReturnToHandChosenPermanentEffect extends OneShotEffect {

    protected final FilterControlledPermanent filter;
    protected int number;

    public ReturnToHandChosenPermanentEffect(FilterControlledPermanent filter) {
        this(filter, 1);
    }

    public ReturnToHandChosenPermanentEffect(FilterControlledPermanent filter, int number) {
        super(Outcome.ReturnToHand);
        this.filter = filter;
        this.number = number;
        this.staticText = getText();
    }

    public ReturnToHandChosenPermanentEffect(final ReturnToHandChosenPermanentEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.number = effect.number;
    }

    @Override
    public ReturnToHandChosenPermanentEffect copy() {
        return new ReturnToHandChosenPermanentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            int available = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            if (available > 0) {
                TargetControlledPermanent target = new TargetControlledPermanent(Math.min(number, available), number, filter, true);
                if (player.chooseTarget(this.outcome, target, source, game)) {
                    player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }

    protected String getText() {
        StringBuilder sb = new StringBuilder("that player returns ");
        if (!filter.getMessage().startsWith("another")) {
            sb.append(CardUtil.numberToText(number, "a"));
        }
        sb.append(' ').append(filter.getMessage());
        sb.append(" they control");
        if (number > 1) {
            sb.append(" to their owner's hand");
        } else {
            sb.append(" to its owner's hand");
        }
        return sb.toString();
    }
}
