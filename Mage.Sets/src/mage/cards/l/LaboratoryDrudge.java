package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaboratoryDrudge extends CardImpl {

    public LaboratoryDrudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of each end step, draw a card if you've cast a spell from a graveyard or activated an ability of a card in a graveyard this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(1), LaboratoryDrudgeCondition.instance,
                        "draw a card if you've cast a spell from a graveyard or activated an ability of a card in a graveyard this turn"
                ), TargetController.ANY, false
        ), new LaboratoryDrudgeWatcher());
    }

    private LaboratoryDrudge(final LaboratoryDrudge card) {
        super(card);
    }

    @Override
    public LaboratoryDrudge copy() {
        return new LaboratoryDrudge(this);
    }
}

enum LaboratoryDrudgeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        LaboratoryDrudgeWatcher watcher = game.getState().getWatcher(LaboratoryDrudgeWatcher.class);
        return watcher != null && watcher.checkPlayer(source.getControllerId());
    }
}

class LaboratoryDrudgeWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    LaboratoryDrudgeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getZone() != Zone.GRAVEYARD) {
            return;
        }
        switch (event.getType()) {
            case SPELL_CAST:
            case ACTIVATED_ABILITY:
                playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        playerSet.clear();
        super.reset();
    }

    boolean checkPlayer(UUID playerId) {
        return playerSet.contains(playerId);
    }
}
