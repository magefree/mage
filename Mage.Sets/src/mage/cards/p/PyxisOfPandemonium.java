
package mage.cards.p;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class PyxisOfPandemonium extends CardImpl {

    public PyxisOfPandemonium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {T}: Each player exiles the top card of their library face down.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PyxisOfPandemoniumExileEffect(), new TapSourceCost()));
        // {7}, {T}, Sacrifice Pyxis of Pandemonium: Each player turns face up all cards he or she owns exiled with Pyxis of Pandemonium, then puts all permanent cards among them onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PyxisOfPandemoniumPutOntoBattlefieldEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    public PyxisOfPandemonium(final PyxisOfPandemonium card) {
        super(card);
    }

    @Override
    public PyxisOfPandemonium copy() {
        return new PyxisOfPandemonium(this);
    }
}

class PyxisOfPandemoniumExileEffect extends OneShotEffect {

    public PyxisOfPandemoniumExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player exiles the top card of their library face down";
    }

    public PyxisOfPandemoniumExileEffect(final PyxisOfPandemoniumExileEffect effect) {
        super(effect);
    }

    @Override
    public PyxisOfPandemoniumExileEffect copy() {
        return new PyxisOfPandemoniumExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            Map<String, UUID> exileIds;
            String valueKey = CardUtil.getObjectZoneString("exileIds", sourceObject, game);
            Object object = game.getState().getValue(valueKey);
            if (object instanceof Map) {
                exileIds = (Map<String, UUID>) object;
            } else {
                exileIds = new HashMap<>();
                game.getState().setValue(valueKey, exileIds);
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {

                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getLibrary().hasCards()) {
                        Card card = player.getLibrary().getFromTop(game);
                        String exileKey = playerId.toString() + CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()).toString();
                        UUID exileId = exileIds.computeIfAbsent(exileKey, k -> UUID.randomUUID());
                        player.moveCardsToExile(card, source, game, false, exileId, sourceObject.getIdName() + " (" + player.getName() + ')');
                        card.setFaceDown(true, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class PyxisOfPandemoniumPutOntoBattlefieldEffect extends OneShotEffect {

    public PyxisOfPandemoniumPutOntoBattlefieldEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Each player turns face up all cards he or she owns exiled with {this}, then puts all permanent cards among them onto the battlefield";
    }

    public PyxisOfPandemoniumPutOntoBattlefieldEffect(final PyxisOfPandemoniumPutOntoBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PyxisOfPandemoniumPutOntoBattlefieldEffect copy() {
        return new PyxisOfPandemoniumPutOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Map<String, UUID> exileIds;
            String valueKey = CardUtil.getObjectZoneString("exileIds", sourceObject, game);
            Object object = game.getState().getValue(valueKey);
            if (object instanceof Map) {
                exileIds = (Map<String, UUID>) object;
            } else {
                return true;
            }
            Cards cardsToBringIntoPlay = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    String exileKey = playerId.toString() + CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()).toString();
                    UUID exileId = exileIds.get(exileKey);
                    if (exileId != null) {
                        ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                        if (exileZone != null) {
                            for (Card card : exileZone.getCards(game)) {
                                card.setFaceDown(false, game);
                                if (card.isPermanent()) {
                                    cardsToBringIntoPlay.add(card);
                                }
                            }
                        }
                    }
                }
            }
            controller.moveCards(cardsToBringIntoPlay.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }
        return false;
    }
}
