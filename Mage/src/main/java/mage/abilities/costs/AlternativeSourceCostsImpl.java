package mage.abilities.costs;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Iterator;

/**
 * @author TheElk801
 */
public abstract class AlternativeSourceCostsImpl extends StaticAbility implements AlternativeSourceCosts {

    protected final AlternativeCost alternativeCost;
    protected final String reminderText;
    protected final String activationKey;
    protected static String getActivationKey(String name){
        return name+"ActivationKey";
    }

    protected AlternativeSourceCostsImpl(String name, String reminderText, String manaString) {
        this(name, reminderText, new ManaCostsImpl<>(manaString));
    }

    protected AlternativeSourceCostsImpl(String name, String reminderText, Cost cost) {
        super(Zone.ALL, null);
        this.name = name;
        this.reminderText = reminderText;
        this.alternativeCost = new AlternativeCostImpl<>(name, reminderText, cost);
        this.activationKey = getActivationKey(name);
    }

    protected AlternativeSourceCostsImpl(final AlternativeSourceCostsImpl ability) {
        super(ability);
        this.alternativeCost = ability.alternativeCost.copy();
        this.reminderText = ability.reminderText;
        this.activationKey = ability.activationKey;
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            handleActivatingAlternativeCosts(ability, game);
        }
        return isActivated(ability, game);
    }

    protected boolean handleActivatingAlternativeCosts(Ability ability, Game game) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return false;
        }
        this.resetCost();
        if (!alternativeCost.canPay(ability, this, player.getId(), game)
                || !player.chooseUse(Outcome.Benefit, "Cast this for its " + this.name + " cost? (" + alternativeCost.getText(true) + ')', ability, game)) {
            return false;
        }
        ability.setCostsTag(activationKey, null);
        alternativeCost.activate();

        ability.clearManaCostsToPay();
        ability.clearCosts();
        for (Iterator<Cost> it = ((Costs<Cost>) alternativeCost).iterator(); it.hasNext(); ) {
            Cost cost = it.next();
            if (cost instanceof ManaCost) {
                ability.addManaCostsToPay((ManaCost) cost.copy());
            } else {
                ability.addCost(cost.copy());
            }
        }
        return true;
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        return CardUtil.checkSourceCostsTagExists(game, ability, activationKey);
    }

    @Override
    public Costs<Cost> getCosts() {
        return (Costs<Cost>) alternativeCost;
    }

    @Override
    public String getRule() {
        return alternativeCost.getText(false) + ' ' + alternativeCost.getReminderText();
    }

    @Override
    public void resetCost() {
        alternativeCost.reset();
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return true;
    }

    public String getCastMessageSuffix(Game game) {
        return alternativeCost.getCastSuffixMessage(0);
    }
}
