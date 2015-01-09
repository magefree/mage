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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import static mage.game.events.GameEvent.EventType.DAMAGE_CREATURE;
import static mage.game.events.GameEvent.EventType.DAMAGE_PLAYER;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class BitterFeud extends CardImpl {

    public BitterFeud(UUID ownerId) {
        super(ownerId, 32, "Bitter Feud", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");
        this.expansionSetCode = "C14";

        this.color.setRed(true);

        // As Bitter Feud enters the battlefield, choose two players.
        this.addAbility(new AsEntersBattlefieldAbility(new BitterFeudEntersBattlefieldEffect()));

        // If a source controlled by one of the chosen players would deal damage to the other chosen player or a permanent that player controls, that source deals double that damage to that player or permanent instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BitterFeudEffect() ));
    }

    public BitterFeud(final BitterFeud card) {
        super(card);
    }

    @Override
    public BitterFeud copy() {
        return new BitterFeud(this);
    }
}

class BitterFeudEntersBattlefieldEffect extends OneShotEffect {

    public BitterFeudEntersBattlefieldEffect() {
        super(Outcome.Damage);
        staticText = "choose two players";
    }

    public BitterFeudEntersBattlefieldEffect(final BitterFeudEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            TargetPlayer target = new TargetPlayer(2,2, true);
            controller.chooseTarget(outcome, target, source, game);
            Player player1 = game.getPlayer(target.getFirstTarget());
            Player player2 = game.getPlayer(target.getTargets().get(1));
            if (player1 != null && player2 != null) {
                game.getState().setValue(source.getSourceId() + "_player1", player1);
                game.getState().setValue(source.getSourceId() + "_player2", player2);
                game.informPlayers(permanent.getName() + ": " + controller.getName() + " has chosen " + player1.getName() + " and " + player2.getName());
                permanent.addInfo("chosen players", "<font color = 'blue'>Chosen players: " + player1.getName() +", " + player2.getName() + "</font>", game);
                return true;
            }
        }
        return false;
    }

    @Override
    public BitterFeudEntersBattlefieldEffect copy() {
        return new BitterFeudEntersBattlefieldEffect(this);
    }

}

class BitterFeudEffect extends ReplacementEffectImpl {

    Player player1;
    Player player2;

    public BitterFeudEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source controlled by one of the chosen players would deal damage to the other chosen player or a permanent that player controls, that source deals double that damage to that player or permanent instead";
    }

    public BitterFeudEffect(final BitterFeudEffect effect) {
        super(effect);
    }

    @Override
    public BitterFeudEffect copy() {
        return new BitterFeudEffect(this);
    }

    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        player1 = (Player) game.getState().getValue(source.getSourceId() + "_player1");
        player2 = (Player) game.getState().getValue(source.getSourceId() + "_player2");
        if (player1 != null && player2 != null) {
            UUID targetPlayerId = null;
            switch (event.getType()) {
                case DAMAGE_PLAYER:
                    targetPlayerId = event.getTargetId();
                    break;
                case DAMAGE_CREATURE:
                case DAMAGE_PLANESWALKER:
                    Permanent permanent = game.getPermanent(event.getTargetId());
                    if (permanent != null) {
                        targetPlayerId = permanent.getControllerId();
                    }
                    break;
                default:
                    return false;
            }

            if (player1.getId().equals(targetPlayerId) || player2.getId().equals(targetPlayerId)) {
                UUID sourcePlayerId = null;
                MageObject damageSource = game.getObject(event.getSourceId());
                if (damageSource instanceof StackObject) {
                    sourcePlayerId = ((StackObject) damageSource).getControllerId();
                } else if (damageSource instanceof Permanent) {
                    sourcePlayerId = ((Permanent) damageSource).getControllerId();
                } else if (damageSource instanceof Card) {
                    sourcePlayerId = ((Card) damageSource).getOwnerId();
                }
                if (sourcePlayerId != null &&
                        (player1.getId().equals(sourcePlayerId) || player2.getId().equals(sourcePlayerId)) &&
                        !sourcePlayerId.equals(targetPlayerId)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }
}
