package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.ChooseBlockersRedundancyWatcher;

/**
 * @author noxx
 */
public final class OdricMasterTactician extends CardImpl {

    public OdricMasterTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Odric, Master Tactician and at least three other creatures attack, you choose which creatures block this combat and how those creatures block.
        this.addAbility(new OdricMasterTacticianTriggeredAbility());
    }

    public OdricMasterTactician(final OdricMasterTactician card) {
        super(card);
    }

    @Override
    public OdricMasterTactician copy() {
        return new OdricMasterTactician(this);
    }
}

class OdricMasterTacticianTriggeredAbility extends TriggeredAbilityImpl {

    public OdricMasterTacticianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OdricMasterTacticianChooseBlockersEffect());
        this.addWatcher(new ChooseBlockersRedundancyWatcher());
        this.addEffect(new ChooseBlockersRedundancyWatcherIncrementEffect());
    }

    public OdricMasterTacticianTriggeredAbility(final OdricMasterTacticianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OdricMasterTacticianTriggeredAbility copy() {
        return new OdricMasterTacticianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 4 && game.getCombat().getAttackers().contains(this.sourceId);
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
            ChooseBlockersRedundancyWatcher watcher = game.getState().getWatcher(ChooseBlockersRedundancyWatcher.class);
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

class OdricMasterTacticianChooseBlockersEffect extends ContinuousRuleModifyingEffectImpl {

    public OdricMasterTacticianChooseBlockersEffect() {
        super(Duration.EndOfCombat, Outcome.Benefit, false, false);
        staticText = "Whenever {this} and at least three other creatures attack, you choose which creatures block this combat and how those creatures block";
    }

    public OdricMasterTacticianChooseBlockersEffect(final OdricMasterTacticianChooseBlockersEffect effect) {
        super(effect);
    }

    @Override
    public OdricMasterTacticianChooseBlockersEffect copy() {
        return new OdricMasterTacticianChooseBlockersEffect(this);
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
        if (source.isControlledBy(event.getPlayerId())) {
            return false; // Don't replace the own call to selectBlockers
        }
        ChooseBlockersRedundancyWatcher watcher = game.getState().getWatcher(ChooseBlockersRedundancyWatcher.class);
        if (watcher == null) {
            return false;
        }
        watcher.decrement();
        watcher.copyCount--;
        if (watcher.copyCountApply > 0) {
            game.informPlayers(source.getSourceObject(game).getIdName() + " didn't apply");
            this.discard();
            return false;
        }
        watcher.copyCountApply = watcher.copyCount;
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, game);
            return true;
        }
        this.discard();
        return false;
    }
}
