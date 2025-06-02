package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EshkiDragonclaw extends CardImpl {

    public EshkiDragonclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // At the beginning of combat on your turn, if you've cast both a creature spell and a noncreature spell this turn, draw a card and put two +1/+1 counters on Eshki Dragonclaw.
        Ability ability = new BeginningOfCombatTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(EshkiDragonclawCondition.instance);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).concatBy("and"));
        this.addAbility(ability.addHint(EshkiDragonclawHint.instance), new EshkiDragonclawWatcher());
    }

    private EshkiDragonclaw(final EshkiDragonclaw card) {
        super(card);
    }

    @Override
    public EshkiDragonclaw copy() {
        return new EshkiDragonclaw(this);
    }
}

enum EshkiDragonclawCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return EshkiDragonclawWatcher.checkPlayer(source.getControllerId(), game) == 3;
    }

    @Override
    public String toString() {
        return "you've cast both a creature spell and a noncreature spell this turn";
    }
}

enum EshkiDragonclawHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        switch (EshkiDragonclawWatcher.checkPlayer(ability.getControllerId(), game)) {
            case 0:
                return null;
            case 1:
                return "You've cast a creature spell this turn";
            case 2:
                return "You've cast a noncreature spell this turn";
            case 3:
                return "You've cast a creature spell and a noncreature spell this turn";
        }
        return null;
    }

    @Override
    public Hint copy() {
        return this;
    }
}

class EshkiDragonclawWatcher extends Watcher {

    private final Map<UUID, Integer> creatureCount = new HashMap<>();
    private final Map<UUID, Integer> nonCreatureCount = new HashMap<>();

    EshkiDragonclawWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null) {
            return;
        }
        if (spell.isCreature(game)) {
            creatureCount.compute(spell.getControllerId(), CardUtil::setOrIncrementValue);
        } else {
            nonCreatureCount.compute(spell.getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatureCount.clear();
        nonCreatureCount.clear();
    }

    private int checkCreature(UUID playerId) {
        return creatureCount.getOrDefault(playerId, 0) > 0 ? 1 : 0;
    }

    private int checkNonCreature(UUID playerId) {
        return nonCreatureCount.getOrDefault(playerId, 0) > 0 ? 2 : 0;
    }

    private int check(UUID playerId) {
        return checkCreature(playerId) + checkNonCreature(playerId);
    }

    static int checkPlayer(UUID playerId, Game game) {
        return game.getState().getWatcher(EshkiDragonclawWatcher.class).check(playerId);
    }
}
