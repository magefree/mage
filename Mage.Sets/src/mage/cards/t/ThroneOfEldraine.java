package mage.cards.t;

import mage.ConditionalMana;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThroneOfEldraine extends CardImpl {

    public ThroneOfEldraine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);

        // As Throne of Eldraine enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // {T}: Add four mana of the chosen color. Spend this mana only to cast monocolored spells of that color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new ThroneOfEldraineManaEffect(), new TapSourceCost()));

        // {3}, {T}: Draw two cards. Spend only mana of the chosen color to activate this ability.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(2),
                new GenericManaCost(3),
                ThroneOfEldraineChosenColorCondition.instance, // disable the ability if no color was chosen or cost is actually {0}.
                "{3}, {T}: Draw two cards. Spend only mana of the chosen color to activate this ability."
        );
        ability.addCost(new TapSourceCost());
        ability.setCostAdjuster(ThroneOfEldraineAdjuster.instance);
        this.addAbility(ability);
    }

    private ThroneOfEldraine(final ThroneOfEldraine card) {
        super(card);
    }

    @Override
    public ThroneOfEldraine copy() {
        return new ThroneOfEldraine(this);
    }
}

class ThroneOfEldraineManaEffect extends ManaEffect {

    private static final ThroneOfEldraineManaBuilder buildWhite = new ThroneOfEldraineManaBuilder(ThroneOfEldraineCondition.white);
    private static final ThroneOfEldraineManaBuilder buildBlue = new ThroneOfEldraineManaBuilder(ThroneOfEldraineCondition.blue);
    private static final ThroneOfEldraineManaBuilder buildBlack = new ThroneOfEldraineManaBuilder(ThroneOfEldraineCondition.black);
    private static final ThroneOfEldraineManaBuilder buildRed = new ThroneOfEldraineManaBuilder(ThroneOfEldraineCondition.red);
    private static final ThroneOfEldraineManaBuilder buildGreen = new ThroneOfEldraineManaBuilder(ThroneOfEldraineCondition.green);

    private ObjectColor chosenColorInfo = null;

    ThroneOfEldraineManaEffect() {
        super();
        staticText = "Add four mana of the chosen color. Spend this mana only to cast monocolored spells of that color";
    }

    private ThroneOfEldraineManaEffect(final ThroneOfEldraineManaEffect effect) {
        super(effect);
        chosenColorInfo = effect.chosenColorInfo;
    }

    @Override
    public ThroneOfEldraineManaEffect copy() {
        return new ThroneOfEldraineManaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            if (color != null) {
                this.chosenColorInfo = color;
                if (color.isWhite()) {
                    return buildWhite.setMana(Mana.WhiteMana(4), source, game).build();
                } else if (color.isBlue()) {
                    return buildBlue.setMana(Mana.BlueMana(4), source, game).build();
                } else if (color.isBlack()) {
                    return buildBlack.setMana(Mana.BlackMana(4), source, game).build();
                } else if (color.isRed()) {
                    return buildRed.setMana(Mana.RedMana(4), source, game).build();
                } else if (color.isGreen()) {
                    return buildGreen.setMana(Mana.GreenMana(4), source, game).build();
                }
            }
        }
        return new Mana();
    }
}

class ThroneOfEldraineManaBuilder extends ConditionalManaBuilder {

    private final ThroneOfEldraineCondition condition;

    ThroneOfEldraineManaBuilder(ThroneOfEldraineCondition condition) {
        this.condition = condition;
    }

    @Override
    public ConditionalMana build(Object... options) {
        return new ThroneOfEldraineConditionalMana(this.mana, this.condition);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast " + condition.toString();
    }

}

class ThroneOfEldraineConditionalMana extends ConditionalMana {
    ThroneOfEldraineConditionalMana(Mana mana, ThroneOfEldraineCondition condition) {
        super(mana);
        staticText = "Spend this mana only to cast " + condition.toString();
        addCondition(condition);
    }
}

enum ThroneOfEldraineCondition implements Condition {
    white(ObjectColor.WHITE, "monocolored white spells"),
    blue(ObjectColor.BLUE, "monocolored blue spells"),
    black(ObjectColor.BLACK, "monocolored black spells"),
    red(ObjectColor.RED, "monocolored red spells"),
    green(ObjectColor.GREEN, "monocolored green spells");

    private final ObjectColor color;
    private final String text;

    ThroneOfEldraineCondition(ObjectColor color, String text) {
        this.color = color;
        this.text = text;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return source instanceof SpellAbility
                && this.color.equals(((SpellAbility) source).getCharacteristics(game).getColor(game));
    }

    @Override
    public String toString() {
        return this.text;
    }
}

enum ThroneOfEldraineAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        ObjectColor color = (ObjectColor) game.getState().getValue(ability.getSourceId() + "_color");
        if (color == null) {
            return;
        }

        int amountMana = ability.getManaCostsToPay().getMana().count();
        if (color.isWhite()) {
            ability.clearManaCostsToPay();
            for (int i = 0; i < amountMana; ++i) {
                ability.addManaCostsToPay(new ColoredManaCost(ColoredManaSymbol.W));
            }
        } else if (color.isBlue()) {
            ability.clearManaCostsToPay();
            for (int i = 0; i < amountMana; ++i) {
                ability.addManaCostsToPay(new ColoredManaCost(ColoredManaSymbol.U));
            }
        } else if (color.isBlack()) {
            ability.clearManaCostsToPay();
            for (int i = 0; i < amountMana; ++i) {
                ability.addManaCostsToPay(new ColoredManaCost(ColoredManaSymbol.B));
            }
        } else if (color.isGreen()) {
            ability.clearManaCostsToPay();
            for (int i = 0; i < amountMana; ++i) {
                ability.addManaCostsToPay(new ColoredManaCost(ColoredManaSymbol.G));
            }
        } else if (color.isRed()) {
            ability.clearManaCostsToPay();
            for (int i = 0; i < amountMana; ++i) {
                ability.addManaCostsToPay(new ColoredManaCost(ColoredManaSymbol.R));
            }
        }
    }
}

enum ThroneOfEldraineChosenColorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        return color != null || source.getManaCostsToPay().getMana().count() == 0;
    }
}