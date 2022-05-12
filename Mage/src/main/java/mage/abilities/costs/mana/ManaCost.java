package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.mana.ManaOptions;
import mage.constants.ColoredManaSymbol;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.ManaPool;

import java.util.List;

public interface ManaCost extends Cost {

    int manaValue();

    Mana getMana();

    List<Mana> getManaOptions();

    Mana getPayment();

    Mana getUsedManaToPay();

    void assignPayment(Game game, Ability ability, ManaPool pool, Cost costsToPay);

    void setPayment(Mana mana);

    ManaCost getUnpaid();

    ManaOptions getOptions();

    boolean testPay(Mana testMana);

    Filter getSourceFilter();

    void setSourceFilter(Filter filter);

    boolean containsColor(ColoredManaSymbol coloredManaSymbol);

    @Override
    String getText();

    @Override
    ManaCost copy();

    boolean isPhyrexian();

    void setPhyrexian(boolean phyrexian);
}
