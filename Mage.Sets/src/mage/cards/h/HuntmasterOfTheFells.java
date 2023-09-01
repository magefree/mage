package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
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
public final class HuntmasterOfTheFells extends TransformingDoubleFacedCard {

    public HuntmasterOfTheFells(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{R}{G}",
                "Ravager of the Fells",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "GR"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(4, 4);

        // Whenever this creature enters the battlefield or transforms into Huntmaster of the Fells, create a 2/2 green Wolf creature token and you gain 2 life.
        Ability ability = new TransformsOrEntersTriggeredAbility(new CreateTokenEffect(new WolfToken()), false);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Huntmaster of the Fells.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever this creature transforms into Ravager of the Fells, it deals 2 damage to target opponent and 2 damage to up to one target creature that player controls.
        ability = new TransformIntoSourceTriggeredAbility(
                new RavagerOfTheFellsEffect(), false, true
        );
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        ability.addTarget(new RavagerOfTheFellsTarget());
        this.getRightHalfCard().addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ravager of the Fells.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private HuntmasterOfTheFells(final HuntmasterOfTheFells card) {
        super(card);
    }

    @Override
    public HuntmasterOfTheFells copy() {
        return new HuntmasterOfTheFells(this);
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
        game.damagePlayerOrPermanent(source.getTargets().get(0).getFirstTarget(), 2, source.getSourceId(), source, game, false, true);
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
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(source);

        for (StackObject item : game.getState().getStack()) {
            if (item.getId().equals(source.getSourceId())) {
                object = item;
            }
            if (item.getSourceId().equals(source.getSourceId())) {
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
