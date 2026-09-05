package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class MeatsqueakHoardLord extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SQUIRREL, "Squirrels");

    public MeatsqueakHoardLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature dies, create a Food token. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new CreateTokenEffect(new FoodToken()), false
        ).setTriggersLimitEachTurn(1));

        // For every seven Foods you control, Squirrels you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            MeatSqueakDynamicValue.instance, MeatSqueakDynamicValue.instance,
            Duration.WhileOnBattlefield,
            filter,
            false
        ).setText("for every seven Foods you control, Squirrels you control get +3/+3")
        ).addHint(MeatSqueakHint.instance));
    }

    private MeatsqueakHoardLord(final MeatsqueakHoardLord card) {
        super(card);
    }

    @Override
    public MeatsqueakHoardLord copy() {
        return new MeatsqueakHoardLord(this);
    }
}

enum MeatSqueakDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int foodCount = game.getBattlefield().count(
            new FilterPermanent(SubType.FOOD, "Foods"),
            sourceAbility.getControllerId(),
            sourceAbility, game
        );
        return (foodCount / 7) * 3;
    }

    @Override
    public MeatSqueakDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "for every seven Foods you control";
    }
}

enum MeatSqueakHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        int foodCount = game.getBattlefield().count(
            new FilterPermanent(SubType.FOOD, "Foods"),
            ability.getControllerId(),
            ability, game
        );
        int boost = (foodCount / 7) * 3;
        return "Foods you control: " + foodCount + " (Squirrels get +" + boost + "/+" + boost + ")";
    }

    @Override
    public MeatSqueakHint copy() {
        return instance;
    }
}
