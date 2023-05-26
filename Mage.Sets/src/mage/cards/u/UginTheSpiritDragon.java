
package mage.cards.u;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class UginTheSpiritDragon extends CardImpl {

    public UginTheSpiritDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{8}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UGIN);

        this.setStartingLoyalty(7);

        // +2: Ugin, the Spirit Dragon deals 3 damage to any target.
        LoyaltyAbility ability = new LoyaltyAbility(new DamageTargetEffect(3), 2);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // -X: Exile each permanent with converted mana cost X or less that's one or more colors.
        this.addAbility(new LoyaltyAbility(new UginTheSpiritDragonEffect2()));

        // -10: You gain 7 life, draw seven cards, then put up to seven permanent cards from your hand onto the battlefield.
        this.addAbility(new LoyaltyAbility(new UginTheSpiritDragonEffect3(), -10));

    }

    private UginTheSpiritDragon(final UginTheSpiritDragon card) {
        super(card);
    }

    @Override
    public UginTheSpiritDragon copy() {
        return new UginTheSpiritDragon(this);
    }

}

class UginTheSpiritDragonEffect2 extends OneShotEffect {

    public UginTheSpiritDragonEffect2() {
        super(Outcome.Exile);
        this.staticText = "exile each permanent with mana value X or less that's one or more colors";
    }

    public UginTheSpiritDragonEffect2(final UginTheSpiritDragonEffect2 effect) {
        super(effect);
    }

    @Override
    public UginTheSpiritDragonEffect2 copy() {
        return new UginTheSpiritDragonEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int cmc = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                cmc = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }

        FilterPermanent filter = new FilterPermanent("permanent with mana value X or less that's one or more colors");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, cmc + 1));
        filter.add(Predicates.not(ColorlessPredicate.instance));
        Set<Card> permanentsToExile = new HashSet<>();
        permanentsToExile.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
        controller.moveCards(permanentsToExile, Zone.EXILED, source, game);
        return true;
    }
}

class UginTheSpiritDragonEffect3 extends OneShotEffect {

    public UginTheSpiritDragonEffect3() {
        super(Outcome.Benefit);
        this.staticText = "You gain 7 life, draw seven cards, then put up to seven permanent cards from your hand onto the battlefield";
    }

    public UginTheSpiritDragonEffect3(final UginTheSpiritDragonEffect3 effect) {
        super(effect);
    }

    @Override
    public UginTheSpiritDragonEffect3 copy() {
        return new UginTheSpiritDragonEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(7, game, source);
            controller.drawCards(7, source, game);
            TargetCardInHand target = new TargetCardInHand(0, 7, new FilterPermanentCard("permanent cards"));
            if (controller.choose(Outcome.PutCardInPlay, target, source, game)) {
                controller.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
