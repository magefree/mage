package mage.cards.n;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NykthosShrineToNyx extends CardImpl {

    public NykthosShrineToNyx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.
        Ability ability = new NykthosShrineToNyxManaAbility();
        ability.addHint(new ValueHint("Devotion to red", NykthosDynamicManaEffect.xValueR));
        ability.addHint(new ValueHint("Devotion to blue", NykthosDynamicManaEffect.xValueU));
        ability.addHint(new ValueHint("Devotion to white", NykthosDynamicManaEffect.xValueW));
        ability.addHint(new ValueHint("Devotion to black", NykthosDynamicManaEffect.xValueB));
        ability.addHint(new ValueHint("Devotion to green", NykthosDynamicManaEffect.xValueG));
        this.addAbility(ability);
    }

    public NykthosShrineToNyx(final NykthosShrineToNyx card) {
        super(card);
    }

    @Override
    public NykthosShrineToNyx copy() {
        return new NykthosShrineToNyx(this);
    }
}

class NykthosShrineToNyxManaAbility extends ActivatedManaAbilityImpl {

    public NykthosShrineToNyxManaAbility() {
        super(Zone.BATTLEFIELD, new NykthosDynamicManaEffect(), new GenericManaCost(2));
        this.addCost(new TapSourceCost());
    }

    public NykthosShrineToNyxManaAbility(final NykthosShrineToNyxManaAbility ability) {
        super(ability);
    }

    @Override
    public NykthosShrineToNyxManaAbility copy() {
        return new NykthosShrineToNyxManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        ArrayList<Mana> netManaCopy = new ArrayList<>();
        if (game != null) {
            netManaCopy.addAll(((ManaEffect) this.getEffects().get(0)).getNetMana(game, this));
        }
        return netManaCopy;
    }
}

class NykthosDynamicManaEffect extends ManaEffect {

    static final DynamicValue xValueR = new DevotionCount(ColoredManaSymbol.R);
    static final DynamicValue xValueU = new DevotionCount(ColoredManaSymbol.U);
    static final DynamicValue xValueW = new DevotionCount(ColoredManaSymbol.W);
    static final DynamicValue xValueB = new DevotionCount(ColoredManaSymbol.B);
    static final DynamicValue xValueG = new DevotionCount(ColoredManaSymbol.G);

    public NykthosDynamicManaEffect() {
        super();
        this.staticText = "Choose a color. Add an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>";
    }

    public NykthosDynamicManaEffect(final NykthosDynamicManaEffect effect) {
        super(effect);
    }

    @Override
    public NykthosDynamicManaEffect copy() {
        return new NykthosDynamicManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;

    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        for (String colorChoice : ChoiceColor.getBaseColors()) {
            Mana mana = computeMana(colorChoice, game, source);
            if (mana.count() > 0) {
                netMana.add(mana);
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceColor choice = new ChoiceColor();
            choice.setMessage("Choose a color for devotion of Nykthos");
            if (controller.choose(outcome, choice, game)) {
                return computeMana(choice.getChoice(), game, source);
            }
        }
        return null;
    }

    public Mana computeMana(String color, Game game, Ability source) {
        Mana mana = new Mana();
        if (color != null && !color.isEmpty()) {
            switch (color) {
                case "Red":
                    mana.setRed(xValueR.calculate(game, source, this));
                    break;
                case "Blue":
                    mana.setBlue(xValueU.calculate(game, source, this));
                    break;
                case "White":
                    mana.setWhite(xValueW.calculate(game, source, this));
                    break;
                case "Black":
                    mana.setBlack(xValueB.calculate(game, source, this));
                    break;
                case "Green":
                    mana.setGreen(xValueG.calculate(game, source, this));
                    break;
            }
        }
        return mana;
    }
}
