
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class AnyaMercilessAngel extends CardImpl {

    public AnyaMercilessAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Anya, Merciless Angel gets +3/+3 for each opponent whose life total is less than half their starting life total.
        DynamicValue dValue = new MultipliedValue(new AnyaMercilessAngelDynamicValue(), 3);
        Effect effect = new BoostSourceEffect(dValue, dValue, Duration.WhileOnBattlefield);
        effect.setText("{this} gets +3/+3 for each opponent whose life total is less than half their starting life total");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(dValue, dValue, Duration.WhileOnBattlefield)));
        
        // As long as an opponent's life total is less than half their starting life total, Anya has indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                        AnyaMercilessAngelCondition.instance,
                        "As long as an opponent's life total is less than half their starting life total, {this} has indestructible")));
    }

    public AnyaMercilessAngel(final AnyaMercilessAngel card) {
        super(card);
    }

    @Override
    public AnyaMercilessAngel copy() {
        return new AnyaMercilessAngel(this);
    }
}

class AnyaMercilessAngelDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int opponentCount = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            int startingLifeTotal = game.getLife();
            for (UUID opponentId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null && opponent.getLife() < startingLifeTotal / 2) {
                    opponentCount++;
                }
            }
        }
        return opponentCount;
    }

    @Override
    public AnyaMercilessAngelDynamicValue copy() {
        return new AnyaMercilessAngelDynamicValue();
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
        return new AnyaMercilessAngelDynamicValue().calculate(game, source, null) > 0;
    }

    @Override
    public String toString() {
        return "an opponent's life total is less than half their starting life total";
    }
}
