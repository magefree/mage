
package mage.cards.d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author jerekwilson
 */
public final class DeadIronSledge extends CardImpl {

    public DeadIronSledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature blocks or becomes blocked by a creature, destroy both creatures.
        this.addAbility(new DeadIronSledgeTriggeredAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public DeadIronSledge(final DeadIronSledge card) {
        super(card);
    }

    @Override
    public DeadIronSledge copy() {
        return new DeadIronSledge(this);
    }

}

class DeadIronSledgeTriggeredAbility extends TriggeredAbilityImpl {

    private Set<UUID> possibleTargets = new HashSet<>();

    DeadIronSledgeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DeadIronSledgeDestroyEffect(), false);
    }

    DeadIronSledgeTriggeredAbility(final DeadIronSledgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<Permanent> targetPermanents = new ArrayList<>();
        Permanent equipment = game.getPermanentOrLKIBattlefield((this.getSourceId()));
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equippedPermanent = game.getPermanentOrLKIBattlefield((equipment.getAttachedTo()));
            if (equippedPermanent != null) {
                possibleTargets.clear();
                if (equippedPermanent.isBlocked(game)) {
                    possibleTargets.add(equippedPermanent.getId()); //add equipped creature to target list
                }
                if (equippedPermanent.isAttacking()) {
                    for (CombatGroup group : game.getCombat().getGroups()) {
                        if (group.getAttackers().contains(equippedPermanent.getId())) {
                            possibleTargets.addAll(group.getBlockers());
                        }
                    }
                } else if (equippedPermanent.getBlocking() > 0) {
                    for (CombatGroup group : game.getCombat().getGroups()) {
                        if (group.getBlockers().contains(equippedPermanent.getId())) {
                            possibleTargets.addAll(group.getAttackers());
                        }
                    }
                }
                if (!possibleTargets.isEmpty()) {
                    this.getTargets().clear();

                    for (UUID creatureId : possibleTargets) {
                        Permanent target = game.getPermanentOrLKIBattlefield(creatureId);
                        targetPermanents.add(target);
                    }

                    this.getEffects().get(0).setTargetPointer(new FixedTargets(targetPermanents, game));

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TriggeredAbility copy() {
        return new DeadIronSledgeTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature blocks or becomes blocked by a creature, destroy both creatures.";
    }
}

class DeadIronSledgeDestroyEffect extends OneShotEffect {

    public DeadIronSledgeDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy both creatures";
    }

    public DeadIronSledgeDestroyEffect(final DeadIronSledgeDestroyEffect effect) {
        super(effect);
    }

    @Override
    public DeadIronSledgeDestroyEffect copy() {
        return new DeadIronSledgeDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.destroy(targetId, game, false);
            }
        }
        return true;
    }
}
