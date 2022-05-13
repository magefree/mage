package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.mana.ManaOptions;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.constants.MultiAmountType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class AddManaInAnyCombinationEffect extends ManaEffect {

    private ArrayList<ColoredManaSymbol> manaSymbols = new ArrayList<>();
    private final DynamicValue amount;
    private final DynamicValue netAmount;

    public AddManaInAnyCombinationEffect(int amount) {
        this(StaticValue.get(amount), StaticValue.get(amount),
                ColoredManaSymbol.W,
                ColoredManaSymbol.U,
                ColoredManaSymbol.B,
                ColoredManaSymbol.R,
                ColoredManaSymbol.G
        );
    }

    public AddManaInAnyCombinationEffect(int amount, ColoredManaSymbol... coloredManaSymbols) {
        this(StaticValue.get(amount), StaticValue.get(amount), coloredManaSymbols);
    }

    public AddManaInAnyCombinationEffect(DynamicValue amount, DynamicValue netAmount, ColoredManaSymbol... coloredManaSymbols) {
        super();
        this.manaSymbols.addAll(Arrays.asList(coloredManaSymbols));
        this.amount = amount;
        this.staticText = setText();
        this.netAmount = netAmount;
    }

    public AddManaInAnyCombinationEffect(int amount, String text) {
        this(amount);
        this.staticText = text;
    }

    public AddManaInAnyCombinationEffect(int amount, String text, ColoredManaSymbol... coloredManaSymbols) {
        this(amount, coloredManaSymbols);
        this.staticText = text;
    }

    public AddManaInAnyCombinationEffect(DynamicValue amount, DynamicValue netAmount, String text, ColoredManaSymbol... coloredManaSymbols) {
        this(amount, netAmount, coloredManaSymbols);
        this.staticText = text;
    }

    public AddManaInAnyCombinationEffect(final AddManaInAnyCombinationEffect effect) {
        super(effect);
        this.manaSymbols = effect.manaSymbols;
        this.amount = effect.amount;
        if (effect.netAmount != null) {
            this.netAmount = effect.netAmount.copy();
        } else {
            this.netAmount = null;
        }
    }

    @Override
    public AddManaInAnyCombinationEffect copy() {
        return new AddManaInAnyCombinationEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            if (game.inCheckPlayableState()) {
                int count = netAmount.calculate(game, source, this);
                if (count > 0) {
                    // add color combinations
                    ManaOptions allPossibleMana = new ManaOptions();
                    for (int i = 0; i < count; ++i) {
                        ManaOptions currentPossibleMana = new ManaOptions();
                        for (ColoredManaSymbol coloredManaSymbol : manaSymbols) {
                            currentPossibleMana.add(new Mana(coloredManaSymbol));
                        }
                        allPossibleMana.addMana(currentPossibleMana);
                    }
                    allPossibleMana.removeDuplicated();
                    return allPossibleMana.stream().collect(Collectors.toList());
                }
            } else {
                int amountOfManaLeft = amount.calculate(game, source, this);
                if (amountOfManaLeft > 0) {
                    netMana.add(Mana.AnyMana(amountOfManaLeft));
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int size = manaSymbols.size();
            Mana mana = new Mana();
            List<String> manaStrings = new ArrayList<>(size);
            for (ColoredManaSymbol coloredManaSymbol : manaSymbols) {
                manaStrings.add(coloredManaSymbol.toString());
            }
            List<Integer> manaList = player.getMultiAmount(this.outcome, manaStrings, 0, amount.calculate(game, source, this), MultiAmountType.MANA, game);
            for (int i = 0; i < size; i++) {
                ColoredManaSymbol coloredManaSymbol = manaSymbols.get(i);
                int amount = manaList.get(i);
                for (int j = 0; j < amount; j++) {
                    mana.add(new Mana(coloredManaSymbol));
                }
            }
            return mana;
        }
        return null;
    }

    @Override
    public Set<ManaType> getProducableManaTypes(Game game, Ability source) {
        Set<ManaType> manaTypes = new HashSet<>();
        for (ColoredManaSymbol coloredManaSymbol : manaSymbols) {
            if (coloredManaSymbol.equals(ColoredManaSymbol.B)) {
                manaTypes.add(ManaType.BLACK);
            }
            if (coloredManaSymbol.equals(ColoredManaSymbol.R)) {
                manaTypes.add(ManaType.RED);
            }
            if (coloredManaSymbol.equals(ColoredManaSymbol.G)) {
                manaTypes.add(ManaType.GREEN);
            }
            if (coloredManaSymbol.equals(ColoredManaSymbol.U)) {
                manaTypes.add(ManaType.BLUE);
            }
            if (coloredManaSymbol.equals(ColoredManaSymbol.W)) {
                manaTypes.add(ManaType.WHITE);
            }
        }
        return manaTypes;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Add ");
        String amountString = CardUtil.numberToText(amount.toString());
        sb.append(amountString);
        sb.append(" mana in any combination of ");
        if (manaSymbols.size() == 5) {
            sb.append("colors");
        } else {
            int i = 0;
            for (ColoredManaSymbol coloredManaSymbol : manaSymbols) {
                i++;
                if (i > 1) {
                    sb.append(" and/or ");
                }
                sb.append('{').append(coloredManaSymbol.toString()).append('}');
            }
        }
        if (amountString.equals("X") && !amount.getMessage().isEmpty()) {
            sb.append(", where X is ");
            sb.append(amount.getMessage());
        }
        return sb.toString();
    }
}
