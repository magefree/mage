
package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnattachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
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
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class StitchersGraft extends CardImpl {

    public StitchersGraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 3)));

        // Whenever equipped creature attacks, it doesn't untap during its controller's next untap step.
        this.addAbility(new StitchersGraftTriggeredAbility());

        // Whenever Stitcher's Graft becomes unattached from a permanent, sacrifice that permanent.
        Effect effect = new SacrificeTargetEffect();
        effect.setText("sacrifice that permanent");
        this.addAbility(new UnattachedTriggeredAbility(effect, false));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private StitchersGraft(final StitchersGraft card) {
        super(card);
    }

    @Override
    public StitchersGraft copy() {
        return new StitchersGraft(this);
    }
}

class StitchersGraftTriggeredAbility extends TriggeredAbilityImpl {

    public StitchersGraftTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepTargetEffect());
    }

    public StitchersGraftTriggeredAbility(final StitchersGraftTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StitchersGraftTriggeredAbility copy() {
        return new StitchersGraftTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.sourceId);
        if (equipment != null && equipment.getAttachedTo() != null
                && event.getSourceId().equals(equipment.getAttachedTo())) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks, it doesn't untap during its controller's next untap step.";
    }
}
