package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OathOfKaya extends CardImpl {

    public OathOfKaya(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);

        // When Oath of Kaya enters the battlefield, it deals 3 damage to any target and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Whenever an opponent attacks a planeswalker you control with one or more creatures, Oath of Kaya deals 2 damage to that player and you gain 2 life.
        this.addAbility(new OathOfKayaTriggeredAbility());
    }

    private OathOfKaya(final OathOfKaya card) {
        super(card);
    }

    @Override
    public OathOfKaya copy() {
        return new OathOfKaya(this);
    }
}

class OathOfKayaTriggeredAbility extends TriggeredAbilityImpl {
    private final Set<UUID> attackedThisCombat = new HashSet();

    OathOfKayaTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private OathOfKayaTriggeredAbility(final OathOfKayaTriggeredAbility ability) {
        super(ability);
        this.attackedThisCombat.addAll(ability.attackedThisCombat);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                || event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_POST) {
            this.attackedThisCombat.clear();
            return false;
        }
        for (UUID attackerId : game.getCombat().getAttackers()) {
            Permanent attacker = game.getPermanent(attackerId);
            if (attacker == null) {
                continue;
            }
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(attackerId, game);
            UUID defenderId = game.getCombat().getDefenderId(attackerId);
            if (defendingPlayerId.equals(defenderId) || attackedThisCombat.contains(defenderId)) {
                continue;
            }
            attackedThisCombat.add(defenderId);
            this.getEffects().clear();
            Effect effect = new DamageTargetEffect(2);
            effect.setTargetPointer(new FixedTarget(defendingPlayerId, game));
            this.addEffect(effect);
            this.addEffect(new GainLifeEffect(2));
            return true;
        }
        return false;
    }

    @Override
    public OathOfKayaTriggeredAbility copy() {
        return new OathOfKayaTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks a planeswalker you control with one or more creatures, " +
                "{this} deals 2 damage to that player and you gain 2 life.";
    }
}
