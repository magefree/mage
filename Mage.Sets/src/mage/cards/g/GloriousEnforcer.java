package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class GloriousEnforcer extends CardImpl {

    public GloriousEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of each combat, if you have more life than an opponent, Glorious Enforcer gains double strike until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn),
                        TargetController.ANY, false
                ),
                GloriousEnforcerCondition.instance,
                "At the beginning of each combat, if you have more life than an opponent, {this} gains double strike until end of turn."
        ));
    }

    private GloriousEnforcer(final GloriousEnforcer card) {
        super(card);
    }

    @Override
    public GloriousEnforcer copy() {
        return new GloriousEnforcer(this);
    }
}

enum GloriousEnforcerCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null && controller.getLife() > opponent.getLife()) {
                    return true;
                }
            }
        }
        return false;
    }
}
