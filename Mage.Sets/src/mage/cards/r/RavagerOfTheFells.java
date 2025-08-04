package mage.cards.r;

import mage.MageInt;
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
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;
import mage.target.targetpointer.EachTargetPointer;

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
        this.setTargetPointer(new EachTargetPointer());
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
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            game.damagePlayerOrPermanent(
                    targetId, 2, source.getSourceId(), source,
                    game, false, true
            );
        }
        return true;
    }

}

class RavagerOfTheFellsTarget extends TargetPermanent {

    RavagerOfTheFellsTarget() {
        super(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    private RavagerOfTheFellsTarget(final RavagerOfTheFellsTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        Player needPlayer = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (needPlayer == null) {
            // playable or not selected - use any
        } else {
            // filter by controller
            possibleTargets.removeIf(id -> {
                Permanent permanent = game.getPermanent(id);
                return permanent == null
                        || permanent.getId().equals(source.getFirstTarget())
                        || !permanent.isControlledBy(needPlayer.getId());
            });
        }

        return possibleTargets;
    }

    @Override
    public RavagerOfTheFellsTarget copy() {
        return new RavagerOfTheFellsTarget(this);
    }
}
