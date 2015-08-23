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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class KiraGreatGlassSpinner extends CardImpl {

    public KiraGreatGlassSpinner(UUID ownerId) {
        super(ownerId, 40, "Kira, Great Glass-Spinner", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.supertype.add("Legendary");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures you control have "Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability."
        Effect effect = new CounterTargetEffect();
        effect.setText("counter that spell or ability");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new KiraGreatGlassSpinnerAbility(effect), Duration.WhileOnBattlefield, new FilterCreaturePermanent("Creatures you control"))),
                new CreatureWasTargetedThisTurnWatcher());

    }

    public KiraGreatGlassSpinner(final KiraGreatGlassSpinner card) {
        super(card);
    }

    @Override
    public KiraGreatGlassSpinner copy() {
        return new KiraGreatGlassSpinner(this);
    }
}

class KiraGreatGlassSpinnerAbility extends TriggeredAbilityImpl {

    public KiraGreatGlassSpinnerAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public KiraGreatGlassSpinnerAbility(final KiraGreatGlassSpinnerAbility ability) {
        super(ability);
    }

    @Override
    public KiraGreatGlassSpinnerAbility copy() {
        return new KiraGreatGlassSpinnerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.getCardType().contains(CardType.CREATURE)) {
                CreatureWasTargetedThisTurnWatcher watcher = (CreatureWasTargetedThisTurnWatcher) game.getState().getWatchers().get("CreatureWasTargetedThisTurn");
                if (watcher != null && watcher.notMoreThanOnceTargetedThisTurn(permanent, game)) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.";
    }

}

class CreatureWasTargetedThisTurnWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> creaturesTargeted = new HashMap<>();

    public CreatureWasTargetedThisTurnWatcher() {
        super("CreatureWasTargetedThisTurn", WatcherScope.GAME);
    }

    public CreatureWasTargetedThisTurnWatcher(final CreatureWasTargetedThisTurnWatcher watcher) {
        super(watcher);
        this.creaturesTargeted.putAll(creaturesTargeted);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TARGETED) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.getCardType().contains(CardType.CREATURE)) {
                MageObjectReference mor = new MageObjectReference(permanent, game);
                int amount = 0;
                if (creaturesTargeted.containsKey(mor)) {
                    amount = creaturesTargeted.get(mor);
                }
                creaturesTargeted.put(mor, ++amount);
            }
        }
    }

    public boolean notMoreThanOnceTargetedThisTurn(Permanent creature, Game game) {
        if (creaturesTargeted.containsKey(new MageObjectReference(creature, game))) {
            return creaturesTargeted.get(new MageObjectReference(creature, game)) < 2;
        }
        return true;
    }

    @Override
    public void reset() {
        super.reset();
        creaturesTargeted.clear();
    }

    @Override
    public CreatureWasTargetedThisTurnWatcher copy() {
        return new CreatureWasTargetedThisTurnWatcher(this);
    }
}
