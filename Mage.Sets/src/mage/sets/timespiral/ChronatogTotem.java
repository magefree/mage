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
package mage.sets.timespiral;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public class ChronatogTotem extends CardImpl {

    public ChronatogTotem(UUID ownerId) {
        super(ownerId, 252, "Chronatog Totem", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "TSP";

        // {tap}: Add {U} to your mana pool.
        this.addAbility(new BlueManaAbility());
        
        // {1}{U}: Chronatog Totem becomes a 1/2 blue Atog artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new ChronatogTotemToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}")));
        
        // {0}: Chronatog Totem gets +3/+3 until end of turn. You skip your next turn. Activate this ability only once each turn and only if Chronatog Totem is a creature.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(3, 3, Duration.EndOfTurn),
                new ManaCostsImpl<>("{0}"),
                new ChronatogTotemCondition(),
                "{0}: {this} gets +3/+3 until end of turn. You skip your next turn. Activate this ability only once each turn and only if {this} is a creature");
        ability.addEffect(new SkipNextTurnSourceEffect());
        this.addAbility(ability, new ActivatedAbilityUsedThisTurnWatcher());
    }

    public ChronatogTotem(final ChronatogTotem card) {
        super(card);
    }

    @Override
    public ChronatogTotem copy() {
        return new ChronatogTotem(this);
    }
}

class ChronatogTotemToken extends Token {

    ChronatogTotemToken() {
        super("Atog", "1/2 blue Atog artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add("Atog");
        power = new MageInt(1);
        toughness = new MageInt(2);
        color.setBlue(true);
    }
}

class ChronatogTotemCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        ActivatedAbilityUsedThisTurnWatcher watcher = (ActivatedAbilityUsedThisTurnWatcher) game.getState().getWatchers().get("ActivatedAbilityUsedThisTurn");
        if (!watcher.getActivatedThisTurn().contains(source.getOriginalId())) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                return permanent.getCardType().contains(CardType.CREATURE);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "once each turn and only if an opponent controls a flying creature";
    }
}

class ActivatedAbilityUsedThisTurnWatcher extends Watcher {

    public Set<UUID> activatedThisTurn = new HashSet<>(0);

    ActivatedAbilityUsedThisTurnWatcher() {
        super("ActivatedAbilityUsedThisTurn", WatcherScope.GAME);
    }

    ActivatedAbilityUsedThisTurnWatcher(final ActivatedAbilityUsedThisTurnWatcher watcher) {
        super(watcher);
        this.activatedThisTurn.addAll(watcher.activatedThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ACTIVATED_ABILITY) {
            StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
            if (stackObject != null) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getTargetId());
                if (stackAbility != null && stackAbility.getAbilityType() == AbilityType.ACTIVATED) {
                    this.activatedThisTurn.add(stackAbility.getOriginalId());
                }
            }
        }
    }

    public Set<UUID> getActivatedThisTurn() {
        return Collections.unmodifiableSet(this.activatedThisTurn);
    }

    @Override
    public ActivatedAbilityUsedThisTurnWatcher copy() {
        return new ActivatedAbilityUsedThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        this.activatedThisTurn.clear();
    }
}