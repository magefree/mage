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
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 * @author LevelX2
 *
 * 702.74. Hideaway 702.74a Hideaway represents a static ability and a triggered
 * ability. "Hideaway" means "This permanent enters the battlefield tapped" and
 * "When this permanent enters the battlefield, look at the top four cards of
 * your library. Exile one of them face down and put the rest on the bottom of
 * your library in any order. The exiled card gains 'Any player who has
 * controlled the permanent that exiled this card may look at this card in the
 * exile zone.'"
 */
public class HideawayAbility extends StaticAbility {

    public HideawayAbility() {
        super(Zone.ALL, new EntersBattlefieldEffect(new TapSourceEffect(true)));
        Ability ability = new EntersBattlefieldTriggeredAbility(new HideawayExileEffect(), false);
        ability.setRuleVisible(false);
        addSubAbility(ability);
        // Allow controller to look at face down card
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new HideawayLookAtFaceDownCardEffect());
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    public HideawayAbility(final HideawayAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Hideaway <i>(This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)</i>";
    }

    @Override
    public HideawayAbility copy() {
        return new HideawayAbility(this);
    }
}

class HideawayExileEffect extends OneShotEffect {

    private static FilterCard filter1 = new FilterCard("card to exile face down");

    public HideawayExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library";
    }

    public HideawayExileEffect(final HideawayExileEffect effect) {
        super(effect);
    }

    @Override
    public HideawayExileEffect copy() {
        return new HideawayExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent hideawaySource = game.getPermanent(source.getSourceId());
        if (hideawaySource == null || controller == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.LIBRARY);
        cards.addAll(controller.getLibrary().getTopCards(game, 4));
        if (cards.size() > 0) {
            TargetCard target1 = new TargetCard(Zone.LIBRARY, filter1);
            if (controller.choose(Outcome.Detriment, cards, target1, game)) {
                Card card = cards.get(target1.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCardToExileWithInfo(card, CardUtil.getCardExileZoneId(game, source),
                            "Hideaway (" + hideawaySource.getIdName() + ")", source.getSourceId(), game, Zone.LIBRARY, false);
                    card.setFaceDown(true, game);
                }
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        }

        return true;
    }
}

class HideawayLookAtFaceDownCardEffect extends AsThoughEffectImpl {

    public HideawayLookAtFaceDownCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public HideawayLookAtFaceDownCardEffect(final HideawayLookAtFaceDownCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HideawayLookAtFaceDownCardEffect copy() {
        return new HideawayLookAtFaceDownCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (game.getState().getZone(objectId) != Zone.EXILED
                || !game.getState().getCardState(objectId).isFaceDown()) {
            return false;
        }
        // TODO: Does not handle if a player had the control of the land permanent some time before
        // we would need to add a watcher to handle this
        Permanent sourcePermanet = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanet != null && sourcePermanet.getControllerId().equals(affectedControllerId)) {
            ExileZone exile = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            Card card = game.getCard(objectId);
            if (exile != null && exile.contains(objectId) && card != null) {
                Player player = game.getPlayer(affectedControllerId);
                if (player != null) {
                    player.lookAtCards("Hideaway by " + sourcePermanet.getIdName(), card, game);
                }
            }
        }
        // only the current or a previous controller can see the card, so always return false for reveal request
        return false;
    }
}
