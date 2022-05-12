
package mage.cards.i;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class InfiltrationLens extends CardImpl {

    public InfiltrationLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature becomes blocked by a creature, you may draw two cards.
        this.addAbility(new EquippedBecomesBlockedTriggeredAbility(new DrawCardSourceControllerEffect(2), true));

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private InfiltrationLens(final InfiltrationLens card) {
        super(card);
    }

    @Override
    public InfiltrationLens copy() {
        return new InfiltrationLens(this);
    }
}

class EquippedBecomesBlockedTriggeredAbility extends TriggeredAbilityImpl {

    public EquippedBecomesBlockedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public EquippedBecomesBlockedTriggeredAbility(final EquippedBecomesBlockedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(sourceId);
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            if (equipped.getId().equals(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever equipped creature becomes blocked by a creature, " ;
    }

    @Override
    public EquippedBecomesBlockedTriggeredAbility copy() {
        return new EquippedBecomesBlockedTriggeredAbility(this);
    }
}