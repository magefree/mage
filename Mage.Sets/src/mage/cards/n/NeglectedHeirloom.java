
package mage.cards.n;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author halljared
 */
public final class NeglectedHeirloom extends CardImpl {

    public NeglectedHeirloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        this.secondSideCardClazz = mage.cards.a.AshmouthBlade.class;

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));

        // When equipped creature transforms, transform Neglected Heirloom.
        this.addAbility(new TransformAbility());
        this.addAbility(new NeglectedHeirloomTriggeredAbility());

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private NeglectedHeirloom(final NeglectedHeirloom card) {
        super(card);
    }

    @Override
    public NeglectedHeirloom copy() {
        return new NeglectedHeirloom(this);
    }

}

class NeglectedHeirloomTriggeredAbility extends TriggeredAbilityImpl {

    public NeglectedHeirloomTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect(), false);
    }

    public NeglectedHeirloomTriggeredAbility(final NeglectedHeirloomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TRANSFORMED) {
            if (game.getPermanent(event.getTargetId()).getAttachments().contains(this.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public NeglectedHeirloomTriggeredAbility copy() {
        return new NeglectedHeirloomTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When equipped creature transforms, transform Neglected Heirloom.";
    }
}