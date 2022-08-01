
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class PyromancersGoggles extends CardImpl {

    public PyromancersGoggles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        addSuperType(SuperType.LEGENDARY);

        // {T}: Add {R}.
        Ability ability = new RedManaAbility();
        this.addAbility(ability);

        // When that mana is used to cast a red instant or sorcery spell, copy that spell and you may choose new targets for the copy.
        Effect effect = new CopyTargetSpellEffect(true);
        effect.setText("copy that spell and you may choose new targets for the copy");
        this.addAbility(new PyromancersGogglesTriggeredAbility(ability.getOriginalId(), effect));

    }

    private PyromancersGoggles(final PyromancersGoggles card) {
        super(card);
    }

    @Override
    public PyromancersGoggles copy() {
        return new PyromancersGoggles(this);
    }
}

class PyromancersGogglesTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    String abilityOriginalId;

    public PyromancersGogglesTriggeredAbility(UUID abilityOriginalId, Effect effect) {
        super(Zone.ALL, effect, false);
        this.abilityOriginalId = abilityOriginalId.toString();
    }

    public PyromancersGogglesTriggeredAbility(final PyromancersGogglesTriggeredAbility ability) {
        super(ability);
        this.abilityOriginalId = ability.abilityOriginalId;
    }

    @Override
    public PyromancersGogglesTriggeredAbility copy() {
        return new PyromancersGogglesTriggeredAbility(this);
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
        return "When that mana is used to cast a red instant or sorcery spell, " ;
    }
}
