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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author JRHerlehy
 */
public class OrchardElemental extends CardImpl {

    public OrchardElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        
        this.subtype.add("Elemental");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Council's dilemma</i> &mdash When Orchard Elemental enters the battlefield, starting with you, each player votes for sprout or harvest. Put two +1/+1 counters on Orchard Elemental for each sprout vote. You gain 3 life for each harvest vote.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OrchardElementalDilemmaEffect(), false, "<i>Council's dilemma</i> — "));
    }

    public OrchardElemental(final OrchardElemental card) {
        super(card);
    }

    @Override
    public OrchardElemental copy() {
        return new OrchardElemental(this);
    }
}

class OrchardElementalDilemmaEffect extends OneShotEffect {

    public OrchardElementalDilemmaEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for sprout or harvest. Put two +1/+1 counters on Orchard Elemental for each sprout vote. You gain 3 life for each harvest vote";
    }

    public OrchardElementalDilemmaEffect(final OrchardElementalDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) return false;

        int sproutCount = 0, harvestCount = 0;

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseUse(Outcome.BoostCreature, "Choose sprout?", source, game)) {
                    sproutCount++;
                    game.informPlayers(player.getName() + " has voted for sprout");
                } else {
                    harvestCount++;
                    game.informPlayers(player.getName() + " has voted for harvest");
                }
            }
        }

        Permanent permanent = game.getPermanent(source.getSourceId());

        if (sproutCount > 0 && permanent != null) permanent.addCounters(CounterType.P1P1.createInstance(sproutCount * 2), game);

        if (harvestCount > 0) {
            Effect gainLifeEffect = new GainLifeEffect(harvestCount * 3);
            gainLifeEffect.apply(game, source);
        }

        return true;
    }

    @Override
    public OrchardElementalDilemmaEffect copy() {
        return new OrchardElementalDilemmaEffect(this);
    }
}
