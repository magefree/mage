package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SunbirdEffigy extends CardImpl {

    public SunbirdEffigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Sunbird Effigy's power and toughness are each equal to the number of colors among the exiled cards used to craft it.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(SunbirdEffigyValue.instance)
        ).addHint(SunbirdEffigyHint.instance));

        // {T}: For each color among the exiled cards used to craft Sunbird Effigy, add one mana of that color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new SunbirdEffigyEffect(), new TapSourceCost()));
    }

    private SunbirdEffigy(final SunbirdEffigy card) {
        super(card);
    }

    @Override
    public SunbirdEffigy copy() {
        return new SunbirdEffigy(this);
    }
}

enum SunbirdEffigyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(getColor(game, sourceAbility))
                .filter(Objects::nonNull)
                .map(ObjectColor::getColorCount)
                .orElse(0);
    }

    @Override
    public SunbirdEffigyValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "colors among the exiled cards used to craft it";
    }

    @Override
    public String toString() {
        return "1";
    }

    static ObjectColor getColor(Game game, Ability source) {
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(
                        game,
                        source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId()) - 2
                ));
        return exileZone == null ? null : exileZone
                .getCards(game)
                .stream()
                .map(card -> card.getColor(game))
                .reduce(new ObjectColor(), (c1, c2) -> c1.union(c2));
    }
}

enum SunbirdEffigyHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        ObjectColor color = SunbirdEffigyValue.getColor(game, ability);
        if (color == null) {
            return null;
        }
        if (color.isColorless()) {
            return "No colors among exiled cards.";
        }
        return color
                .getColors()
                .stream()
                .map(ObjectColor::getDescription)
                .collect(Collectors.joining(", ", "Colors among exiled cards: ", ""));
    }

    @Override
    public Hint copy() {
        return this;
    }
}

class SunbirdEffigyEffect extends ManaEffect {

    public SunbirdEffigyEffect() {
        super();
        staticText = "for each color among the exiled cards used to craft {this}, add one mana of that color";
    }

    private SunbirdEffigyEffect(final SunbirdEffigyEffect effect) {
        super(effect);
    }

    @Override
    public SunbirdEffigyEffect copy() {
        return new SunbirdEffigyEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(game, source, -2));
        if (exileZone == null) {
            return mana;
        }
        ObjectColor color = exileZone
                .getCards(game)
                .stream()
                .map(card -> card.getColor(game))
                .reduce(new ObjectColor(), (c1, c2) -> c1.union(c2));
        if (color.isWhite()) {
            mana.increaseWhite();
        }
        if (color.isBlue()) {
            mana.increaseBlue();
        }
        if (color.isBlack()) {
            mana.increaseBlack();
        }
        if (color.isRed()) {
            mana.increaseRed();
        }
        if (color.isGreen()) {
            mana.increaseGreen();
        }
        return mana;
    }
}
