
package mage.cards.h;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class HedronBlade extends CardImpl {

    public HedronBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature becomes blocked by one or more colorless creatures, it gains deathtouch until end of turn.
        this.addAbility(new HedronBladeTriggeredAbility(
                new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.EndOfTurn)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private HedronBlade(final HedronBlade card) {
        super(card);
    }

    @Override
    public HedronBlade copy() {
        return new HedronBlade(this);
    }
}

class HedronBladeTriggeredAbility extends TriggeredAbilityImpl {

    HedronBladeTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    private HedronBladeTriggeredAbility(final HedronBladeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HedronBladeTriggeredAbility copy() {
        return new HedronBladeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanentOrLKIBattlefield((this.getSourceId()));
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equippedPermanent = game.getPermanentOrLKIBattlefield((equipment.getAttachedTo()));
            if (equippedPermanent != null) {
                if (equippedPermanent.isAttacking()) {
                    for (CombatGroup group : game.getCombat().getGroups()) {
                        if (group.getAttackers().contains(equippedPermanent.getId())) {
                            for (UUID blockerId : group.getBlockers()) {
                                Permanent blocker = game.getPermanent(blockerId);
                                if (blocker != null && blocker.getColor(game).isColorless()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature becomes blocked by one or more colorless creatures, "
                + "" + "it gains deathtouch until end of turn.";
    }
}
