package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Peregrination extends CardImpl {

    public Peregrination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Seach your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Shuffle your library, then scry 1.
        this.getSpellAbility().addEffect(new PeregrinationEffect());
        Effect effect = new ScryEffect(1);
        effect.concatBy(", then");
        this.getSpellAbility().addEffect(effect);
    }

    private Peregrination(final Peregrination card) {
        super(card);
    }

    @Override
    public Peregrination copy() {
        return new Peregrination(this);
    }
}

class PeregrinationEffect extends OneShotEffect {

    protected static final FilterCard filter = new FilterCard("card to put on the battlefield tapped");

    public PeregrinationEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Shuffle";
    }

    public PeregrinationEffect(final PeregrinationEffect effect) {
        super(effect);
    }

    @Override
    public PeregrinationEffect copy() {
        return new PeregrinationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND);
        if (controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards revealed = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    revealed.add(card);
                }
                controller.revealCards(sourceObject.getIdName(), revealed, game);
                if (target.getTargets().size() == 2) {
                    TargetCard target2 = new TargetCard(Zone.LIBRARY, filter);
                    controller.choose(Outcome.Benefit, revealed, target2, source, game);
                    Card card = revealed.get(target2.getFirstTarget(), game);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    revealed.remove(card);
                    Set<Card> cards = revealed.getCards(game);
                    card = cards.isEmpty() ? null : cards.iterator().next();
                    controller.moveCards(card, Zone.HAND, source, game);
                } else if (target.getTargets().size() == 1) {
                    Set<Card> cards = revealed.getCards(game);
                    Card card = cards.isEmpty() ? null : cards.iterator().next();
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                }

            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;

    }

}
