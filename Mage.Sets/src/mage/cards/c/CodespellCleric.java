package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class CodespellCleric extends CardImpl {

    public CodespellCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Codespell Cleric enters the battlefield, if it was the second spell you cast this turn, put a +1/+1 counter on target creature.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance())),
                CodespellClericCondition.instance, "When {this} enters the battlefield, " +
                "if it was the second spell you cast this turn, put a +1/+1 counter on target creature."
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability, new CodespellClericWatcher());
    }

    private CodespellCleric(final CodespellCleric card) {
        super(card);
    }

    @Override
    public CodespellCleric copy() {
        return new CodespellCleric(this);
    }
}

enum CodespellClericCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CodespellClericWatcher watcher = game.getState().getWatcher(CodespellClericWatcher.class);
        return watcher != null && watcher.checkSpell(source, game);
    }
}

class CodespellClericWatcher extends Watcher {

    private final Map<UUID, List<MageObjectReference>> spellMap = new HashMap<>();
    private static final List<MageObjectReference> emptyList = Collections.unmodifiableList(new ArrayList<>());

    CodespellClericWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            spellMap.computeIfAbsent(event.getPlayerId(), x -> new ArrayList<>())
                    .add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        spellMap.clear();
    }

    boolean checkSpell(Ability source, Game game) {
        Permanent permanent = (Permanent) source.getEffects().get(0).getValue("permanentEnteredBattlefield");
        if (permanent == null) {
            return false;
        }
        int index = 0;
        for (MageObjectReference mor : spellMap.getOrDefault(source.getControllerId(), emptyList)) {
            index++;
            if (mor.getSourceId() == permanent.getId()
                    && mor.getZoneChangeCounter() + 1 == permanent.getZoneChangeCounter(game)) {
                return index == 2;
            }
        }
        return false;
    }
}
