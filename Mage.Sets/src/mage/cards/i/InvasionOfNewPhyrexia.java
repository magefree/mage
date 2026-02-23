package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.command.emblems.TeferiAkosaOfZhalfirEmblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KnightWhiteBlueToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfNewPhyrexia extends TransformingDoubleFacedCard {

    public InvasionOfNewPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{X}{W}{U}",
                "Teferi Akosa of Zhalfir",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.TEFERI}, "WU"
        );

        // Invasion of New Phyrexia
        this.getLeftHalfCard().setStartingDefense(6);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of New Phyrexia enters the battlefield, create X 2/2 white and blue Knight creature tokens with vigilance.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightWhiteBlueToken(), GetXValue.instance)));

        // Teferi Akosa of Zhalfir
        this.getRightHalfCard().setStartingLoyalty(4);

        // +1: Draw two cards. Then discard two cards unless you discard a creature card.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(2), 1);
        ability.addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2),
                new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A))
                        .setText("discard a creature card instead of discarding two cards")
        ).setText("Then discard two cards unless you discard a creature card"));
        this.getRightHalfCard().addAbility(ability);

        // -2: You get an emblem with "Knights you control get +1/+0 and have ward {1}."
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new GetEmblemEffect(new TeferiAkosaOfZhalfirEmblem()), -2));

        // -3: Tap any number of untapped creatures you control. When you do, shuffle target nonland permanent an opponent controls with mana value X or less into its owner's library, where X is the number of creatures tapped this way.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new TeferiAkosaOfZhalfirEffect(), -3));
    }

    private InvasionOfNewPhyrexia(final InvasionOfNewPhyrexia card) {
        super(card);
    }

    @Override
    public InvasionOfNewPhyrexia copy() {
        return new InvasionOfNewPhyrexia(this);
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
