package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MoonveilRegent extends CardImpl {

    public MoonveilRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell, you may discard your hand. If you do, draw a card for each of that spell's colors.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(MoonveilRegentSpellValue.instance)
                        .setText("draw a card for each of that spell's colors"),
                new DiscardHandCost()
        ), StaticFilters.FILTER_SPELL_A, false, true));

        // When Moonveil Regent dies, it deals X damage to any target, where X is the number of colors among permanents you control.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(
                MoonveilRegentColorValue.instance
        ).setText("it deals X damage to any target, where X is the number of colors among permanents you control"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MoonveilRegent(final MoonveilRegent card) {
        super(card);
    }

    @Override
    public MoonveilRegent copy() {
        return new MoonveilRegent(this);
    }
}

enum MoonveilRegentSpellValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = (Spell) effect.getValue("spellCast");
        return spell != null ? spell.getColor(game).getColorCount() : 0;
    }

    @Override
    public MoonveilRegentSpellValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum MoonveilRegentColorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return getColorUnion(game, sourceAbility).getColorCount();
    }

    static ObjectColor getColorUnion(Game game, Ability ability) {
        ObjectColor color = new ObjectColor();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT, ability.getControllerId(), game
        )) {
            color.addColor(permanent.getColor(game));
            if (color.getColorCount() >= 5) {
                break;
            }
        }
        return color;
    }

    @Override
    public MoonveilRegentColorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum MoonveilRegentHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        ObjectColor color = MoonveilRegentColorValue.getColorUnion(game, ability);
        return "Colors among permanents you control: " + color.getColorCount() + (
                color.getColorCount() > 0
                        ? color
                        .getColors()
                        .stream()
                        .map(ObjectColor::getDescription)
                        .collect(Collectors.joining(", ", " (", ")")) : ""
        );
    }

    @Override
    public MoonveilRegentHint copy() {
        return this;
    }
}
