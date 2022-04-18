package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FleshReaver extends CardImpl {

    public FleshReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Flesh Reaver deals damage to a creature or opponent, Flesh Reaver deals that much damage to you.
        this.addAbility(new FleshReaverTriggeredAbility());
    }

    private FleshReaver(final FleshReaver card) {
        super(card);
    }

    @Override
    public FleshReaver copy() {
        return new FleshReaver(this);
    }
}

class FleshReaverTriggeredAbility extends TriggeredAbilityImpl {

    FleshReaverTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageControllerEffect(SavedDamageValue.MUCH));
    }

    private FleshReaverTriggeredAbility(final FleshReaverTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public FleshReaverTriggeredAbility copy() {
        return new FleshReaverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if ((permanent != null && permanent.isCreature(game))
                || game.getOpponents(event.getTargetId()).contains(getControllerId())) {
            this.getEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} deals damage to a creature or opponent, ";
    }
}
