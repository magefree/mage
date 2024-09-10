package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class ReturnFromGraveyardAtRandomEffect extends OneShotEffect {

    private final FilterCard filter;
    private final Zone zone;
    private final int number;

    /**
     * Returns one card from graveyard at random to the specified zone
     * @param filter card filter (text should NOT include "from your graveyard")
     * @param zone only Zone.HAND and Zone.BATTLEFIELD currently supported
     */
    public ReturnFromGraveyardAtRandomEffect(FilterCard filter, Zone zone) {
        this(filter, zone, 1);
    }

    /**
     * @param filter card filter (text should NOT include "from your graveyard")
     * @param zone only Zone.HAND and Zone.BATTLEFIELD currently supported
     * @param number number of cards to return at random
     */
    public ReturnFromGraveyardAtRandomEffect(FilterCard filter, Zone zone, int number) {
        super(Outcome.ReturnToHand);
        this.filter = filter;
        this.zone = zone;
        this.number = number;
        this.staticText = "return " +
                (number == 1 ? CardUtil.addArticle(filter.getMessage()) : CardUtil.numberToText(number) + " " + filter.getMessage()) +
                " at random from your graveyard to " + (zone == Zone.HAND ? "your hand" : "the battlefield");
    }

    protected ReturnFromGraveyardAtRandomEffect(final ReturnFromGraveyardAtRandomEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.zone = effect.zone;
        this.number = effect.number;
    }

    @Override
    public ReturnFromGraveyardAtRandomEffect copy() {
        return new ReturnFromGraveyardAtRandomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(Math.min(player.getGraveyard().size(), number), filter);
        target.withNotTarget(true);
        target.setRandom(true);
        target.chooseTarget(outcome, player.getId(), source, game);
        return player.moveCards(new CardsImpl(target.getTargets()), zone, source, game);
    }
}
