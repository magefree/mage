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
package mage.sets.gameday;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public class ScaleguardSentinels extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate("Dragon"));
    }
    
    public ScaleguardSentinels(UUID ownerId) {
        super(ownerId, 43, "Scaleguard Sentinels", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{G}{G}");
        this.expansionSetCode = "MGDC";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As an additional cost to cast Scaleguard Sentinels, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addEffect(new InfoEffect("As an additional cost to cast {this}, you may reveal a Dragon card from your hand"));
        
        // Scaleguard Sentinels enters the battlefield with a +1/+1 counter on it if you revealed a Dragon card or controlled a Dragon as you cast Scaleguard Sentinels.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true),
                ScaleguardSentinelsCondition.getInstance(), true,
                "{this} enters the battlefield with a +1/+1 counter on it if you revealed a Dragon card or controlled a Dragon as you cast {this}", ""),
                new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
        
    }
    
    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldAbility) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                if (controller.getHand().count(filter, game) > 0) {
                    ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0,1, filter)));
                }
            }
        }
    }
    
    public ScaleguardSentinels(final ScaleguardSentinels card) {
        super(card);
    }

    @Override
    public ScaleguardSentinels copy() {
        return new ScaleguardSentinels(this);
    }
}

class ScaleguardSentinelsCondition implements Condition {

    private static final ScaleguardSentinelsCondition fInstance = new ScaleguardSentinelsCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = (DragonOnTheBattlefieldWhileSpellWasCastWatcher) game.getState().getWatchers().get("DragonOnTheBattlefieldWhileSpellWasCastWatcher");
        boolean condition = watcher != null && watcher.castWithConditionTrue(source.getId());
        if (!condition) {
            for (Cost cost: source.getCosts()) {
                if (cost instanceof RevealTargetFromHandCost) {
                    condition = ((RevealTargetFromHandCost)cost).getNumberRevealedCards() > 0;
                }
            }
        }
        return condition;
    }
}
