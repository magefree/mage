package mage.cards.s;

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
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
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
public final class SunbirdStandard extends TransformingDoubleFacedCard {

    public SunbirdStandard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}",
                "Sunbird Effigy",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.BIRD, SubType.CONSTRUCT}, ""
        );

        // {T}: Add one mana of any color.
        this.getLeftHalfCard().addAbility(new AnyColorManaAbility());

        // Craft with one or more {5}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{5}", "one or more", "other permanents " +
                "you control and/or cards in your graveyard", 1, Integer.MAX_VALUE
        ));

        // Sunbird Effigy
        this.getRightHalfCard().setPT(0, 0);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Sunbird Effigy's power and toughness are each equal to the number of colors among the exiled cards used to craft it.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(SunbirdEffigyValue.instance)
        ).addHint(SunbirdEffigyHint.instance));

        // {T}: For each color among the exiled cards used to craft Sunbird Effigy, add one mana of that color.
        this.getRightHalfCard().addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new SunbirdEffigyEffect(), new TapSourceCost()));
    }

    private SunbirdStandard(final SunbirdStandard card) {
        super(card);
    }

    @Override
    public SunbirdStandard copy() {
        return new SunbirdStandard(this);
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
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return null;
        }
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(
                        game,
                        sourceCard.getMainCard().getId(),
                        sourceCard.getMainCard().getZoneChangeCounter(game) - 1
                ));
        return exileZone == null ? null : exileZone
                .getCards(game)
                .stream()
                .map(card -> card.getColor(game))
                .reduce(new ObjectColor(), ObjectColor::union);
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

    SunbirdEffigyEffect() {
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
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return mana;
        }
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(game, sourceCard.getMainCard().getId(),
                        sourceCard.getMainCard().getZoneChangeCounter(game) - 1));
        if (exileZone == null) {
            return mana;
        }
        ObjectColor color = exileZone
                .getCards(game)
                .stream()
                .map(card -> card.getColor(game))
                .reduce(new ObjectColor(), ObjectColor::union);
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
