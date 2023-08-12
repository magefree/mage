package mage.cards.d;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import mage.*;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author Susucr
 */
public final class DelightedHalfling extends CardImpl {

    public DelightedHalfling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {T}: Add one mana of any color. Spend this mana only to cast a legendary spell, and that spell can't be countered.
        Ability ability = new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new DelightedHalflingManaBuilder(), true);
        this.addAbility(ability, new DelightedHalflingWatcher(ability.getOriginalId()));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new DelightedHalflingCantCounterEffect()));
    }

    private DelightedHalfling(final DelightedHalfling card) {
        super(card);
    }

    @Override
    public DelightedHalfling copy() {
        return new DelightedHalfling(this);
    }
}


class DelightedHalflingManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new DelightedHalflingConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a legendary spell, and that spell can't be countered";
    }

}

class DelightedHalflingConditionalMana extends ConditionalMana {
    public DelightedHalflingConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a legendary spell, and that spell can't be countered";
        addCondition(new DelightedHalflingManaCondition());
    }
}

class DelightedHalflingManaCondition extends ManaCondition {

    DelightedHalflingManaCondition() { }

    @Override
    public boolean apply(Game game, Ability source) {
        // check: ... to cast a spell
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            // check: ... that is legendary
            if (object != null && object.isLegendary(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}

class DelightedHalflingWatcher extends Watcher {

    private final Set<MageObjectReference> spells = new HashSet<>();
    private final UUID originalId;

    public DelightedHalflingWatcher(UUID originalId) {
        super(WatcherScope.CARD);
        this.originalId = originalId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            if (event.getData() != null && event.getData().equals(originalId.toString()) && event.getTargetId() != null) {
                spells.add(new MageObjectReference(game.getObject(event.getTargetId()), game));
            }
        }
    }

    public boolean spellCantBeCountered(MageObjectReference mor) {
        return spells.contains(mor);
    }

    @Override
    public void reset() {
        super.reset();
        spells.clear();
    }
}

class DelightedHalflingCantCounterEffect extends ContinuousRuleModifyingEffectImpl {

    public DelightedHalflingCantCounterEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = null;
    }

    public DelightedHalflingCantCounterEffect(final DelightedHalflingCantCounterEffect effect) {
        super(effect);
    }

    @Override
    public DelightedHalflingCantCounterEffect copy() {
        return new DelightedHalflingCantCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null) {
            return "This spell can't be countered because a colored mana from " + sourceObject.getName() + " was spent to cast it.";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DelightedHalflingWatcher watcher = game.getState().getWatcher(DelightedHalflingWatcher.class, source.getSourceId());
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && watcher != null && watcher.spellCantBeCountered(new MageObjectReference(spell, game));
    }
}
