package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author androosss
 */
public final class BetorKinToAll extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Total toughness of creatures you control", ControlledCreaturesToughnessValue.instance
    );

    public BetorKinToAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, if creatures you control have total toughness 10 or greater, draw a card. Then if creatures you control have total toughness 20 or greater, untap each creature you control. Then if creatures you control have total toughness 40 or greater, each opponent loses half their life, rounded up.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(BetorKinToAllCondition.TEN);
        ability.addEffect(new ConditionalOneShotEffect(
                new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE), BetorKinToAllCondition.TWENTY,
                "Then if creatures you control have total toughness 20 or greater, untap each creature you control"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new BetorKinToAllEffect(), BetorKinToAllCondition.FORTY, "Then if creatures you control " +
                "have total toughness 40 or greater, each opponent loses half their life, rounded up"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private BetorKinToAll(final BetorKinToAll card) {
        super(card);
    }

    @Override
    public BetorKinToAll copy() {
        return new BetorKinToAll(this);
    }
}

enum BetorKinToAllCondition implements Condition {
    TEN(10),
    TWENTY(20),
    FORTY(40);
    private final int amount;

    private BetorKinToAllCondition(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return ControlledCreaturesToughnessValue.instance.calculate(game, source, null) >= amount;
    }

    @Override
    public String toString() {
        return "creatures you control have total toughness " + amount + " or greater";
    }
}

class BetorKinToAllEffect extends OneShotEffect {

    BetorKinToAllEffect() {
        super(Outcome.Benefit);
    }

    private BetorKinToAllEffect(final BetorKinToAllEffect effect) {
        super(effect);
    }

    @Override
    public BetorKinToAllEffect copy() {
        return new BetorKinToAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null) {
                opponent.loseLife(opponent.getLife() / 2 + opponent.getLife() % 2, game, source, false);
            }
        }
        return true;
    }
}

enum ControlledCreaturesToughnessValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(MageObject::getToughness)
                .mapToInt(MageInt::getValue)
                .sum();
    }

    @Override
    public ControlledCreaturesToughnessValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "total toughness of creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
