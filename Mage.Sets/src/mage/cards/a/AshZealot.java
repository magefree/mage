

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;


/**
 *
 * @author LevelX2
 */
public final class AshZealot extends CardImpl {

    public AshZealot (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike, haste
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // Whenever a player casts a spell from a graveyard, Ash Zealot deals 3 damage to that player.
        this.addAbility(new AshZealotTriggeredAbility());


    }

    private AshZealot(final AshZealot card) {
        super(card);
    }

    @Override
    public AshZealot copy() {
        return new AshZealot(this);
    }
}

class AshZealotTriggeredAbility extends TriggeredAbilityImpl {

    public AshZealotTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3), false);
    }

    private AshZealotTriggeredAbility(final AshZealotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AshZealotTriggeredAbility copy() {
        return new AshZealotTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getZone() == Zone.GRAVEYARD) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(spell.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell from a graveyard, {this} deals 3 damage to that player.";
    }
}