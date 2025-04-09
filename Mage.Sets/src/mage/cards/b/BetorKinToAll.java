package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author androosss
 */
public final class BetorKinToAll extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Total toughness of creatures you control", ControlledCreaturesToughnessValue.instance);

    public BetorKinToAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{2}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, if creatures you control have total
        // toughness 10 or greater, draw a card. Then if creatures you control have
        // total toughness 20 or greater, untap each creature you control. Then if
        // creatures you control have total toughness 40 or greater, each opponent loses
        // half their life, rounded up.
        Ability betorAbility = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(BetorKinToAllCondition.instance).addHint(hint);
        betorAbility.addEffect(new BetorKinToAllEffect());
        this.addAbility(betorAbility);
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
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ControlledCreaturesToughnessValue.instance.calculate(game, source, null) >= 10;
    }

}

class BetorKinToAllEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    BetorKinToAllEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if creatures you control have total toughness 20 or greater, untap each creature you control. Then if creatures you control have total toughness 40 or greater, each opponent loses half their life, rounded up.";
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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int sumToughness = ControlledCreaturesToughnessValue.instance.calculate(game, source, null);

        if (sumToughness < 20) {
            return true;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(),
                game)) {
            permanent.untap(game);
        }

        if (sumToughness < 40) {
            return true;
        }

        for (UUID playerId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            int amount = (int) Math.ceil(opponent.getLife() / 2f);
            if (amount > 0) {
                opponent.loseLife(amount, game, source, false);
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