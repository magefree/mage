package mage.cards.f;

import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaeburrowElder extends CardImpl {

    public FaeburrowElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Faeburrow Elder gets +1/+1 for each color among permanents you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                FaeburrowElderValue.instance, FaeburrowElderValue.instance, Duration.WhileOnBattlefield
        )));

        // {T}: For each color among permanents you control, add one mana of that color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new FaeburrowElderManaEffect(), new TapSourceCost()));
    }

    private FaeburrowElder(final FaeburrowElder card) {
        super(card);
    }

    @Override
    public FaeburrowElder copy() {
        return new FaeburrowElder(this);
    }
}

enum FaeburrowElderValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ObjectColor color = new ObjectColor("");
        game.getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .map(permanent -> permanent.getColor(game))
                .forEach(color::addColor);
        return color.getColorCount();
    }

    @Override
    public FaeburrowElderValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "color among permanents you control";
    }
}

class FaeburrowElderManaEffect extends ManaEffect {

    FaeburrowElderManaEffect() {
        super();
        staticText = "For each color among permanents you control, add one mana of that color";
    }

    private FaeburrowElderManaEffect(final FaeburrowElderManaEffect effect) {
        super(effect);
    }

    @Override
    public FaeburrowElderManaEffect copy() {
        return new FaeburrowElderManaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (mana.getBlack() == 0 && permanent.getColor(game).isBlack()) {
                mana.increaseBlack();
            }
            if (mana.getBlue() == 0 && permanent.getColor(game).isBlue()) {
                mana.increaseBlue();
            }
            if (mana.getRed() == 0 && permanent.getColor(game).isRed()) {
                mana.increaseRed();
            }
            if (mana.getGreen() == 0 && permanent.getColor(game).isGreen()) {
                mana.increaseGreen();
            }
            if (mana.getWhite() == 0 && permanent.getColor(game).isWhite()) {
                mana.increaseWhite();
            }
        }
        return mana;
    }
}
