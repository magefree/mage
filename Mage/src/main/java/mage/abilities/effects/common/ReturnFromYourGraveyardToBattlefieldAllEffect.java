package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 * @author xenohedron
 */
public class ReturnFromYourGraveyardToBattlefieldAllEffect extends OneShotEffect {

    private final FilterCard filter;
    private final boolean tapped;

    public ReturnFromYourGraveyardToBattlefieldAllEffect(FilterCard filter) {
        this(filter, false);
    }

    public ReturnFromYourGraveyardToBattlefieldAllEffect(FilterCard filter, boolean tapped) {
        super(Outcome.PutCardInPlay);
        this.filter = filter;
        this.tapped = tapped;
        staticText = "return all " + filter.getMessage() + " from your graveyard to the battlefield" + (tapped ? " tapped" : "");
    }

    protected ReturnFromYourGraveyardToBattlefieldAllEffect(final ReturnFromYourGraveyardToBattlefieldAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.tapped = effect.tapped;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        return controller.moveCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game),
                Zone.BATTLEFIELD, source, game, tapped, false, false, null);
    }

    @Override
    public ReturnFromYourGraveyardToBattlefieldAllEffect copy() {
        return new ReturnFromYourGraveyardToBattlefieldAllEffect(this);
    }
}
