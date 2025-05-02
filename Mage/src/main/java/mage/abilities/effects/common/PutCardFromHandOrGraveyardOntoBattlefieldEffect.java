package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class PutCardFromHandOrGraveyardOntoBattlefieldEffect extends OneShotEffect {

    private final FilterCard filter;
    private final boolean tapped;

    /**
     * @param filter for selecting a card (don't include zone-related text in filter message)
     * @param tapped ETB tapped if true
     */
    public PutCardFromHandOrGraveyardOntoBattlefieldEffect(FilterCard filter, boolean tapped) {
        super(Outcome.PutCardInPlay);
        this.filter = filter;
        this.tapped = tapped;
        this.staticText = "you may put " + CardUtil.addArticle(filter.getMessage())
                + " from your hand or graveyard onto the battlefield" + (tapped ? " tapped" : "");
    }

    private PutCardFromHandOrGraveyardOntoBattlefieldEffect(final PutCardFromHandOrGraveyardOntoBattlefieldEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.tapped = effect.tapped;
    }

    @Override
    public PutCardFromHandOrGraveyardOntoBattlefieldEffect copy() {
        return new PutCardFromHandOrGraveyardOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
        cards.addAllCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
        target.withNotTarget(true);
        controller.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, false, null);
        }
        return true;
    }

}
