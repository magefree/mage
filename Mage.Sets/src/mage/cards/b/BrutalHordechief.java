
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.ChooseBlockersRedundancyWatcher;

/**
 *
 * @author LevelX2
 */
public final class BrutalHordechief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BrutalHordechief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ORC, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature you control attacks, defending player loses 1 life and you gain 1 life.
        this.addAbility(new BrutalHordechiefTriggeredAbility());

        // {3}{R/W}{R/W}: Creatures your opponents control block this turn if able, and you choose how those creatures block.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BlocksIfAbleAllEffect(filter, Duration.EndOfTurn), new ManaCostsImpl("{3}{R/W}{R/W}"));
        ability.addEffect(new BrutalHordechiefChooseBlockersEffect());
        ability.addWatcher(new ChooseBlockersRedundancyWatcher());
        ability.addEffect(new ChooseBlockersRedundancyWatcherIncrementEffect());
        this.addAbility(ability);
    }

    public BrutalHordechief(final BrutalHordechief card) {
        super(card);
    }

    @Override
    public BrutalHordechief copy() {
        return new BrutalHordechief(this);
    }
    
    private class ChooseBlockersRedundancyWatcherIncrementEffect extends OneShotEffect {
    
        ChooseBlockersRedundancyWatcherIncrementEffect() {
            super(Outcome.Neutral);
        }
    
        ChooseBlockersRedundancyWatcherIncrementEffect(final ChooseBlockersRedundancyWatcherIncrementEffect effect) {
            super(effect);
        }
    
        @Override
        public boolean apply(Game game, Ability source) {
            ChooseBlockersRedundancyWatcher watcher = (ChooseBlockersRedundancyWatcher) game.getState().getWatchers().get(ChooseBlockersRedundancyWatcher.class.getSimpleName());
            if (watcher != null) {
                watcher.increment();
                return true;
            }
            return false;
        }
    
        @Override
        public ChooseBlockersRedundancyWatcherIncrementEffect copy() {
            return new ChooseBlockersRedundancyWatcherIncrementEffect(this);
        }
    }
}

class BrutalHordechiefTriggeredAbility extends TriggeredAbilityImpl {

    public BrutalHordechiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
        this.addEffect(new GainLifeEffect(1));
    }

    public BrutalHordechiefTriggeredAbility(final BrutalHordechiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BrutalHordechiefTriggeredAbility copy() {
        return new BrutalHordechiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.isControlledBy(controllerId)) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defendingPlayerId));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks, defending player loses 1 life and you gain 1 life.";
    }
}

class BrutalHordechiefChooseBlockersEffect extends ContinuousRuleModifyingEffectImpl {

    public BrutalHordechiefChooseBlockersEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "You choose which creatures block this turn and how those creatures block";
    }

    public BrutalHordechiefChooseBlockersEffect(final BrutalHordechiefChooseBlockersEffect effect) {
        super(effect);
    }

    @Override
    public BrutalHordechiefChooseBlockersEffect copy() {
        return new BrutalHordechiefChooseBlockersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ChooseBlockersRedundancyWatcher watcher = (ChooseBlockersRedundancyWatcher) game.getState().getWatchers().get(ChooseBlockersRedundancyWatcher.class.getSimpleName());
        watcher.decrement();
        if (watcher.copyCountApply > 0) {
            game.informPlayers(source.getSourceObject(game).getIdName() + " didn't apply");
            return false;
        }
        watcher.copyCountApply = watcher.copyCount;
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, game);
            return true;
        }
        return false;
    }
}
