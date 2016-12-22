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
package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author MTGfan
 */
public class CyclopeanTomb extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent();
    public List<UUID> lands = new ArrayList<>();
    
    static {
        filter.add(Predicates.not(new SubtypePredicate("Swamp")));
    }

    public CyclopeanTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        

        // {2}, {tap}: Put a mire counter on target non-Swamp land. That land is a Swamp for as long as it has a mire counter on it. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.MIRE.createInstance()), new GenericManaCost(2), new IsStepCondition(PhaseStep.UPKEEP), "{2}, {T}: Put a mire counter on target non-Swamp land. That land is a Swamp for as long as it has a mire counter on it. Activate this ability only during your upkeep.");
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetLandPermanent(filter));
        ability.addEffect(new BecomeSwampEffect(Duration.Custom, false, true, "Swamp"));
        this.addAbility(ability);
        //add the targets UUID to an array to track which specific lands the instance of Cyclopean Tomb added a mire counter to.
        lands.add(ability.getTargets().getFirstTarget());
        // When Cyclopean Tomb is put into a graveyard from the battlefield, at the beginning of each of your upkeeps for the rest of the game, remove all mire counters from a land that a mire counter was put onto with Cyclopean Tomb but that a mire counter has not been removed from with Cyclopean Tomb.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new CycleDelayedTriggeredAbility(lands));
        effect.setText("at the beginning of each of your upkeeps for the rest of the game, remove all mire counters from a land that a mire counter was put onto with {this} but that a mire counter has not been removed from with {this}.");
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(effect));
    }

    public CyclopeanTomb(final CyclopeanTomb card) {
        super(card);
    }

    @Override
    public CyclopeanTomb copy() {
        return new CyclopeanTomb(this);
    }
}

class BecomeSwampEffect extends BecomesBasicLandTargetEffect {
    
    public BecomeSwampEffect(Duration duration, boolean chooseLandType, boolean loseOther, String... landNames) {
        super(duration, chooseLandType, loseOther, landNames);
        staticText = "That land is a Swamp for as long as it has a mire counter on it.";
    }
    
    public BecomeSwampEffect(final BecomeSwampEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent land = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (land == null) {
            // if permanent left battlefield the effect can be removed because it was only valid for that object
            this.discard();
        } else if (land.getCounters(game).getCount(CounterType.MIRE) > 0) {
            // only if Mire counter is on the object it becomes a Swamp.
            super.apply(layer, sublayer, source, game);
        }
        return true;
    }
    
    @Override
    public BecomeSwampEffect copy() {
        return new BecomeSwampEffect(this);
    }
}

class CycleDelayedTriggeredAbility extends DelayedTriggeredAbility {

    CycleDelayedTriggeredAbility(List<UUID> lands) {
        super(new CyclopeanTombEffect(lands), Duration.OneUse, true, false);
    }

    CycleDelayedTriggeredAbility(CycleDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CycleDelayedTriggeredAbility copy() {
        return new CycleDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return  event.getPlayerId().equals(this.controllerId);
    }
}

class CyclopeanTombEffect extends OneShotEffect {

    List<UUID> lands = new ArrayList<>();
    public static final FilterLandPermanent mireFilter = new FilterLandPermanent();

    static {
        mireFilter.add(new CounterPredicate(CounterType.MIRE));
    }
    
    public CyclopeanTombEffect(List<UUID> lands) {
        super(Outcome.Benefit);
        this.lands = lands;
        this.staticText = "at the beginning of each of your upkeeps for the rest of the game, remove all mire counters from a land that a mire counter was put onto with {this} but that a mire counter has not been removed from with {this}.";
    }
    
    public CyclopeanTombEffect(final CyclopeanTombEffect effect) {
        super(effect);
    }

    @Override
    public CyclopeanTombEffect copy() {
        return new CyclopeanTombEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null){
            /*
            *
            *Remove all counters from target code goes here. Created a RemoveAllCountersTargetEffect for that.
            *
            */
            new ChooseLandEffect(lands).apply(game, source);
            Effect effect = new RemoveAllCountersTargetEffect(CounterType.MIRE);
            effect.setTargetPointer(new FixedTarget((UUID) game.getState().getValue(source.getSourceId().toString() + "_land")));
            effect.apply(game, source);
            //CyclopianTombEffect and CycleDelayedTriggeredAbility will maintain a loop
            //as long as there are one or more mire counters left to be removed
            new ConditionalOneShotEffect(new CreateDelayedTriggeredAbilityEffect(new CycleDelayedTriggeredAbility(lands), false), new CyclopeanTombCounterCondition(lands)).apply(game, source);
            return true;
        }
    return false;
    }
}

class CyclopeanTombCounterCondition implements Condition {

    private List<UUID> lands;
    
    public CyclopeanTombCounterCondition(List<UUID> lands) {
        this.lands = lands;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(CyclopeanTombEffect.mireFilter, game);
        for (UUID landId : lands) {
            for(Permanent permanent : permanents) {
                if(landId.equals(permanent.getId())) {
                    return permanent.getCounters(game).getCount(CounterType.MIRE) > 0;
                }
            }
        }
        return false;
    }
}

class ChooseLandEffect extends OneShotEffect {
    
    private List<UUID> lands;

    public ChooseLandEffect(List<UUID> lands) {
        super(Outcome.Neutral);
        this.lands = lands;
        this.staticText = "choose a land that a mire counter was put onto with Cyclopean Tomb but that a mire counter has not been removed from with Cyclopean Tomb";
    }

    public ChooseLandEffect(final ChooseLandEffect effect) {
        super(effect);
    }

    @Override
    public ChooseLandEffect copy() {
        return new ChooseLandEffect(this);
    }
    //UUID chosenLand = ; use with addTarget or setTargetPointer
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        FilterLandPermanent filter = new FilterLandPermanent();
        filter.add(new LandIdPredicate(lands));
        if(controller != null){
            TargetLandPermanent target = new TargetLandPermanent(filter);
            if (controller.choose(this.outcome, target, source.getSourceId(), game)) {
                Permanent chosenLand = game.getPermanent(target.getFirstTarget());
                if(chosenLand != null) {
                    game.getState().setValue(mageObject.getId() + "_land", target.getFirstTarget());
                    return true;
                }
            }
        }
        return false;
    }
}

class LandIdPredicate implements Predicate<Permanent> {
    
    private List<UUID> lands;

    public LandIdPredicate(List<UUID> lands) {
        this.lands = lands;
    }
    @Override
    public boolean apply(Permanent input, Game game) {
        for(UUID landId : lands){
                return input.getId().equals(landId);
        }
        return false;
    }
}


