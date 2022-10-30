package mage.cards.b;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class BattlemagesBracers extends CardImpl {

    public BattlemagesBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield
        )));

        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, you may pay {1}. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new BattlemagesBracersTriggeredAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private BattlemagesBracers(final BattlemagesBracers card) {
        super(card);
    }

    @Override
    public BattlemagesBracers copy() {
        return new BattlemagesBracers(this);
    }
}

class BattlemagesBracersTriggeredAbility extends TriggeredAbilityImpl {

    BattlemagesBracersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CopyStackObjectEffect(), new GenericManaCost(1)));
    }

    private BattlemagesBracersTriggeredAbility(final BattlemagesBracersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BattlemagesBracersTriggeredAbility copy() {
        return new BattlemagesBracersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.getSourceId());
        if (equipment == null || !equipment.isAttachedTo(event.getSourceId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null || stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl) {
            return false;
        }
        getEffects().setValue("stackObject", stackAbility);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an ability of equipped creature is activated, if it isn't a mana ability, you may pay {1}. " +
                "If you do, copy that ability. You may choose new targets for the copy.";
    }
}
