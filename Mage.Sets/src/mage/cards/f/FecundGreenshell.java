package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class FecundGreenshell extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with toughness greater than its power");

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    public FecundGreenshell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // As long as you control ten or more lands, creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(
                                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                                ComparisonType.OR_GREATER,
                                10),
                        "as long as you control ten or more lands, creatures you control get +2/+2")
        ).addHint(LandsYouControlHint.instance));

        // Whenever Fecund Greenshell or another creature you control with toughness greater than its power enters,
        // look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped. Otherwise, put it into your hand.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new FecundGreenshellEffect(), filter, false, false));
    }

    private FecundGreenshell(final FecundGreenshell card) {
        super(card);
    }

    @Override
    public FecundGreenshell copy() {
        return new FecundGreenshell(this);
    }
}

class FecundGreenshellEffect extends OneShotEffect {

    FecundGreenshellEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. " +
                "If it's a land card, you may put it onto the battlefield tapped. Otherwise, put it into your hand";
    }

    private FecundGreenshellEffect(final FecundGreenshellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top card of library", card, game);
        if (card.isLand(game) && player.chooseUse(
                outcome,
                "Put " + card.getName() + " onto the battlefield tapped?",
                "(otherwise put it into your hand)",
                "To battlefield",
                "To hand",
                source,
                game)) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        } else {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }

    @Override
    public FecundGreenshellEffect copy() {
        return new FecundGreenshellEffect(this);
    }
}
