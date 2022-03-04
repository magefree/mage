package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
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

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature transforms into Ravager of the Fells, it deals 2 damage to target opponent and 2 damage to up to one target creature that player controls.
        Ability ability = new TransformIntoSourceTriggeredAbility(
                new RavagerOfTheFellsEffect(), false, true
        );
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        ability.addTarget(new RavagerOfTheFellsTarget());
        this.addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ravager of the Fells.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private RavagerOfTheFells(final RavagerOfTheFells card) {
        super(card);
    }

    @Override
    public RavagerOfTheFells copy() {
        return new RavagerOfTheFells(this);
    }
}

class RavagerOfTheFellsEffect extends OneShotEffect {

    RavagerOfTheFellsEffect() {
        super(Outcome.Damage);
        staticText = "it deals 2 damage to target opponent or planeswalker and 2 damage " +
                "to up to one target creature that player or that planeswalker's controller controls.";
    }

    private RavagerOfTheFellsEffect(final RavagerOfTheFellsEffect effect) {
        super(effect);
    }

    @Override
    public RavagerOfTheFellsEffect copy() {
        return new RavagerOfTheFellsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.damagePlayerOrPlaneswalker(source.getTargets().get(0).getFirstTarget(), 2, source.getSourceId(), source, game, false, true);
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.damage(2, source.getSourceId(), source, game, false, true);
        }
        return true;
    }

}

class RavagerOfTheFellsTarget extends TargetPermanent {

    RavagerOfTheFellsTarget() {
        super(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false);
    }

    private RavagerOfTheFellsTarget(final RavagerOfTheFellsTarget target) {
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
