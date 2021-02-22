package mage.cards.g;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiantsSkewer extends CardImpl {

    public GiantsSkewer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Whenever equipped creature deals combat damage to a creature, create a Food token.
        this.addAbility(new GiantsSkewerTriggeredAbility());

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private GiantsSkewer(final GiantsSkewer card) {
        super(card);
    }

    @Override
    public GiantsSkewer copy() {
        return new GiantsSkewer(this);
    }
}

class GiantsSkewerTriggeredAbility extends TriggeredAbilityImpl {

    GiantsSkewerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new FoodToken()));
    }

    private GiantsSkewerTriggeredAbility(final GiantsSkewerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GiantsSkewerTriggeredAbility copy() {
        return new GiantsSkewerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null && permanent.getAttachments().contains(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a creature, create a Food token.";
    }
}
