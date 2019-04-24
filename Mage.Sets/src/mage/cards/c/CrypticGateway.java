
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class CrypticGateway extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(Predicates.not(TappedPredicate.instance));
    }

    TargetControlledPermanent target;

    public CrypticGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Tap two untapped creatures you control: You may put a creature card from your hand that shares a creature type with each creature tapped this way onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrypticGatewayEffect(), new CrypticGatewayCost(new TargetControlledPermanent(filter))));
    }

    public CrypticGateway(final CrypticGateway card) {
        super(card);
    }

    @Override
    public CrypticGateway copy() {
        return new CrypticGateway(this);
    }
}

class CrypticGatewayCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");
    private UUID targetCreatureId = null;
    private UUID targetCreatureId2 = null;

    TargetControlledPermanent target;

    static {
        filter.add(Predicates.not(TappedPredicate.instance));
    }

    public CrypticGatewayCost(TargetControlledPermanent target) {
        this.target = target;
        this.text = "Tap two untapped creatures you control";
    }

    public CrypticGatewayCost(final CrypticGatewayCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        int numTargets = 0;
        while (numTargets < 2 && target.choose(Outcome.Tap, controllerId, sourceId, game)) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.tap(game);
                if (paid) {
                    numTargets++;
                    target.clearChosen();
                }
                for (Effect effect : ability.getEffects()) {
                    if (targetCreatureId == null) {
                        targetCreatureId = permanent.getId();
                    } else if (targetCreatureId2 == null) {
                        targetCreatureId2 = permanent.getId();
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(controllerId, game);
    }

    @Override
    public CrypticGatewayCost copy() {
        return new CrypticGatewayCost(this);
    }

    public UUID getTargetCreatureId() {
        return targetCreatureId;
    }

    public UUID getTargetCreatureId2() {
        return targetCreatureId2;
    }
}

class CrypticGatewayEffect extends OneShotEffect {

    public CrypticGatewayEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a creature card from your hand that shares a creature type with each creature tapped this way onto the battlefield";
    }

    public CrypticGatewayEffect(final CrypticGatewayEffect effect) {
        super(effect);
    }

    @Override
    public CrypticGatewayEffect copy() {
        return new CrypticGatewayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getCosts() == null) {
            return false;
        }

        FilterCard filter = new FilterCreatureCard("creature card from your hand that shares a creature type with each creature tapped this way");

        for (Cost cost : source.getCosts()) {
            if (cost instanceof CrypticGatewayCost) {
                UUID id = ((CrypticGatewayCost) cost).getTargetCreatureId();
                UUID id2 = ((CrypticGatewayCost) cost).getTargetCreatureId2();
                Permanent creature = game.getPermanentOrLKIBattlefield(id);
                Permanent creature2 = game.getPermanentOrLKIBattlefield(id2);

                if (creature == null || creature2 == null) {
                    return false;
                }

                boolean commonSubType = false;
                boolean changeling = false;
                boolean changeling2 = false;
                if (creature.getAbilities().containsKey(ChangelingAbility.getInstance().getId()) || creature.isAllCreatureTypes()) {
                    changeling = true;
                }
                if (creature2.getAbilities().containsKey(ChangelingAbility.getInstance().getId()) || creature2.isAllCreatureTypes()) {
                    changeling2 = true;
                }

                List<SubtypePredicate> subtypes = new ArrayList<>();

                for (SubType subtype : creature.getSubtype(game)) {
                    if (creature2.hasSubtype(subtype, game) || changeling2) {
                        subtypes.add(new SubtypePredicate(subtype));
                        commonSubType = true;
                    }
                }

                for (SubType subtype : creature2.getSubtype(game)) {
                    if (creature.hasSubtype(subtype, game) || changeling) {
                        subtypes.add(new SubtypePredicate(subtype));
                        commonSubType = true;
                    }
                }

                if (changeling && changeling2) {
                    filter = new FilterCreatureCard("creature card from your hand that shares a creature type with each creature tapped this way");
                } else if (commonSubType) {
                    filter.add(Predicates.or(subtypes));
                }

                if (commonSubType) {
                    PutCardFromHandOntoBattlefieldEffect putIntoPlay = new PutCardFromHandOntoBattlefieldEffect(filter);
                    putIntoPlay.apply(game, source);
                }
            }
        }

        return false;
    }
}
