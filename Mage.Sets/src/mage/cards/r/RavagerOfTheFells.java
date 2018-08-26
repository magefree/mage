
package mage.cards.r;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author BetaSteward
 */
public final class RavagerOfTheFells extends CardImpl {

    public RavagerOfTheFells(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature transforms into Ravager of the Fells, it deals 2 damage to target opponent and 2 damage to up to one target creature that player controls.
        this.addAbility(new RavagerOfTheFellsAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ravager of the Fells.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                TwoOrMoreSpellsWereCastLastTurnCondition.instance,
                TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE
        ));
    }

    public RavagerOfTheFells(final RavagerOfTheFells card) {
        super(card);
    }

    @Override
    public RavagerOfTheFells copy() {
        return new RavagerOfTheFells(this);
    }
}

class RavagerOfTheFellsAbility extends TriggeredAbilityImpl {

    public RavagerOfTheFellsAbility() {
        super(Zone.BATTLEFIELD, new RavagerOfTheFellsEffect(), false);
        Target target1 = new TargetOpponentOrPlaneswalker();
        this.addTarget(target1);
        this.addTarget(new RavagerOfTheFellsTarget());
    }

    public RavagerOfTheFellsAbility(final RavagerOfTheFellsAbility ability) {
        super(ability);
    }

    @Override
    public RavagerOfTheFellsAbility copy() {
        return new RavagerOfTheFellsAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.isTransformed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature transforms into {this}, "
                + "it deals 2 damage to target opponent or planeswalker "
                + "and 2 damage to up to one target creature that player or that planeswalker's controller controls.";
    }

}

class RavagerOfTheFellsEffect extends OneShotEffect {

    public RavagerOfTheFellsEffect() {
        super(Outcome.Damage);
    }

    public RavagerOfTheFellsEffect(final RavagerOfTheFellsEffect effect) {
        super(effect);
    }

    @Override
    public RavagerOfTheFellsEffect copy() {
        return new RavagerOfTheFellsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.damagePlayerOrPlaneswalker(source.getTargets().get(0).getFirstTarget(), 2, source.getSourceId(), game, false, true);
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.damage(2, source.getSourceId(), game, false, true);
        }
        return true;
    }

}

class RavagerOfTheFellsTarget extends TargetPermanent {

    public RavagerOfTheFellsTarget() {
        super(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false);
    }

    public RavagerOfTheFellsTarget(final RavagerOfTheFellsTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        UUID firstTarget = player.getId();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.isControlledBy(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(sourceId);

        for (StackObject item : game.getState().getStack()) {
            if (item.getId().equals(sourceId)) {
                object = item;
            }
            if (item.getSourceId().equals(sourceId)) {
                object = item;
            }
        }

        if (object instanceof StackObject) {
            UUID playerId = ((StackObject) object).getStackAbility().getFirstTarget();
            Player player = game.getPlayerOrPlaneswalkerController(playerId);
            if (player != null) {
                for (UUID targetId : availablePossibleTargets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.isControlledBy(player.getId())) {
                        possibleTargets.add(targetId);
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public RavagerOfTheFellsTarget copy() {
        return new RavagerOfTheFellsTarget(this);
    }
}
