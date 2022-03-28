package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.game.turn.UpkeepStep;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ParadoxHaze extends CardImpl {

    public ParadoxHaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.
        this.addAbility(new ParadoxHazeTriggeredAbility(), new ParadoxHazeWatcher());
    }

    private ParadoxHaze(final ParadoxHaze card) {
        super(card);
    }

    @Override
    public ParadoxHaze copy() {
        return new ParadoxHaze(this);
    }
}

class ParadoxHazeTriggeredAbility extends TriggeredAbilityImpl {

    ParadoxHazeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ParadoxHazeEffect(), false);
    }

    private ParadoxHazeTriggeredAbility(final ParadoxHazeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ParadoxHazeTriggeredAbility copy() {
        return new ParadoxHazeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent == null
                || !game.isActivePlayer(permanent.getAttachedTo())
                || game.getState().getWatcher(ParadoxHazeWatcher.class).conditionMet()) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(permanent.getAttachedTo()));
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.";
    }
}

class ParadoxHazeEffect extends OneShotEffect {

    ParadoxHazeEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player gets an additional upkeep step after this step";
    }

    private ParadoxHazeEffect(final ParadoxHazeEffect effect) {
        super(effect);
    }

    @Override
    public ParadoxHazeEffect copy() {
        return new ParadoxHazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(this.getTargetPointer().getFirst(game, source), new UpkeepStep(), null));
        return true;
    }
}

class ParadoxHazeWatcher extends Watcher {

    ParadoxHazeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.UPKEEP_STEP_POST) {
            condition = true;
        }
    }
}
