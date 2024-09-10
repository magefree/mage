package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.command.emblems.TeferiAkosaOfZhalfirEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferiAkosaOfZhalfir extends CardImpl {

    public TeferiAkosaOfZhalfir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);
        this.setStartingLoyalty(4);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // +1: Draw two cards. Then discard two cards unless you discard a creature card.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(2), 1);
        ability.addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2),
                new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A))
                        .setText("discard a creature card instead of discarding two cards")
        ).setText("Then discard two cards unless you discard a creature card"));
        this.addAbility(ability);

        // -2: You get an emblem with "Knights you control get +1/+0 and have ward {1}."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TeferiAkosaOfZhalfirEmblem()), -2));

        // -3: Tap any number of untapped creatures you control. When you do, shuffle target nonland permanent an opponent controls with mana value X or less into its owner's library, where X is the number of creatures tapped this way.
        this.addAbility(new LoyaltyAbility(new TeferiAkosaOfZhalfirEffect(), -3));
    }

    private TeferiAkosaOfZhalfir(final TeferiAkosaOfZhalfir card) {
        super(card);
    }

    @Override
    public TeferiAkosaOfZhalfir copy() {
        return new TeferiAkosaOfZhalfir(this);
    }
}

class TeferiAkosaOfZhalfirEffect extends OneShotEffect {

    TeferiAkosaOfZhalfirEffect() {
        super(Outcome.Benefit);
        staticText = "tap any number of untapped creatures you control. When you do, " +
                "shuffle target nonland permanent an opponent controls with mana value X or less " +
                "into its owner's library, where X is the number of creatures tapped this way";
    }

    private TeferiAkosaOfZhalfirEffect(final TeferiAkosaOfZhalfirEffect effect) {
        super(effect);
    }

    @Override
    public TeferiAkosaOfZhalfirEffect copy() {
        return new TeferiAkosaOfZhalfirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES, true
        );
        player.choose(Outcome.Tap, target, source, game);
        int count = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.tap(source, game)) {
                count++;
            }
        }
        FilterPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent controls with mana value " + count + " or less");
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, count + 1));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ShuffleIntoLibraryTargetEffect()
                .setText("shuffle target nonland permanent an opponent controls with mana value X or less " +
                        "into its owner's library, where X is the number of creatures tapped this way"), false);
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
