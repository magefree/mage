package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorShieldHoplite extends CardImpl {

    public MirrorShieldHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a creature you control becomes the target of a backup ability, copy that ability. You may choose new targets for the copy. This ability triggers only once each turn.
        this.addAbility(new MirrorShieldHopliteTriggeredAbility());
    }

    private MirrorShieldHoplite(final MirrorShieldHoplite card) {
        super(card);
    }

    @Override
    public MirrorShieldHoplite copy() {
        return new MirrorShieldHoplite(this);
    }
}

class MirrorShieldHopliteTriggeredAbility extends TriggeredAbilityImpl {

    MirrorShieldHopliteTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyStackObjectEffect());
        this.setTriggerPhrase("Whenever a creature you control becomes the target of a backup ability, ");
        this.setTriggersOnceEachTurn(true);
    }

    private MirrorShieldHopliteTriggeredAbility(final MirrorShieldHopliteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MirrorShieldHopliteTriggeredAbility copy() {
        return new MirrorShieldHopliteTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
        if (sourceObject == null || !(sourceObject.getStackAbility() instanceof BackupAbility)) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game) || !permanent.isControlledBy(this.getControllerId())) {
            return false;
        }
        this.getEffects().setValue("stackObject", sourceObject);
        return true;
    }
}
