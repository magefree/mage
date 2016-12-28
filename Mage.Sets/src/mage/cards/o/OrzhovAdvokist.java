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
package mage.cards.o;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class OrzhovAdvokist extends CardImpl {

    public OrzhovAdvokist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add("Human");
        this.subtype.add("Advisor");
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, each player may put two +1/+1 counters on a creature he or she controls. If a player does, creatures that player controls can't attack you or a planeswalker you control until your next turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new OrzhovAdvokistEffect(), TargetController.YOU, false));
    }

    public OrzhovAdvokist(final OrzhovAdvokist card) {
        super(card);
    }

    @Override
    public OrzhovAdvokist copy() {
        return new OrzhovAdvokist(this);
    }
}

class OrzhovAdvokistEffect extends OneShotEffect {

    public OrzhovAdvokistEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player may put two +1/+1 counters on a creature he or she controls. "
                + "If a player does, creatures that player controls can't attack you or a planeswalker you control until your next turn";
    }

    public OrzhovAdvokistEffect(final OrzhovAdvokistEffect effect) {
        super(effect);
    }

    @Override
    public OrzhovAdvokistEffect copy() {
        return new OrzhovAdvokistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ArrayList<UUID> players = new ArrayList<>();
            ArrayList<UUID> creatures = new ArrayList<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(outcome, "Put two +1/+1 counters on a creature you control?", source, game)) {
                        Target target = new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("a creature you control (to add two +1/+1 counters on it)"));
                        if (player.choose(outcome, target, playerId, game)) {
                            creatures.add(target.getFirstTarget());
                            players.add(player.getId());
                        }

                    }
                }
            }
            for (UUID creatureId : creatures) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    creature.addCounters(CounterType.P1P1.createInstance(2), source, game);
                }
            }
            for (UUID playerId : players) {
                if (playerId != source.getControllerId()) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent();
                    filter.add(new ControllerIdPredicate(playerId));
                    game.addEffect(new CantAttackYouAllEffect(Duration.UntilYourNextTurn, filter, true), source);
                }
            }
            return true;
        }
        return false;
    }
}
