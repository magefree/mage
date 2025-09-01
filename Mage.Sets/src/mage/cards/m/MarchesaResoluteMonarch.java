package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchesaResoluteMonarch extends CardImpl {

    public MarchesaResoluteMonarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);
        this.color.setBlack(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Marchesa, Resolute Monarch attacks, remove all counters from up to one target permanent.
        Ability ability = new AttacksTriggeredAbility(new RemoveAllCountersPermanentTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT));
        this.addAbility(ability);

        // At the beginning of your upkeep, if you haven't been dealt combat damage since your last turn, you draw a card and you lose 1 life.
        ability = new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1, true))
                .withInterveningIf(MarchesaResoluteMonarchCondition.instance);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private MarchesaResoluteMonarch(final MarchesaResoluteMonarch card) {
        super(card);
    }

    @Override
    public MarchesaResoluteMonarch copy() {
        return new MarchesaResoluteMonarch(this);
    }

    public static MarchesaResoluteMonarchWatcher makeWatcher() {
        return new MarchesaResoluteMonarchWatcher();
    }
}

enum MarchesaResoluteMonarchCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return MarchesaResoluteMonarchWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you haven't been dealt combat damage since your last turn";
    }
}

class MarchesaResoluteMonarchWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    MarchesaResoluteMonarchWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PLAYER:
                if (((DamagedEvent) event).isCombatDamage()) {
                    players.add(event.getTargetId());
                }
                return;
            case END_TURN_STEP_POST:
                players.remove(game.getActivePlayerId());
                return;

        }
    }

    static boolean checkPlayer(Game game, Ability source) {
        return !game
                .getState()
                .getWatcher(MarchesaResoluteMonarchWatcher.class)
                .players
                .contains(source.getControllerId());
    }
}
