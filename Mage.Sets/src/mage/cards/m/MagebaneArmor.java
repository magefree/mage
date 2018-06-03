
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class MagebaneArmor extends CardImpl {

    public MagebaneArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+4 and loses flying.
        this.addAbility(new MagebaneArmorAbility());
        // Prevent all noncombat damage that would be dealt to equipped creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MagebaneArmorPreventionEffect()));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public MagebaneArmor(final MagebaneArmor card) {
        super(card);
    }

    @Override
    public MagebaneArmor copy() {
        return new MagebaneArmor(this);
    }
}

class MagebaneArmorAbility extends StaticAbility {

    public MagebaneArmorAbility() {
        super(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 4));
        this.addEffect(new LoseAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT));
    }

    public MagebaneArmorAbility(MagebaneArmorAbility ability) {
        super(ability);
    }

    @Override
    public MagebaneArmorAbility copy() {
        return new MagebaneArmorAbility(this);
    }

    @Override
    public String getRule() {
        return "Equipped creature gets +2/+4 and loses flying.";
    }
}

class MagebaneArmorPreventionEffect extends PreventionEffectImpl {

    public MagebaneArmorPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Prevent all noncombat damage that would be dealt to equipped creature";
    }

    public MagebaneArmorPreventionEffect(final MagebaneArmorPreventionEffect effect) {
        super(effect);
    }

    @Override
    public MagebaneArmorPreventionEffect copy() {
        return new MagebaneArmorPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, equipment.getAttachedTo(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
            if (!game.replaceEvent(preventEvent)) {
                int damage = event.getAmount();
                event.setAmount(0);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, equipment.getAttachedTo(), source.getSourceId(), source.getControllerId(), damage));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && !((DamageEvent) event).isCombatDamage()) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null
                    && event.getTargetId().equals(equipment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }
}
