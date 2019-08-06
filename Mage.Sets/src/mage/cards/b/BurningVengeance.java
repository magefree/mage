
package mage.cards.b;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetAnyTarget;

/**
 * @author nantuko
 */
public final class BurningVengeance extends CardImpl {

    public BurningVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");


        // Whenever you cast a spell from your graveyard, Burning Vengeance deals 2 damage to any target.
        this.addAbility(new BurningVengeanceOnCastAbility());
    }

    public BurningVengeance(final BurningVengeance card) {
        super(card);
    }

    @Override
    public BurningVengeance copy() {
        return new BurningVengeance(this);
    }
}

class BurningVengeanceOnCastAbility extends TriggeredAbilityImpl {

    private static final String abilityText = "Whenever you cast a spell from your graveyard, {this} deals 2 damage to any target";

    BurningVengeanceOnCastAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
        TargetAnyTarget target = new TargetAnyTarget();
        this.addTarget(target);
    }

    BurningVengeanceOnCastAbility(BurningVengeanceOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId) && event.getZone() == Zone.GRAVEYARD;
    }

    @Override
    public BurningVengeanceOnCastAbility copy() {
        return new BurningVengeanceOnCastAbility(this);
    }

    @Override
    public String getRule() {
        return abilityText;
    }
}
