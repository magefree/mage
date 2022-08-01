
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class PrimalWellspring extends CardImpl {

    public PrimalWellspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;

        // Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        this.addAbility(ability);

        // When that mana is spent to cast an instant or sorcery spell, copy that spell and you may choose new targets for the copy.
        Effect effect = new CopyTargetSpellEffect(true);
        effect.setText("copy that spell and you may choose new targets for the copy");
        this.addAbility(new PyrimalWellspringTriggeredAbility(ability.getOriginalId(), effect));
    }

    private PrimalWellspring(final PrimalWellspring card) {
        super(card);
    }

    @Override
    public PrimalWellspring copy() {
        return new PrimalWellspring(this);
    }
}

class PyrimalWellspringTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();

    String abilityOriginalId;

    public PyrimalWellspringTriggeredAbility(UUID abilityOriginalId, Effect effect) {
        super(Zone.ALL, effect, false);
        this.abilityOriginalId = abilityOriginalId.toString();
    }

    public PyrimalWellspringTriggeredAbility(final PyrimalWellspringTriggeredAbility ability) {
        super(ability);
        this.abilityOriginalId = ability.abilityOriginalId;
    }

    @Override
    public PyrimalWellspringTriggeredAbility copy() {
        return new PyrimalWellspringTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(abilityOriginalId)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, getControllerId(), this, game)) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When that mana is used to cast an instant or sorcery spell, " ;
    }
}
