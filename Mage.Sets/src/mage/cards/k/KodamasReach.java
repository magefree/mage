package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
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
public final class KodamasReach extends CardImpl {

    public KodamasReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.subtype.add(SubType.ARCANE);

        // Search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new KodamasReachEffect());
    }

    private KodamasReach(final KodamasReach card) {
        super(card);
    }

    @Override
    public KodamasReach copy() {
        return new KodamasReach(this);
    }
}

class KodamasReachEffect extends OneShotEffect {

    protected static final FilterCard filter = new FilterCard("card to put on the battlefield tapped");

    public KodamasReachEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "search your library for up to two basic land cards, reveal those cards, " +
                "put one onto the battlefield tapped and the other into your hand, then shuffle";
    }

    public KodamasReachEffect(final KodamasReachEffect effect) {
        super(effect);
    }

    @Override
    public KodamasReachEffect copy() {
        return new KodamasReachEffect(this);
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
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                        revealed.remove(card);
                    }
                    Set<Card> cards = revealed.getCards(game);
                    card = cards.isEmpty() ? null : cards.iterator().next();
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                } else if (target.getTargets().size() == 1) {
                    Set<Card> cards = revealed.getCards(game);
                    Card card = cards.isEmpty() ? null : cards.iterator().next();
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    }
                }

            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;

    }

}
