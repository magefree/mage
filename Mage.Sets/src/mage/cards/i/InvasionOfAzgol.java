package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPlayer;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfAzgol extends TransformingDoubleFacedCard {

    public InvasionOfAzgol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{B}{R}",
                "Ashen Reaper",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE, SubType.ELEMENTAL}, "BR"
        );

        // Invasion of Azgol
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Azgol enters the battlefield, target player sacrifices a creature or planeswalker and loses 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A, 1, "target player"
        ));
        ability.addEffect(new LoseLifeTargetEffect(1).setText("and loses 1 life"));
        ability.addTarget(new TargetPlayer());
        ability.addWatcher(new AshenReaperWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Ashen Reaper
        this.getRightHalfCard().setPT(2, 1);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // At the beginning of your end step, put a +1/+1 counter on Ashen Reaper if a permanent was put into a graveyard from the battlefield this turn.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        AshenReaperCondition.instance, "put a +1/+1 counter on {this} " +
                        "if a permanent was put into a graveyard from the battlefield this turn"
                )
        ));
    }

    private InvasionOfAzgol(final InvasionOfAzgol card) {
        super(card);
    }

    @Override
    public InvasionOfAzgol copy() {
        return new InvasionOfAzgol(this);
    }
}

enum AshenReaperCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().getWatcher(AshenReaperWatcher.class).conditionMet();
    }
}

class AshenReaperWatcher extends Watcher {

    AshenReaperWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent()) {
            condition = true;
        }
    }
}