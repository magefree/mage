package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.condition.common.OpponentHasMoreLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.FishNoAbilityToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class BezaTheBoundingSpring extends CardImpl {

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS);
    private static final Condition condition2 = new OpponentControlsMoreCondition(StaticFilters.FILTER_PERMANENT_CREATURES);
    private static final Hint hint = new ConditionHint(condition);
    private static final Hint hint2 = new ConditionHint(condition2);
    private static final Hint hint3 = new ConditionHint(OpponentHasMoreLifeCondition.instance);
    private static final Hint hint4 = new ConditionHint(BezaOpponentHasMoreCardsInHandThanYouCondition.instance);

    public BezaTheBoundingSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Beza, the Bounding Spring enters, create a Treasure token if an opponent controls more lands than you. You gain 4 life if an opponent has more life than you. Create two 1/1 blue Fish creature tokens if an opponent controls more creatures than you. Draw a card if an opponent has more cards in hand than you.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new TreasureToken()), condition,
                "create a Treasure token if an opponent controls more lands than you"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(4), OpponentHasMoreLifeCondition.instance,
                "you gain 4 life if an opponent has more life than you"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new FishNoAbilityToken(), 2), condition2,
                "Create two 1/1 blue Fish creature tokens if an opponent controls more creatures than you"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                BezaOpponentHasMoreCardsInHandThanYouCondition.instance,
                "draw a card if an opponent has more cards in hand than you"
        ));
        this.addAbility(ability.addHint(hint).addHint(hint2).addHint(hint3).addHint(hint4));
    }

    private BezaTheBoundingSpring(final BezaTheBoundingSpring card) {
        super(card);
    }

    @Override
    public BezaTheBoundingSpring copy() {
        return new BezaTheBoundingSpring(this);
    }
}

//Based on MoreCardsInHandThanOpponentsCondition
enum BezaOpponentHasMoreCardsInHandThanYouCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        int cardsInHand = Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getHand)
                .map(Set::size)
                .orElse(0);
        return game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .map(Player::getHand)
                .mapToInt(Set::size)
                .anyMatch(x -> x > cardsInHand);
    }

    @Override
    public String toString() {
        return "an opponent has more cards in hand than you";
    }

}
