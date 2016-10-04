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
package mage.sets.torment;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LoneFox

 */
public class NantukoCultivator extends CardImpl {

    public NantukoCultivator(UUID ownerId) {
        super(ownerId, 133, "Nantuko Cultivator", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "TOR";
        this.subtype.add("Insect");
        this.subtype.add("Druid");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Nantuko Cultivator enters the battlefield, you may discard any number of land cards. Put that many +1/+1 counters on Nantuko Cultivator and draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NantukoCultivatorEffect(), true));
    }

    public NantukoCultivator(final NantukoCultivator card) {
        super(card);
    }

    @Override
    public NantukoCultivator copy() {
        return new NantukoCultivator(this);
    }
}

class NantukoCultivatorEffect extends OneShotEffect {

    public NantukoCultivatorEffect() {
        super(Outcome.BoostCreature);
        staticText  = "you may discard any number of land cards. Put that many +1/+1 counters on {this} and draw that many cards.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            TargetCardInHand toDiscard = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterLandCard());
            if(player.chooseTarget(Outcome.Discard, toDiscard, source, game)) {
                int count = 0;
                for(UUID targetId: toDiscard.getTargets()) {
                    player.discard(game.getCard(targetId), source, game);
                    count++;
                }
                Permanent permanent = game.getPermanent(source.getSourceId());
                if(permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(count), game);
                }
                player.drawCards(count, game);
            }
            return true;
        }
        return false;
    }

    public NantukoCultivatorEffect(final NantukoCultivatorEffect effect) {
        super(effect);
    }

    @Override
    public NantukoCultivatorEffect copy() {
        return new NantukoCultivatorEffect(this);
    }

}
