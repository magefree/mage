
package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.ManaPool;

/**
 *
 * @author LevelX2
 */
public class ColorlessManaCost extends ManaCostImpl {

    protected int mana;

    public ColorlessManaCost(int mana) {
        this.mana = mana;
        this.cost = Mana.ColorlessMana(mana);
        this.options.addMana(Mana.ColorlessMana(mana));
    }

    public ColorlessManaCost(ColorlessManaCost manaCost) {
        super(manaCost);
        this.mana = manaCost.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public int manaValue() {
        return mana;
    }

    @Override
    public boolean isPaid() {
        if (paid) {
            return true;
        }
        return this.isColorlessPaid(mana);
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        this.assignColorless(ability, game, pool, mana, costToPay);
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mana; i++) {
            sb.append("{C}");
        }
        return sb.toString();
    }

    @Override
    public ColorlessManaCost getUnpaid() {
        ColorlessManaCost unpaid = new ColorlessManaCost(mana - this.payment.count());
        if (sourceFilter != null) {
            unpaid.setSourceFilter(sourceFilter);
        }
        return unpaid;
    }

    @Override
    public boolean testPay(Mana testMana) {
        return testMana.getColorless() > 0;
    }

    @Override
    public ColorlessManaCost copy() {
        return new ColorlessManaCost(this);
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return false;
    }

}
