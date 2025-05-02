
package mage.cards.n;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NullstoneGargoyle extends CardImpl {

    public NullstoneGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever the first noncreature spell of a turn is cast, counter that spell.
        this.addAbility(new NullstoneGargoyleTriggeredAbility());
    }

    private NullstoneGargoyle(final NullstoneGargoyle card) {
        super(card);
    }

    @Override
    public NullstoneGargoyle copy() {
        return new NullstoneGargoyle(this);
    }
}

class NullstoneGargoyleTriggeredAbility extends TriggeredAbilityImpl {

    public NullstoneGargoyleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect(), false);
    }

    private NullstoneGargoyleTriggeredAbility(final NullstoneGargoyleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NullstoneGargoyleTriggeredAbility copy() {
        return new NullstoneGargoyleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell.isCreature(game)) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null && watcher.getNumberOfNonCreatureSpells() == 1) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever the first noncreature spell of a turn is cast, counter that spell.";
    }
}
