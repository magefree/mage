package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.keyword.DemonstrateAbility;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.players.Player;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;
import java.util.List;


//TODO manage imports

/**
 * @author Mo1eculeMan
 */
public final class TheTwelfthDoctor extends CardImpl {

    public TheTwelfthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

    // The first spell you cast from anywhere other than your hand each turn has demonstrate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new TheTwelfthDoctorGainDemonstrateFirstSpellCastFromNotHandEffect()),
            new TheTwelfthDoctorWatcher());
        this.addAbility(new TheTwelfthDoctorCopyTriggeredAbility());
    }

    private TheTwelfthDoctor(final TheTwelfthDoctor card) {
        super(card);
    }

    @Override
    public TheTwelfthDoctor copy() {
        return new TheTwelfthDoctor(this);
    }
}

class TheTwelfthDoctorGainDemonstrateFirstSpellCastFromNotHandEffect extends ContinuousEffectImpl {

    private final Ability DemonstrateAbility = new DemonstrateAbility();

    public TheTwelfthDoctorGainDemonstrateFirstSpellCastFromNotHandEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "The first spell you cast from anywhere other than your hand each turn has demonstrate";
    }

    private TheTwelfthDoctorGainDemonstrateFirstSpellCastFromNotHandEffect(final TheTwelfthDoctorGainDemonstrateFirstSpellCastFromNotHandEffect effect) {
        super(effect);
    }

    @Override
    public TheTwelfthDoctorGainDemonstrateFirstSpellCastFromNotHandEffect copy() {
        return new TheTwelfthDoctorGainDemonstrateFirstSpellCastFromNotHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TheTwelfthDoctorWatcher watcher = game.getState().getWatcher(TheTwelfthDoctorWatcher.class);
        if (controller == null || watcher == null) {
            return false;
        }

        for (StackObject stackObject : game.getStack()) {
            // only spells cast, so no copies of spells
            System.out.print("Checking if Spell should be demonstrated.");
            if ((stackObject instanceof Spell)
                    && !stackObject.isCopy()
                    && stackObject.isControlledBy(source.getControllerId())) {
                Spell spell = (Spell) stackObject;
                
                System.out.print("It's demonstratable.");

                if (FirstSpellCastFromNotHandEachTurnCondition.instance.apply(game, source)) {
                    System.out.print("Demonstrate this beach.");
                    game.getState().addOtherAbility(spell.getCard(), DemonstrateAbility);
                }
            }
        }
        return true;
    }
}

enum FirstSpellCastFromNotHandEachTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getStack().isEmpty()) {
            return false;
        }
        TheTwelfthDoctorWatcher watcher = game.getState().getWatcher(TheTwelfthDoctorWatcher.class);
        StackObject so = game.getStack().getFirst();
        return watcher != null
                && TheTwelfthDoctorWatcher.checkSpell(so, game);
    }
}

class TheTwelfthDoctorWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> playerMap = new HashMap<>();

    TheTwelfthDoctorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CAST_SPELL
                || event.getZone() == Zone.HAND) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell == null) {
            return;
        }
        playerMap.computeIfAbsent(event.getPlayerId(), x -> new MageObjectReference(spell.getMainCard(), game));
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    static boolean checkSpell(StackObject stackObject, Game game) {
        if (stackObject.isCopy()
                || !(stackObject instanceof Spell)) {
            return false;
        }
        TheTwelfthDoctorWatcher watcher = game.getState().getWatcher(TheTwelfthDoctorWatcher.class);
        return watcher.playerMap.containsKey(stackObject.getControllerId())
                && watcher.playerMap.get(stackObject.getControllerId()).refersTo(((Spell) stackObject).getMainCard(), game);
    }
}

class TheTwelfthDoctorCopyTriggeredAbility extends TriggeredAbilityImpl {

    TheTwelfthDoctorCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private TheTwelfthDoctorCopyTriggeredAbility(final TheTwelfthDoctorCopyTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPIED_STACKOBJECT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        return spell != null && spell.isControlledBy(getControllerId());
    }

    @Override
    public TheTwelfthDoctorCopyTriggeredAbility copy() {
        return new TheTwelfthDoctorCopyTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you copy a spell, put a +1/+1 counter on {this}.";
    }
}