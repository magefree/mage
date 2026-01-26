package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.ManaType;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class AddConditionalManaEffect extends ManaEffect {

    private final Mana mana;
    private final ConditionalManaBuilder manaBuilder;
    private final DynamicValue netAmount;

    public AddConditionalManaEffect(Mana mana, ConditionalManaBuilder manaBuilder) {
        this(mana, manaBuilder, null);
    }

    public AddConditionalManaEffect(Mana mana, ConditionalManaBuilder manaBuilder, DynamicValue netAmount) {
        super();
        this.mana = mana;
        this.manaBuilder = manaBuilder;
        this.netAmount = netAmount;
        staticText = "Add " + this.mana.toString() + ". " + manaBuilder.getRule();

    }

    protected AddConditionalManaEffect(final AddConditionalManaEffect effect) {
        super(effect);
        this.mana = effect.mana.copy();
        this.manaBuilder = effect.manaBuilder;
        this.netAmount = effect.netAmount;
    }

    @Override
    public AddConditionalManaEffect copy() {
        return new AddConditionalManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState() && netAmount != null) {
            List<Mana> maxAvailableMana = new ArrayList<>();
            int amountAvailableMana = netAmount.calculate(game, source, this);
            if (amountAvailableMana > 0) {
                Mana calculatedMana = mana.copy();
                for (ManaType manaType : ManaType.getTrueManaTypes()) {
                    calculatedMana.set(manaType, CardUtil.overflowMultiply(calculatedMana.get(manaType), amountAvailableMana));
                }
                maxAvailableMana.add(manaBuilder.setMana(calculatedMana, source, game).build());
            }
            return maxAvailableMana;
        }
        return super.getNetMana(game, source);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            return manaBuilder.setMana(mana, source, game).build();
        } else {
            return new Mana();
        }
    }

    public Mana getMana() {
        return mana;
    }
}
