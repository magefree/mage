package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OathOfKaya extends CardImpl {

    public OathOfKaya(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Oath of Kaya enters the battlefield, it deals 3 damage to any target and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Whenever an opponent attacks a planeswalker you control with one or more creatures,
        // Oath of Kaya deals 2 damage to that player and you gain 2 life.
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

    public OathOfKayaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
        this.addEffect(new GainLifeEffect(2));
    }

    public OathOfKayaTriggeredAbility(final OathOfKayaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OathOfKayaTriggeredAbility copy() {
        return new OathOfKayaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player you = game.getPlayer(this.getControllerId());
        if (you == null) {
            return false;
        }

        if (game.getCombat().isPlaneswalkerAttacked(you.getId(), game)) {
            for (UUID attacker : game.getCombat().getAttackers()) {
                Permanent attackingPermanent = game.getPermanent(attacker);
                if (attackingPermanent != null && attackingPermanent.isCreature(game)) {
                    getEffects().setTargetPointer(new FixedTarget(attackingPermanent.getControllerId(), game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks a planeswalker you control with one or more creatures, "
                + "{this} deals 2 damage to that player and you gain 2 life.";
    }
}