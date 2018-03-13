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
package mage.cards.l;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class LoxodonPeacekeeper extends CardImpl {

    public LoxodonPeacekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);        
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, the player with the lowest life total gains control of Loxodon Peacekeeper. If two or more players are tied for lowest life total, you choose one of them, and that player gains control of Loxodon Peacekeeper.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoxodonPeacekeeperEffect(), TargetController.YOU, false));

    }

    public LoxodonPeacekeeper(final LoxodonPeacekeeper card) {
        super(card);
    }

    @Override
    public LoxodonPeacekeeper copy() {
        return new LoxodonPeacekeeper(this);
    }
}

class LoxodonPeacekeeperEffect extends OneShotEffect {

    public LoxodonPeacekeeperEffect() {
        super(Outcome.Benefit);
        this.staticText = "the player with the lowest life total gains control of {this}. If two or more players are tied for lowest life total, you choose one of them, and that player gains control of {this}";
    }

    public LoxodonPeacekeeperEffect(final LoxodonPeacekeeperEffect effect) {
        super(effect);
    }

    @Override
    public LoxodonPeacekeeperEffect copy() {
        return new LoxodonPeacekeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                int lowLife = Integer.MAX_VALUE;
                Set<UUID> tiedPlayers = new HashSet<>();
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        if (player.getLife() < lowLife) {
                            lowLife = player.getLife();
                        }
                    }
                }
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        if (player.getLife() == lowLife) {
                            tiedPlayers.add(playerId);
                        }
                    }
                }
                
                if (tiedPlayers.size() > 0) {
                    UUID newControllerId = null;
                    if (tiedPlayers.size() > 1) {
                        FilterPlayer filter = new FilterPlayer("a player tied for lowest life total");
                        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                            if (!tiedPlayers.contains(playerId)) {
                                filter.add(Predicates.not(new PlayerIdPredicate(playerId)));
                            }
                        }
                        TargetPlayer target = new TargetPlayer(1, 1, true, filter);
                        if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
                            while (!target.isChosen() && target.canChoose(controller.getId(), game) && controller.canRespond()) {
                                controller.chooseTarget(outcome, target, source, game);
                            }
                        } else {
                            return false;
                        }
                        newControllerId = game.getPlayer(target.getFirstTarget()).getId();
                    } else {
                        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                            if (tiedPlayers.contains(playerId)) {
                                newControllerId = playerId;
                                break;
                            }
                        }
                    }
                    if (newControllerId != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newControllerId);
                        effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                        game.addEffect(effect, source);
                        game.informPlayers(game.getPlayer(newControllerId).getLogName() + " has gained control of " + sourcePermanent.getLogName());
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
