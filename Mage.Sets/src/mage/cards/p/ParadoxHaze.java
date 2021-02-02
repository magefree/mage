
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.game.turn.UpkeepStep;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.FirstTimeStepWatcher;

/**
 *
 * @author emerald000
 */
public final class ParadoxHaze extends CardImpl {

    public ParadoxHaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.
        this.addAbility(new ParadoxHazeTriggeredAbility(), new FirstTimeStepWatcher(EventType.UPKEEP_STEP_POST));
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

    ParadoxHazeTriggeredAbility(final ParadoxHazeTriggeredAbility ability) {
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
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getAttachedTo());
            if (player != null && game.isActivePlayer(player.getId())) {
                FirstTimeStepWatcher watcher = game.getState().getWatcher(FirstTimeStepWatcher.class, EventType.UPKEEP_STEP_POST.toString());
                if (watcher != null && !watcher.conditionMet()) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                    return true;
                }
            }
        }
        return false;
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

    ParadoxHazeEffect(final ParadoxHazeEffect effect) {
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
