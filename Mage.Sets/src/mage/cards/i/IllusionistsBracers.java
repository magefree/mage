package mage.cards.i;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IllusionistsBracers extends CardImpl {

    public IllusionistsBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.
        this.addAbility(new IllusionistsBracersTriggeredAbility());

        // Equip 3
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private IllusionistsBracers(final IllusionistsBracers card) {
        super(card);
    }

    @Override
    public IllusionistsBracers copy() {
        return new IllusionistsBracers(this);
    }
}

class IllusionistsBracersTriggeredAbility extends TriggeredAbilityImpl {

    IllusionistsBracersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyStackObjectEffect());
    }

    private IllusionistsBracersTriggeredAbility(final IllusionistsBracersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IllusionistsBracersTriggeredAbility copy() {
        return new IllusionistsBracersTriggeredAbility(this);
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
        this.getEffects().setValue("stackObject", stackAbility);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an ability of equipped creature is activated, if it isn't a mana ability, " +
                "copy that ability. You may choose new targets for the copy.";
    }
}
