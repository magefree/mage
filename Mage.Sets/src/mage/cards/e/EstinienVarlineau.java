package mage.cards.e;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class EstinienVarlineau extends CardImpl {

    public EstinienVarlineau(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, put a +1/+1 counter on Estinien Varlineau. It gains flying until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains flying until end of turn"));
        this.addAbility(ability);

        // At the beginning of your second main phase, you draw X cards and lose X life, where X is the number of your opponents who were dealt combat damage by Estinien Varlineau or a Dragon this turn.
        ability = new BeginningOfSecondMainTriggeredAbility(
                new DrawCardSourceControllerEffect(EstinienVarlineauValue.instance)
                        .setText("you draw X cards"), false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(EstinienVarlineauValue.instance)
                .setText("and lose X life, where X is the number of your opponents " +
                        "who were dealt combat damage by {this} or a Dragon this turn"));
        this.addAbility(ability, new EstinienVarlineauWatcher());
    }

    private EstinienVarlineau(final EstinienVarlineau card) {
        super(card);
    }

    @Override
    public EstinienVarlineau copy() {
        return new EstinienVarlineau(this);
    }
}

enum EstinienVarlineauValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return EstinienVarlineauWatcher.getCount(game, sourceAbility);
    }

    @Override
    public EstinienVarlineauValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class EstinienVarlineauWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> morMap = new HashMap<>();
    private final Set<UUID> dragonSet = new HashSet<>();

    EstinienVarlineauWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER) {
            return;
        }
        DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
        if (!dEvent.isCombatDamage()) {
            return;
        }
        Permanent permanent = game.getPermanent(dEvent.getSourceId());
        if (permanent == null) {
            return;
        }
        morMap.computeIfAbsent(
                new MageObjectReference(permanent, game), x -> new HashSet<>()
        ).add(dEvent.getTargetId());
        if (permanent.hasSubtype(SubType.DRAGON, game)) {
            dragonSet.add(dEvent.getTargetId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        morMap.clear();
        dragonSet.clear();
    }

    static int getCount(Game game, Ability source) {
        return game.getState()
                .getWatcher(EstinienVarlineauWatcher.class)
                .computeCount(
                        game.getOpponents(source.getControllerId()),
                        new MageObjectReference(source.getSourcePermanentOrLKI(game), game)
                );
    }

    private int computeCount(Set<UUID> opponents, MageObjectReference mor) {
        return opponents
                .stream()
                .filter(uuid -> dragonSet.contains(uuid) || morMap.getOrDefault(mor, Collections.emptySet()).contains(uuid))
                .mapToInt(x -> 1)
                .sum();
    }
}
