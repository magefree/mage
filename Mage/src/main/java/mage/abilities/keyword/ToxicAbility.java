package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.abilities.InfectAbilityIcon;
import mage.constants.Zone;
import mage.util.CardUtil;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 */
public class ToxicAbility extends StaticAbility {

    private final int amount;

    public ToxicAbility(int amount) {
        super(Zone.BATTLEFIELD, null);
        this.amount = amount;
    }

    private ToxicAbility(final ToxicAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public String getRule() {
        return "toxic " + amount + " <i>(Players dealt combat damage by this creature also get " +
                CardUtil.numberToText(amount, "a") + " poison counter" + (amount > 1 ? "s" : "") + ".)</i>";
    }

    @Override
    public ToxicAbility copy() {
        return new ToxicAbility(this);
    }

    public int getAmount() {
        return amount;
    }
}
