package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SwordOfKaldra extends CardImpl {

    public SwordOfKaldra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +5/+5.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(5, 5, Duration.WhileOnBattlefield)));
        // Whenever equipped creature deals damage to a creature, exile that creature.
        this.addAbility(new SwordOfKaldraTriggeredAbility());
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{4}")));
    }

    private SwordOfKaldra(final SwordOfKaldra card) {
        super(card);
    }

    @Override
    public SwordOfKaldra copy() {
        return new SwordOfKaldra(this);
    }
}

class SwordOfKaldraTriggeredAbility extends TriggeredAbilityImpl {

    public SwordOfKaldraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect("exile that creature. (Exile it only if it's still on the battlefield.)"), false);
    }

    public SwordOfKaldraTriggeredAbility(final SwordOfKaldraTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfKaldraTriggeredAbility copy() {
        return new SwordOfKaldraTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.getSourceId());
        if (equipment != null
                && equipment.getAttachedTo() != null
                && event.getSourceId().equals(equipment.getAttachedTo())) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever equipped creature deals damage to a creature, ";
    }

}
