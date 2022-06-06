
package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class GauntletsOfChaos extends CardImpl {

    public GauntletsOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {5}, Sacrifice Gauntlets of Chaos: Exchange control of target artifact, creature, or land you control and target permanent an opponent controls that shares one of those types with it. If those permanents are exchanged this way, destroy all Auras attached to them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExchangeControlTargetEffect(Duration.EndOfGame,
                "exchange control of target artifact, creature, or land you control and target permanent an opponent controls that shares one of those types with it."
                        + " If those permanents are exchanged this way, destroy all Auras attached to them", false, true, true),
                new ManaCostsImpl<>("{5}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new GauntletsOfChaosFirstTarget());
        ability.addTarget(new GauntletsOfChaosSecondTarget());
        this.addAbility(ability);
    }

    private GauntletsOfChaos(final GauntletsOfChaos card) {
        super(card);
    }

    @Override
    public GauntletsOfChaos copy() {
        return new GauntletsOfChaos(this);
    }
}

class GauntletsOfChaosFirstTarget extends TargetControlledPermanent {

    public GauntletsOfChaosFirstTarget() {
        super();
        this.filter = this.filter.copy();
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
        setTargetName("artifact, creature, or land you control");
    }

    public GauntletsOfChaosFirstTarget(final GauntletsOfChaosFirstTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Set<CardType> cardTypes = getOpponentPermanentCardTypes(source.getSourceId(), controllerId, game);
            Permanent permanent = game.getPermanent(id);
            for (CardType type : permanent.getCardType(game)) {
                if (cardTypes.contains(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        // get all cardtypes from opponents permanents 
        Set<CardType> cardTypes = getOpponentPermanentCardTypes(source.getSourceId(), sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        if (targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    for (CardType type : permanent.getCardType(game)) {
                        if (cardTypes.contains(type)) {
                            possibleTargets.add(permanent.getId());
                            break;
                        }
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public GauntletsOfChaosFirstTarget copy() {
        return new GauntletsOfChaosFirstTarget(this);
    }

    private EnumSet<CardType> getOpponentPermanentCardTypes(UUID sourceId, UUID sourceControllerId, Game game) {
        Player controller = game.getPlayer(sourceControllerId);
        EnumSet<CardType> cardTypes = EnumSet.noneOf(CardType.class);
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
                if (controller.hasOpponent(permanent.getControllerId(), game)) {
                    cardTypes.addAll(permanent.getCardType(game));
                }
            }
        }
        return cardTypes;
    }
}


class GauntletsOfChaosSecondTarget extends TargetPermanent {

    private Permanent firstTarget = null;

    public GauntletsOfChaosSecondTarget() {
        super();
        this.filter = this.filter.copy();
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        setTargetName("permanent an opponent controls that shares one of those types with it");
    }

    public GauntletsOfChaosSecondTarget(final GauntletsOfChaosSecondTarget target) {
        super(target);
        this.firstTarget = target.firstTarget;
    }


    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (super.canTarget(id, source, game)) {
            Permanent target1 = game.getPermanent(source.getFirstTarget());
            Permanent opponentPermanent = game.getPermanent(id);
            if (target1 != null && opponentPermanent != null) {
                return target1.shareTypes(opponentPermanent, game);
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        if (firstTarget != null) {
            MageObject targetSource = game.getObject(source);
            if (targetSource != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                    if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                        if (permanent.shareTypes(firstTarget, game)) {
                            possibleTargets.add(permanent.getId());
                        }
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        firstTarget = game.getPermanent(source.getFirstTarget());
        return super.chooseTarget(Outcome.Damage, playerId, source, game);
    }

    @Override
    public GauntletsOfChaosSecondTarget copy() {
        return new GauntletsOfChaosSecondTarget(this);
    }
}
