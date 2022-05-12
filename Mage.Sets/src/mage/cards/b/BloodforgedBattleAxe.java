
package mage.cards.b;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class BloodforgedBattleAxe extends CardImpl {

    public BloodforgedBattleAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0)));

        // Whenever equipped creature deals combat damage to a player, create a token that's a copy of Bloodforged War Axe.
        this.addAbility(new BloodforgedBattleAxeAbility());

        // Equip 2
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));

    }

    private BloodforgedBattleAxe(final BloodforgedBattleAxe card) {
        super(card);
    }

    @Override
    public BloodforgedBattleAxe copy() {
        return new BloodforgedBattleAxe(this);
    }
}

class BloodforgedBattleAxeAbility extends TriggeredAbilityImpl {

    public BloodforgedBattleAxeAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenCopySourceEffect());
    }

    public BloodforgedBattleAxeAbility(final BloodforgedBattleAxeAbility ability) {
        super(ability);
    }

    @Override
    public BloodforgedBattleAxeAbility copy() {
        return new BloodforgedBattleAxeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, create a token that's a copy of {this}.";
    }
}
