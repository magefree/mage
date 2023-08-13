package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.turn.TurnMod;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphinxOfTheSecondSun extends CardImpl {

    public SphinxOfTheSecondSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your postcombat main phase, you get an additional beginning phase after this phase. (The beginning phase includes the untap, upkeep, and draw steps.)
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(
                new SphinxOfTheSecondSunEffect(), TargetController.YOU, false
        ), new SphinxOfTheSecondSunWatcher());
    }

    private SphinxOfTheSecondSun(final SphinxOfTheSecondSun card) {
        super(card);
    }

    @Override
    public SphinxOfTheSecondSun copy() {
        return new SphinxOfTheSecondSun(this);
    }
}

class SphinxOfTheSecondSunEffect extends OneShotEffect {

    SphinxOfTheSecondSunEffect() {
        super(Outcome.Benefit);
        staticText = "you get an additional beginning phase after this phase. <i>(The beginning phase includes the untap, upkeep, and draw steps.)</i>";
    }

    private SphinxOfTheSecondSunEffect(final SphinxOfTheSecondSunEffect effect) {
        super(effect);
    }

    @Override
    public SphinxOfTheSecondSunEffect copy() {
        return new SphinxOfTheSecondSunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TurnPhase turnPhase = game.getTurnPhaseType();
        for (TurnMod turnMod : game.getState().getTurnMods()) {
            if ("sphinxSecondSun".equals(turnMod.getTag())
                    && turnMod.getPlayerId().equals(source.getControllerId())
                    && turnMod.getAfterPhase() == turnPhase) {
                turnPhase = TurnPhase.BEGINNING;
                turnMod.withTag("sphinxSecondSunIgnore");
                break;
            }
        }
        TurnMod newPhase = new TurnMod(source.getControllerId())
                .withExtraPhase(TurnPhase.BEGINNING, turnPhase)
                .withTag("sphinxSecondSun");
        game.getState().getTurnMods().add(newPhase);
        return true;
    }
}

class SphinxOfTheSecondSunWatcher extends Watcher {

    SphinxOfTheSecondSunWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE) {
            return;
        }
        for (TurnMod turnMod : game.getState().getTurnMods()) {
            if ("sphinxSecondSun".equals(turnMod.getTag())) {
                turnMod.withTag("sphinxSecondSunIgnore");
            }
        }
    }
}
