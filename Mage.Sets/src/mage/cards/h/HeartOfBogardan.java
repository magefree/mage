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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author emerald000 & L_J
 */
public class HeartOfBogardan extends CardImpl {

    public HeartOfBogardan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Cumulative upkeep-Pay {2}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{2}")));

        // When a player doesn't pay Heart of Bogardan's cumulative upkeep, Heart of Bogardan deals X damage to target player and each creature he or she controls, where X is twice the number of age counters on Heart of Bogardan minus 2.
        this.addAbility(new HeartOfBogardanTriggeredAbility());
    }

    public HeartOfBogardan(final HeartOfBogardan card) {
        super(card);
    }

    @Override
    public HeartOfBogardan copy() {
        return new HeartOfBogardan(this);
    }
}

class HeartOfBogardanTriggeredAbility extends TriggeredAbilityImpl {

    HeartOfBogardanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HeartOfBogardanEffect(), false);
        this.addTarget(new TargetPlayer());
    }

    HeartOfBogardanTriggeredAbility(final HeartOfBogardanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeartOfBogardanTriggeredAbility copy() {
        return new HeartOfBogardanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DIDNT_PAY_CUMULATIVE_UPKEEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId() != null && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When a player doesn't pay {this}'s cumulative upkeep, {this} deals X damage to target player and each creature he or she controls, where X is twice the number of age counters on {this} minus 2.";
    }
}

class HeartOfBogardanEffect extends OneShotEffect {

    public HeartOfBogardanEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target player and each creature he or she controls, where X is twice the number of age counters on {this} minus 2";
    }

    public HeartOfBogardanEffect(final HeartOfBogardanEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && sourcePermanent != null) {
           int damage = sourcePermanent.getCounters(game).getCount(CounterType.AGE) * 2 - 2;
            if (damage > 0)  {
                player.damage(damage, source.getSourceId(), game, false, true);
                for (Permanent perm: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game)) {
                    perm.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public HeartOfBogardanEffect copy() {
        return new HeartOfBogardanEffect(this);
    }

}
