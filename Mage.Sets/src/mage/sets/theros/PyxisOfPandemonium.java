/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.theros;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class PyxisOfPandemonium extends CardImpl<PyxisOfPandemonium> {

    public PyxisOfPandemonium(UUID ownerId) {
        super(ownerId, 220, "Pyxis of Pandemonium", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "THS";

        // {T}: Each player exiles the top card of his or her library face down.
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

class PyxisOfPandemoniumExileEffect extends OneShotEffect<PyxisOfPandemoniumExileEffect> {

    public PyxisOfPandemoniumExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player exiles the top card of his or her library face down";
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
        if (controller != null) {
            Map<String, UUID> exileIds;
            String valueKey = CardUtil.getCardZoneString("exileIds", source.getSourceId(), game);
            Object object = game.getState().getValue(valueKey);
            if (object != null && object instanceof Map) {
                exileIds = (Map<String, UUID>) object;
            } else {
                exileIds = new HashMap<String, UUID>();
                game.getState().setValue(valueKey, exileIds);
            }

            for (UUID playerId : controller.getInRange()) {

                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getLibrary().size() > 0) {
                        Card card = player.getLibrary().removeFromTop(game);
                        String exileKey = new StringBuilder(playerId.toString()).append(source.getSourceId().toString()).toString();
                        UUID exileId = exileIds.get(exileKey);
                        if (exileId == null) {
                            exileId = UUID.randomUUID();
                            exileIds.put(exileKey, exileId);
                        }
                        card.setFaceDown(true);
                        card.moveToExile(exileId, new StringBuilder("Pyxis of Pandemonium (").append(player.getName()).append(")").toString(), source.getSourceId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class PyxisOfPandemoniumPutOntoBattlefieldEffect extends OneShotEffect<PyxisOfPandemoniumPutOntoBattlefieldEffect> {

    public PyxisOfPandemoniumPutOntoBattlefieldEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Each player turns face up all cards he or she owns exiled with Pyxis of Pandemonium, then puts all permanent cards among them onto the battlefield";
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
        if (controller != null) {
            Map<String, UUID> exileIds;
            String valueKey = CardUtil.getCardZoneString("exileIds", source.getSourceId(), game);
            Object object = game.getState().getValue(valueKey);
            if (object != null && object instanceof Map) {
                exileIds = (Map<String, UUID>) object;
            } else {
                return true;
            }

            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    String exileKey = new StringBuilder(playerId.toString()).append(source.getSourceId().toString()).toString();
                    UUID exileId = exileIds.get(exileKey);
                    if (exileId != null) {
                        ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                        if (exileZone != null) {
                            for(Card card: exileZone.getCards(game)) {
                                card.setFaceDown(false);
                                if (CardUtil.isPermanentCard(card)) {
                                    card.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), playerId);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
