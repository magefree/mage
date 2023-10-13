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

    private BurningVengeance(final BurningVengeance card) {
        super(card);
    }

    @Override
    public BurningVengeance copy() {
        return new BurningVengeance(this);
    }
}

class BurningVengeanceOnCastAbility extends TriggeredAbilityImpl {

    BurningVengeanceOnCastAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
        this.addTarget(new TargetAnyTarget());
        setTriggerPhrase("Whenever you cast a spell from your graveyard, ");
    }

    private BurningVengeanceOnCastAbility(final BurningVengeanceOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId) && event.getZone() == Zone.GRAVEYARD;
    }

    @Override
    public BurningVengeanceOnCastAbility copy() {
        return new BurningVengeanceOnCastAbility(this);
    }

}
