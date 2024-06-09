
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class EidolonOfTheGreatRevel extends CardImpl {

    public EidolonOfTheGreatRevel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player casts a spell with converted mana cost 3 or less, Eidolon of the Great Revel deals 2 damage to that player.
        this.addAbility(new EidolonOfTheGreatRevelTriggeredAbility());

    }

    private EidolonOfTheGreatRevel(final EidolonOfTheGreatRevel card) {
        super(card);
    }

    @Override
    public EidolonOfTheGreatRevel copy() {
        return new EidolonOfTheGreatRevel(this);
    }
}


class EidolonOfTheGreatRevelTriggeredAbility extends TriggeredAbilityImpl {


    public EidolonOfTheGreatRevelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"));
    }


    private EidolonOfTheGreatRevelTriggeredAbility(final EidolonOfTheGreatRevelTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public EidolonOfTheGreatRevelTriggeredAbility copy() {
        return new EidolonOfTheGreatRevelTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getManaValue() <= 3){
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell with mana value 3 or less, {this} deals 2 damage to that player.";
    }
}
