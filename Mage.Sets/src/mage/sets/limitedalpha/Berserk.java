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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public class Berserk extends CardImpl {

    public Berserk(UUID ownerId) {
        super(ownerId, 94, "Berserk", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "LEA";

        this.color.setGreen(true);

        // Cast Berserk only before the combat damage step. (Zone = all because it can be at least graveyard or hand)
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new BerserkReplacementEffect()), new CombatDamageStepStartedWatcher());

        // Target creature gains trample and gets +X/+0 until end of turn, where X is its power. 
        // At the beginning of the next end step, destroy that creature if it attacked this turn.
        Effect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Target creature gains trample");
        this.getSpellAbility().addEffect(effect);
        effect = new BoostTargetEffect(new TargetPermanentPowerCount(), new StaticValue(0), Duration.EndOfTurn);
        effect.setText("and gets +X/+0 until end of turn, where X is its power");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new BerserkDestroyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());

    }

    public Berserk(final Berserk card) {
        super(card);
    }

    @Override
    public Berserk copy() {
        return new Berserk(this);
    }
}

class BerserkReplacementEffect extends ContinuousRuleModifyingEffectImpl {
    BerserkReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only before the combat damage step";
    }

    BerserkReplacementEffect(final BerserkReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.CAST_SPELL) && event.getSourceId().equals(source.getSourceId())) {
            CombatDamageStepStartedWatcher watcher = (CombatDamageStepStartedWatcher) game.getState().getWatchers().get("CombatDamageStepStarted");
            return watcher == null || watcher.conditionMet();
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BerserkReplacementEffect copy() {
        return new BerserkReplacementEffect(this);
    }
}

class CombatDamageStepStartedWatcher extends Watcher {

    public CombatDamageStepStartedWatcher() {
        super("CombatDamageStepStarted", WatcherScope.GAME);
    }

    public CombatDamageStepStartedWatcher(final CombatDamageStepStartedWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // if no damage happens, the first event after is END_COMBAT_STEP_PRE
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE || event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE) {
            condition = true;
        }
    }

    @Override
    public CombatDamageStepStartedWatcher copy() {
        return new CombatDamageStepStartedWatcher(this);
    }
}

class BerserkDestroyEffect extends OneShotEffect {

    public BerserkDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "At the beginning of the next end step, destroy that creature if it attacked this turn";
    }

    public BerserkDestroyEffect(final BerserkDestroyEffect effect) {
        super(effect);
    }

    @Override
    public BerserkDestroyEffect copy() {
        return new BerserkDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            //create delayed triggered ability
            Effect effect = new BerserkDelayedDestroyEffect();
            effect.setTargetPointer(new FixedTarget(this.getTargetPointer().getFirst(game, source)));
            AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game));
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}

class BerserkDelayedDestroyEffect extends OneShotEffect {

    public BerserkDelayedDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy that creature if it attacked this turn";
    }

    public BerserkDelayedDestroyEffect(final BerserkDelayedDestroyEffect effect) {
        super(effect);
    }

    @Override
    public BerserkDelayedDestroyEffect copy() {
        return new BerserkDelayedDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                Watcher watcher = game.getState().getWatchers().get("AttackedThisTurn");
                if (watcher != null && watcher instanceof AttackedThisTurnWatcher) {
                    if (((AttackedThisTurnWatcher)watcher).getAttackedThisTurnCreatures().contains(permanent.getId())) {
                        return permanent.destroy(source.getSourceId(), game, false);
                    }
                }
            }
        }
        return false;
    }
}
