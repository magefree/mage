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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class MineMineMine extends CardImpl {

    public MineMineMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}{G}");

        // When Mine, Mine, Mine enters the battlefield, each player puts his or her library into his or her hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MineMineMineDrawEffect()));

        // Players have no maximum hand size and don't lose the game for drawing from an empty library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, HandSizeModification.SET, TargetController.ANY)
                .setText("Players have no maximum hand size and don't lose the game for drawing from an empty library")));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MineMineMineDontLoseEffect()));

        // Each player can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantCastMoreThanOneSpellEffect(TargetController.ANY)));
        
        // When Mine, Mine, Mine leaves the battlefield, each player shuffles his or her hand and graveyard into his or her library.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new MineMineMineShuffleEffect(), false));
    }

    public MineMineMine(final MineMineMine card) {
        super(card);
    }

    @Override
    public MineMineMine copy() {
        return new MineMineMine(this);
    }
}

class MineMineMineDrawEffect extends OneShotEffect {

    MineMineMineDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "each player puts his or her library into his or her hand";
    }

    MineMineMineDrawEffect(final MineMineMineDrawEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                CardsImpl libraryCards = new CardsImpl();
                libraryCards.addAll(player.getLibrary().getCards(game));
                player.moveCards(libraryCards, Zone.HAND, source, game);
            }
        }
        return true;
    }

    @Override
    public MineMineMineDrawEffect copy() {
        return new MineMineMineDrawEffect(this);
    }
}

class MineMineMineDontLoseEffect extends ReplacementEffectImpl {

    MineMineMineDontLoseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
    }

    MineMineMineDontLoseEffect(final MineMineMineDontLoseEffect effect) {
        super(effect);
    }

    @Override
    public MineMineMineDontLoseEffect copy() {
        return new MineMineMineDontLoseEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null && player.getLibrary().getCards(game).isEmpty()) {
            return true;
        }
        return false;
    }
}

class MineMineMineShuffleEffect extends OneShotEffect {

    public MineMineMineShuffleEffect() {
        super(Outcome.Neutral);
        staticText = "each player shuffles his or her hand and graveyard into his or her library";
    }

    public MineMineMineShuffleEffect(final MineMineMineShuffleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                player.moveCards(player.getGraveyard(), Zone.LIBRARY, source, game);
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }

    @Override
    public MineMineMineShuffleEffect copy() {
        return new MineMineMineShuffleEffect(this);
    }
}
