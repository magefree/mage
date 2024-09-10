package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class AnyaMercilessAngel extends CardImpl {

    public AnyaMercilessAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Anya, Merciless Angel gets +3/+3 for each opponent whose life total is less than half their starting life total.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                AnyaMercilessAngelDynamicValue.instance,
                AnyaMercilessAngelDynamicValue.instance,
                Duration.WhileOnBattlefield
        ).setText("{this} gets +3/+3 for each opponent whose life total is less than half their starting life total")));

        // As long as an opponent's life total is less than half their starting life total, Anya has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield
                ), AnyaMercilessAngelCondition.instance, "As long as an opponent's life total " +
                "is less than half their starting life total, {this} has indestructible"
        )));
    }

    private AnyaMercilessAngel(final AnyaMercilessAngel card) {
        super(card);
    }

    @Override
    public AnyaMercilessAngel copy() {
        return new AnyaMercilessAngel(this);
    }
}

enum AnyaMercilessAngelDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int opponentCount = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 3 * opponentCount;
        }
        int startingLifeTotal = game.getStartingLife();
        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null 
                    && opponent.isInGame()
                    && opponent.getLife() < startingLifeTotal / 2) {
                opponentCount++;
            }
        }
        return 3 * opponentCount;
    }

    @Override
    public AnyaMercilessAngelDynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "number of opponents whose life total is less than half their starting life total";
    }

    @Override
    public String toString() {
        return "X";
    }
}

enum AnyaMercilessAngelCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return AnyaMercilessAngelDynamicValue.instance.calculate(game, source, null) > 0;
    }

    @Override
    public String toString() {
        return "an opponent's life total is less than half their starting life total";
    }
}
