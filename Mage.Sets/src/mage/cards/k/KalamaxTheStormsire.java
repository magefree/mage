package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author AsterAether
 */
public final class KalamaxTheStormsire extends CardImpl {

    public KalamaxTheStormsire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast your first instant spell each turn, if Kalamax, the Stormsire is tapped, copy that spell. You may choose new targets for the copy.
        this.addAbility(new KalamaxTheStormsireSpellCastAbility(), new SpellsCastWatcher());
        // Whenever you copy an instant spell, put a +1/+1 counter on Kalamax.
        this.addAbility(new KalamaxTheStormsireCopyTriggeredAbility());
    }

    private KalamaxTheStormsire(final KalamaxTheStormsire card) {
        super(card);
    }

    @Override
    public KalamaxTheStormsire copy() {
        return new KalamaxTheStormsire(this);
    }
}

class KalamaxTheStormsireSpellCastAbility extends SpellCastControllerTriggeredAbility {
    KalamaxTheStormsireSpellCastAbility() {
        super(new CopyTargetSpellEffect(true), new FilterInstantSpell(), false);
    }

    KalamaxTheStormsireSpellCastAbility(KalamaxTheStormsireSpellCastAbility ability) {
        super(ability);
    }

    @Override
    public KalamaxTheStormsireSpellCastAbility copy() {
        return new KalamaxTheStormsireSpellCastAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.stream().filter(spell1 -> spell1.isInstant(game)).count() == 1) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null && spell.isInstant(game)) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.isTapped();
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first instant spell each turn, " +
                "if Kalamax, the Stormsire is tapped, " +
                "copy that spell. You may choose new targets for the copy.";
    }
}

class KalamaxTheStormsireCopyTriggeredAbility extends TriggeredAbilityImpl {

    KalamaxTheStormsireCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private KalamaxTheStormsireCopyTriggeredAbility(final KalamaxTheStormsireCopyTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPIED_STACKOBJECT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        return spell != null && spell.isControlledBy(getControllerId()) && spell.isInstant(game);
    }

    @Override
    public KalamaxTheStormsireCopyTriggeredAbility copy() {
        return new KalamaxTheStormsireCopyTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you copy an instant spell, put a +1/+1 counter on {this}.";
    }
}