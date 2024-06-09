package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.*;

/**
 *
 * @author LevelX2
 */
public final class GhastlyConscription extends CardImpl {

    public GhastlyConscription(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Exile all creature cards from target player's graveyard in a face-down pile, shuffle that pile, then manifest those cards.<i> (To manifest a card, put it onto the battlefield face down as a 2/2 creature. Turn it face up at any time for its mana cost if it's a creature card.)</i>
        this.getSpellAbility().addEffect(new GhastlyConscriptionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private GhastlyConscription(final GhastlyConscription card) {
        super(card);
    }

    @Override
    public GhastlyConscription copy() {
        return new GhastlyConscription(this);
    }
}

class GhastlyConscriptionEffect extends OneShotEffect {

    GhastlyConscriptionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile all creature cards from target player's graveyard in a face-down pile, " +
                "shuffle that pile, then manifest those cards. " +
                "<i>(To manifest a card, put it onto the battlefield face down as a 2/2 creature. " +
                "Turn it face up at any time for its mana cost if it's a creature card.)</i>";
    }

    private GhastlyConscriptionEffect(final GhastlyConscriptionEffect effect) {
        super(effect);
    }

    @Override
    public GhastlyConscriptionEffect copy() {
        return new GhastlyConscriptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }
        List<Card> cardsToManifest = new ArrayList<>();
        for (Card card : targetPlayer.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
            cardsToManifest.add(card);
            controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.GRAVEYARD, true);
            card.setFaceDown(true, game);
        }
        if (cardsToManifest.isEmpty()) {
            return true;
        }
        Collections.shuffle(cardsToManifest);
        game.informPlayers(controller.getLogName() + " shuffles the face-down pile");
        game.getState().processAction(game);
        ManifestEffect.doManifestCards(game, source, controller, new LinkedHashSet<>(cardsToManifest));
        return true;
    }
}
