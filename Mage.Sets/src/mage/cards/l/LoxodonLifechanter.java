package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoxodonLifechanter extends CardImpl {

    public LoxodonLifechanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Loxodon Lifechanter enters the battlefield, you may have your life total become the total toughness of creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SetPlayerLifeSourceEffect(
                LoxodonLifechanterValue.instance
        ).setText("have your life total become the total toughness of creatures you control"), true));

        // {5}{W}: Loxodon Lifechanter gets +X/+X until end of turn, where X is your life total.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                LoxodonLifechanterValue2.instance,
                LoxodonLifechanterValue2.instance,
                Duration.EndOfTurn, true
        ), new ManaCostsImpl<>("{5}{W}")));
    }

    private LoxodonLifechanter(final LoxodonLifechanter card) {
        super(card);
    }

    @Override
    public LoxodonLifechanter copy() {
        return new LoxodonLifechanter(this);
    }
}

enum LoxodonLifechanterValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                sourceAbility.getControllerId(), game
        ).stream().mapToInt(permanent -> (permanent.getToughness().getValue())).sum();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return null;
    }
}

enum LoxodonLifechanterValue2 implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return player.getLife();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "your life total";
    }

    @Override
    public String toString() {
        return "X";
    }
}