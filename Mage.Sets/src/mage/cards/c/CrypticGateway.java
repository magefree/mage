package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class CrypticGateway extends CardImpl {

    public CrypticGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Tap two untapped creatures you control: You may put a creature card from your hand that shares a creature type with each creature tapped this way onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(new CrypticGatewayEffect(), new CrypticGatewayCost()));
    }

    private CrypticGateway(final CrypticGateway card) {
        super(card);
    }

    @Override
    public CrypticGateway copy() {
        return new CrypticGateway(this);
    }
}

class CrypticGatewayCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    private final TargetControlledPermanent target = new TargetControlledPermanent(2, filter);
    private CrypticGatewayPredicate predicate;

    public CrypticGatewayCost() {
        this.text = "Tap two untapped creatures you control";
    }

    public CrypticGatewayCost(final CrypticGatewayCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        int numTargets = 0;
        Set<Permanent> permanents = new HashSet<>();
        while (numTargets < 2 && target.choose(Outcome.Tap, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.tap(source, game);
                if (paid) {
                    numTargets++;
                    target.clearChosen();
                    permanents.add(permanent);
                }
            }
        }
        if (paid) {
            this.predicate = new CrypticGatewayPredicate(permanents);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(controllerId, source, game);
    }

    public CrypticGatewayPredicate getPredicate() {
        return predicate;
    }

    @Override
    public CrypticGatewayCost copy() {
        return new CrypticGatewayCost(this);
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
                Predicate predicate = ((CrypticGatewayCost) cost).getPredicate();
                filter.add(predicate);
                return new PutCardFromHandOntoBattlefieldEffect(filter).apply(game, source);
            }
        }
        return false;
    }
}

class CrypticGatewayPredicate implements Predicate<Card> {

    private final Set<Permanent> permanents = new HashSet<>();

    CrypticGatewayPredicate(Set<Permanent> permanents) {
        this.permanents.addAll(permanents);
    }

    @Override
    public boolean apply(Card input, Game game) {
        for (Permanent permanent : permanents) {
            if (!permanent.shareCreatureTypes(game, input)) {
                return false;
            }
        }
        return true;
    }
}
