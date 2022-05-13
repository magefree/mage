package mage.cards.x;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.CanPlayCardControllerEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class XandersPact extends CardImpl {

    public XandersPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Casualty 2
        this.addAbility(new CasualtyAbility(this, 2));

        // Each opponent exiles the top card of their library. You may cast spells from among those cards this turn. If you cast a spell this way, pay life equal to that spell's mana value rather than pay its mana cost.
        this.getSpellAbility().addEffect(new XandersPactExileEffect());
    }

    private XandersPact(final XandersPact card) {
        super(card);
    }

    @Override
    public XandersPact copy() {
        return new XandersPact(this);
    }
}

class XandersPactExileEffect extends OneShotEffect {

    XandersPactExileEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles the top card of their library. You may cast spells " +
                "from among those cards this turn. If you cast a spell this way, " +
                "pay life equal to that spell's mana value rather than pay its mana cost";
    }

    private XandersPactExileEffect(final XandersPactExileEffect effect) {
        super(effect);
    }

    @Override
    public XandersPactExileEffect copy() {
        return new XandersPactExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards) {
            game.addEffect(new XandersPactCastEffect(game, card), source);
        }
        return true;
    }
}

class XandersPactCastEffect extends CanPlayCardControllerEffect {

    XandersPactCastEffect(Game game, Card card) {
        super(game, card.getMainCard().getId(), card.getZoneChangeCounter(game), Duration.EndOfTurn);
    }

    private XandersPactCastEffect(final XandersPactCastEffect effect) {
        super(effect);
    }

    @Override
    public XandersPactCastEffect copy() {
        return new XandersPactCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!super.applies(objectId, source, affectedControllerId, game)) {
            return false;
        }
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck.isLand(game)) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.add(new PayLifeCost(cardToCheck.getManaValue()));
        newCosts.addAll(cardToCheck.getSpellAbility().getCosts());
        controller.setCastSourceIdWithAlternateMana(cardToCheck.getId(), null, newCosts);
        return true;
    }
}
