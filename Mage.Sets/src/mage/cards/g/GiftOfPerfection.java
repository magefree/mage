package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AssimilateTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author JayDi85
 */
public final class GiftOfPerfection extends CardImpl {

    public GiftOfPerfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Each opponent reveals cards from the top of their library until they reveal a creature card. Assimilate one of the creature cards revealed this way. Then each opponent puts the other cards they revealed this way on the bottom of their library in a random order.
        this.getSpellAbility().addEffect(new GiftOfPerfectionEffect());
    }

    private GiftOfPerfection(final GiftOfPerfection card) {
        super(card);
    }

    @Override
    public GiftOfPerfection copy() {
        return new GiftOfPerfection(this);
    }
}

class GiftOfPerfectionEffect extends OneShotEffect {

    GiftOfPerfectionEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Each opponent reveals cards from the top of their library until they reveal a creature card. "
                + "Assimilate one of the creature cards revealed this way. "
                + "Then each opponent puts the other cards they revealed this way on the bottom of their library in a random order. "
                + "<i>(To assimilate a card, put it onto the battlefield under your control with a +1/+1 counter. "
                + "It's a Borg artifact creature and loses all other creature types.)</i>";
    }

    private GiftOfPerfectionEffect(final GiftOfPerfectionEffect effect) {
        super(effect);
    }

    @Override
    public GiftOfPerfectionEffect copy() {
        return new GiftOfPerfectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Map<UUID, Cards> revealedByOpponent = new LinkedHashMap<>();
        Cards revealedCreatures = new CardsImpl();

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (!controller.hasOpponent(playerId, game)) {
                continue;
            }
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            Cards revealed = new CardsImpl();
            for (Card card : opponent.getLibrary().getCards(game)) {
                revealed.add(card);
                if (card.isCreature(game)) {
                    revealedCreatures.add(card);
                    break;
                }
            }
            if (!revealed.isEmpty()) {
                opponent.revealCards(source, revealed, game);
                revealedByOpponent.put(playerId, revealed);
            }
        }

        if (revealedByOpponent.isEmpty()) {
            return false;
        }

        Card chosenCreature = null;
        switch (revealedCreatures.size()) {
            case 0:
                break;
            case 1:
                chosenCreature = revealedCreatures.getCards(game).iterator().next();
                break;
            default:
                TargetCard target = new TargetCard(Zone.LIBRARY, StaticFilters.FILTER_CARD_CREATURE);
                target.withNotTarget(true);
                target.withChooseHint("to assimilate");
                controller.choose(Outcome.PutCreatureInPlay, revealedCreatures, target, source, game);
                chosenCreature = game.getCard(target.getFirstTarget());
        }

        if (chosenCreature != null) {
            Cards ownerRevealed = revealedByOpponent.get(chosenCreature.getOwnerId());
            if (ownerRevealed != null) {
                ownerRevealed.remove(chosenCreature);
            }
            AssimilateTargetEffect.assimilateCard(controller, chosenCreature, source, game);
        }

        for (Map.Entry<UUID, Cards> entry : revealedByOpponent.entrySet()) {
            Player opponent = game.getPlayer(entry.getKey());
            Cards rest = entry.getValue();
            if (opponent != null && !rest.isEmpty()) {
                rest.retainZone(Zone.LIBRARY, game);
                opponent.putCardsOnBottomOfLibrary(rest, game, source, false);
            }
        }
        return true;
    }
}
