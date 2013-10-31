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
package mage.sets.eventide;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 *
 */
public class GroundlingPouncer extends CardImpl<GroundlingPouncer> {
    
    private String rule = "{this} gets +1/+3 and gains flying until end of turn. Activate this ability only once each turn and only if an opponent controls a creature with flying.";

    public GroundlingPouncer(UUID ownerId) {
        super(ownerId, 154, "Groundling Pouncer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G/U}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Faerie");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {GU}: Groundling Pouncer gets +1/+3 and gains flying until end of turn. Activate this ability only once each turn and only if an opponent controls a creature with flying.
        Condition condition = new GroundingPouncerCondition();
        Effect effect = new BoostSourceEffect(1, 3, Duration.EndOfTurn);
        Effect effect2 = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, false, true);
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{G/U}"), condition, rule);
        ability.addEffect(effect2);
        this.addAbility(ability);
        this.addWatcher(new ActivatedAbilityUsedThisTurnWatcher());

    }

    public GroundlingPouncer(final GroundlingPouncer card) {
        super(card);
    }

    @Override
    public GroundlingPouncer copy() {
        return new GroundlingPouncer(this);
    }
}

class GroundingPouncerCondition implements Condition {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ActivatedAbilityUsedThisTurnWatcher watcher = (ActivatedAbilityUsedThisTurnWatcher) game.getState().getWatchers().get("ActivatedAbilityUsedThisTurn");
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (game.getBattlefield().countAll(filter, opponentId, game) > 0 && !watcher.getActivatedThisTurn().contains(source.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "once each turn and only if an opponent controls a flying creature";
    }
}

class ActivatedAbilityUsedThisTurnWatcher extends WatcherImpl<ActivatedAbilityUsedThisTurnWatcher> {

    public Set<UUID> activatedThisTurn = new HashSet<UUID>();

    public ActivatedAbilityUsedThisTurnWatcher() {
        super("ActivatedAbilityUsedThisTurn", WatcherScope.GAME);
    }

    public ActivatedAbilityUsedThisTurnWatcher(final ActivatedAbilityUsedThisTurnWatcher watcher) {
        super(watcher);
        this.activatedThisTurn.addAll(watcher.activatedThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
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
        return this.activatedThisTurn;
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