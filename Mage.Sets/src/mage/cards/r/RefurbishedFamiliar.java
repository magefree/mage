package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class RefurbishedFamiliar extends CardImpl {

    public RefurbishedFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Refurbished Familiar enters the battlefield, each opponent discards a card. For each opponent who can't, you draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RefurbishedFamiliarEffect()));
    }

    private RefurbishedFamiliar(final RefurbishedFamiliar card) {
        super(card);
    }

    @Override
    public RefurbishedFamiliar copy() {
        return new RefurbishedFamiliar(this);
    }
}

class RefurbishedFamiliarEffect extends OneShotEffect {

    RefurbishedFamiliarEffect() {
        super(Outcome.DrawCard);
        staticText = "each opponent discards a card. For each opponent who can't, you draw a card";
    }

    private RefurbishedFamiliarEffect(final RefurbishedFamiliarEffect effect) {
        super(effect);
    }

    @Override
    public RefurbishedFamiliarEffect copy() {
        return new RefurbishedFamiliarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (!game.getOpponents(source.getControllerId()).contains(playerId)) {
                continue;
            }
            int numberOfCardsToDiscard = Math.min(1, player.getHand().size());
            Cards cards = new CardsImpl();
            Target target = new TargetDiscard(numberOfCardsToDiscard, numberOfCardsToDiscard, StaticFilters.FILTER_CARD, playerId);
            player.chooseTarget(Outcome.Discard, target, source, game);
            cards.addAll(target.getTargets());
            cardsToDiscard.put(playerId, cards);
        }

        // discard all chosen cards
        int toDraw = 0;
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (!game.getOpponents(source.getControllerId()).contains(playerId)) {
                continue;
            }
            Cards cards = cardsToDiscard.get(playerId);
            if (cards != null && !cards.isEmpty()) {
                player.discard(cardsToDiscard.get(playerId), false, source, game);
            } else {
                toDraw++;
            }
        }
        controller.drawCards(toDraw, source, game);
        return true;
    }

}