package mage.cards.e;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class EarthenAlly extends CardImpl {

    public EarthenAlly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // This creature gets +1/+0 for each color among Allies you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                EarthenAllyValue.instance, StaticValue.get(0), Duration.WhileOnBattlefield
        )).addHint(EarthenAllyHint.instance));

        // {2}{W}{U}{B}{R}{G}: Earthbend 5.
        Ability ability = new SimpleActivatedAbility(
                new EarthbendTargetEffect(5), new ManaCostsImpl<>("{2}{W}{U}{B}{R}{G}")
        );
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private EarthenAlly(final EarthenAlly card) {
        super(card);
    }

    @Override
    public EarthenAlly copy() {
        return new EarthenAlly(this);
    }
}

enum EarthenAllyValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ALLY);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return getColors(game, sourceAbility).getColorCount();
    }

    static ObjectColor getColors(Game game, Ability sourceAbility) {
        return game.getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(permanent -> permanent.getColor(game))
                .collect(Collectors.reducing((c1, c2) -> c1.union(c2)))
                .orElseGet(ObjectColor::new);
    }

    @Override
    public EarthenAllyValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "color among Allies you control";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum EarthenAllyHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        ObjectColor color = EarthenAllyValue.getColors(game, ability);
        return "Colors among Allies you control: " + color.getColorCount() +
                (color.getColorCount() > 0
                        ? color
                        .getColors()
                        .stream()
                        .map(ObjectColor::getDescription)
                        .collect(Collectors.joining(", ", " (", ")"))
                        : "");
    }

    @Override
    public Hint copy() {
        return this;
    }
}
