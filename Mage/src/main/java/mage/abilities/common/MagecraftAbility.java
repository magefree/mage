package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 * @author TheElk801
 */
public class MagecraftAbility extends TriggeredAbilityImpl {

    public static final String SPELL_KEY = "castCopiedSpell";

    public MagecraftAbility(Effect effect) {
        this(effect, false);
    }

    public MagecraftAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setAbilityWord(AbilityWord.MAGECRAFT);
        setTriggerPhrase("Whenever you cast or copy an instant or sorcery spell, ");
    }

    private MagecraftAbility(final MagecraftAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPIED_STACKOBJECT
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null
                || !spell.isControlledBy(getControllerId())
                || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        getEffects().setValue(SPELL_KEY, spell);
        return true;
    }

    @Override
    public MagecraftAbility copy() {
        return new MagecraftAbility(this);
    }
}
