package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Viserion
 */
public final class SwordOfFeastAndFamine extends CardImpl {

    public SwordOfFeastAndFamine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from black and from green.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.GREEN), AttachmentType.EQUIPMENT
        ).setText("and has protection from black and from green"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, that player discards a card and you untap all lands you control.
        this.addAbility(new SwordOfFeastAndFamineAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private SwordOfFeastAndFamine(final SwordOfFeastAndFamine card) {
        super(card);
    }

    @Override
    public SwordOfFeastAndFamine copy() {
        return new SwordOfFeastAndFamine(this);
    }
}

class SwordOfFeastAndFamineAbility extends TriggeredAbilityImpl {

    public SwordOfFeastAndFamineAbility() {
        super(Zone.BATTLEFIELD, new DiscardTargetEffect(1));
        this.addEffect(new UntapAllLandsControllerEffect());
    }

    public SwordOfFeastAndFamineAbility(final SwordOfFeastAndFamineAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfFeastAndFamineAbility copy() {
        return new SwordOfFeastAndFamineAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent p = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, " +
                "that player discards a card and you untap all lands you control.";
    }
}
