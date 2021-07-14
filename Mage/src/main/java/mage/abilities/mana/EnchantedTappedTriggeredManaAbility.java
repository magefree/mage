package mage.abilities.mana;

import mage.abilities.effects.mana.ManaEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class EnchantedTappedTriggeredManaAbility extends TriggeredManaAbility {

    private final String permName;

    public EnchantedTappedTriggeredManaAbility(ManaEffect manaEffect) {
        this(manaEffect, "land");
    }

    public EnchantedTappedTriggeredManaAbility(ManaEffect manaEffect, String permName) {
        super(Zone.BATTLEFIELD, manaEffect);
        this.permName = permName;
    }

    private EnchantedTappedTriggeredManaAbility(final EnchantedTappedTriggeredManaAbility ability) {
        super(ability);
        this.permName = ability.permName;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment == null || !event.getSourceId().equals(enchantment.getAttachedTo())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(game.getControllerId(enchantment.getAttachedTo())));
        return true;
    }

    @Override
    public EnchantedTappedTriggeredManaAbility copy() {
        return new EnchantedTappedTriggeredManaAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever enchanted " + permName + " is tapped for mana, " ;
    }
}
