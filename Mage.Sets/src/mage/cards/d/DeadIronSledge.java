
package mage.cards.d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author jerekwilson, ciaccona007
 */
public final class DeadIronSledge extends CardImpl {

    public DeadIronSledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature blocks or becomes blocked by a creature, destroy both creatures.
        this.addAbility(new DeadIronSledgeTriggeredAbility(new DeadIronSledgeDestroyEffect(), false));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private DeadIronSledge(final DeadIronSledge card) {
        super(card);
    }

    @Override
    public DeadIronSledge copy() {
        return new DeadIronSledge(this);
    }

}

class DeadIronSledgeTriggeredAbility extends TriggeredAbilityImpl {

    public DeadIronSledgeTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever equipped creature blocks or becomes blocked by a creature, ");
    }

    public DeadIronSledgeTriggeredAbility(final DeadIronSledgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(sourceId);
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            if (equipped.getId().equals(event.getTargetId()) || equipped.getId().equals(event.getSourceId())) {
                List<Permanent> targets = new ArrayList<>();

                Permanent blocker = game.getPermanent(event.getSourceId());
                if (blocker != null) {
                    targets.add(blocker);
                }

                Permanent blocked = game.getPermanent(event.getTargetId());
                if (blocked != null) {
                    targets.add(blocked);
                }
                this.getEffects().get(0).setTargetPointer(new FixedTargets(targets, game));

                return true;
            }
        }
        return false;
    }

    @Override
    public DeadIronSledgeTriggeredAbility copy() {
        return new DeadIronSledgeTriggeredAbility(this);
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
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}
