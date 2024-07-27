package mage.abilities.costs;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.AbilityType;
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

    protected static String getActivationKey(String name) {
        return name + "ActivationKey";
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
    public boolean canActivateAlternativeCostsNow(Ability ability, Game game) {
        if (ability == null || !AbilityType.SPELL.equals(ability.getAbilityType())) {
            return isActivated(ability, game);
        }
        Player player = game.getPlayer(ability.getControllerId());
        return player != null && alternativeCost.canPay(ability, this, player.getId(), game);
    }

    @Override
    public String getAlternativeCostText(Ability ability, Game game) {
        return "Cast with " + this.name + " alternative cost: " + alternativeCost.getText(true) + CardUtil.getSourceLogName(game, this);
    }

    @Override
    public boolean activateAlternativeCosts(Ability ability, Game game) {
        this.resetCost();
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
