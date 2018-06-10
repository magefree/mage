

package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

/**
 * @author Loki
 */
public final class SwordOfFireAndIce extends CardImpl {

    public SwordOfFireAndIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from red and from blue.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ProtectionAbility.from(ObjectColor.RED, ObjectColor.BLUE), AttachmentType.EQUIPMENT)));
        // Whenever equipped creature deals combat damage to a player, Sword of Fire and Ice deals 2 damage to any target and you draw a card.
        this.addAbility(new SwordOfFireAndIceAbility());
        // Equip
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public SwordOfFireAndIce(final SwordOfFireAndIce card) {
        super(card);
    }

    @Override
    public SwordOfFireAndIce copy() {
        return new SwordOfFireAndIce(this);
    }

}

class SwordOfFireAndIceAbility extends TriggeredAbilityImpl {

    public SwordOfFireAndIceAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2));
        this.addEffect(new DrawCardSourceControllerEffect(1));
        this.addTarget(new TargetAnyTarget());
    }

    public SwordOfFireAndIceAbility(final SwordOfFireAndIceAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfFireAndIceAbility copy() {
        return new SwordOfFireAndIceAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, {this} deals 2 damage to any target and you draw a card.";
    }
}
