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

    /**
     * Return all options for paying the mana cost (this) while taking into accoutn if the player can pay life.
     * Used to correctly highlight (or not) spells with Phyrexian mana depending on if the player can pay life costs.
     * <p>
     * E.g. Tezzeret's Gambit has a cost of {3}{U/P}.
     * If `canPayLifeCost == true` the two options are {3}{U} and {3}. The second including a 2 life cost that is not
     * captured by the ManaOptions object being returned.
     * However, if `canPayLifeCost == false` than the return is only {3}{U} since the Phyrexian mana MUST be paid with
     * a {U} and not with 2 life.
     *
     * @param canPayLifeCost if the player is able to pay life for the ability/effect that this cost is associated with
     * @return
     */
    ManaOptions getOptions(boolean canPayLifeCost);

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
