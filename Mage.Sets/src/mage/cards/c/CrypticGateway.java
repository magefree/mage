package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

/**
 * @author awjackson
 */
public final class CrypticGateway extends CardImpl {

    public CrypticGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Tap two untapped creatures you control: You may put a creature card from your hand that shares a creature type with each creature tapped this way onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(new CrypticGatewayEffect(), new TapTargetCost(
                new TargetControlledPermanent(2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES)
        )));
    }

    private CrypticGateway(final CrypticGateway card) {
        super(card);
    }

    @Override
    public CrypticGateway copy() {
        return new CrypticGateway(this);
    }
}

class CrypticGatewayEffect extends OneShotEffect {

    public CrypticGatewayEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put a creature card from your hand that shares a creature type with each creature tapped this way onto the battlefield";
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
        List<Permanent> tapped = (List<Permanent>) getValue("tappedPermanents");
        if (tapped == null || tapped.isEmpty()) {
            return false;
        }
        FilterCreatureCard filter = new FilterCreatureCard("creature card that shares a creature type with "
                + CardUtil.concatWithAnd(tapped.stream().map(MageObject::getName).collect(Collectors.toList()))
        );
        for (Permanent perm : tapped) {
            filter.add(new SharesCreatureTypePredicate(perm));
        }
        return new PutCardFromHandOntoBattlefieldEffect(filter).apply(game, source);
    }
}
