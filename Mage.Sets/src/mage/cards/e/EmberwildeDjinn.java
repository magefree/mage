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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class EmberwildeDjinn extends CardImpl {

    public EmberwildeDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each player's upkeep, that player may pay {R}{R} or 2 life. If he or she does, the player gains control of Emberwilde Djinn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new EmberwildeDjinnEffect(), TargetController.ANY, false));
    }

    public EmberwildeDjinn(final EmberwildeDjinn card) {
        super(card);
    }

    @Override
    public EmberwildeDjinn copy() {
        return new EmberwildeDjinn(this);
    }
}

class EmberwildeDjinnEffect extends OneShotEffect {

    EmberwildeDjinnEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player may pay {R}{R} or 2 life. If he or she does, the player gains control of {this}";
    }

    EmberwildeDjinnEffect(final EmberwildeDjinnEffect effect) {
        super(effect);
    }

    @Override
    public EmberwildeDjinnEffect copy() {
        return new EmberwildeDjinnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        Cost cost = new OrCost(new ManaCostsImpl("{R}{R}"), new PayLifeCost(2), "{R}{R} or 2 life");
        if (player.chooseUse(Outcome.GainControl, "Gain control of " + sourceObject.getLogName() + "?", source, game)) {
            if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, false, player.getId());
                effect.setTargetPointer(new FixedTarget(source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
                game.addEffect(effect, source);
                player.resetStoredBookmark(game);
            }
        }
        return true;
    }
}
