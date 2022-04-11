package mage.abilities.costs;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Iterator;

/**
 * @author TheElk801
 */
public abstract class AlternativeSourceCostsImpl extends StaticAbility implements AlternativeSourceCosts {

    protected final AlternativeCost alternativeCost;
    protected final String reminderText;
    private int zoneChangeCounter = 0;

    protected AlternativeSourceCostsImpl(String name, String reminderText, String manaString) {
        this(name, reminderText, new ManaCostsImpl<>(manaString));
    }

    protected AlternativeSourceCostsImpl(String name, String reminderText, Cost cost) {
        super(Zone.ALL, null);
        this.name = name;
        this.reminderText = reminderText;
        this.alternativeCost = new AlternativeCostImpl<>(name, reminderText, cost);
    }

    protected AlternativeSourceCostsImpl(final AlternativeSourceCostsImpl ability) {
        super(ability);
        this.alternativeCost = ability.alternativeCost.copy();
        this.reminderText = ability.reminderText;
        this.zoneChangeCounter = ability.zoneChangeCounter;
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
        alternativeCost.activate();
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
            } else {
                throw new IllegalArgumentException("source card not found");
            }
        }
        ability.getManaCostsToPay().clear();
        ability.getCosts().clear();
        for (Iterator<Cost> it = ((Costs<Cost>) alternativeCost).iterator(); it.hasNext(); ) {
            Cost cost = it.next();
            if (cost instanceof ManaCost) {
                ability.getManaCostsToPay().add((ManaCost) cost.copy());
            } else {
                ability.getCosts().add(cost.copy());
            }
        }
        return true;
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null && card.getZoneChangeCounter(game) <= zoneChangeCounter + 1) {
            return alternativeCost.isActivated(game);
        }
        return false;
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
        this.zoneChangeCounter = 0;
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return true;
    }

    public String getCastMessageSuffix(Game game) {
        return alternativeCost.getCastSuffixMessage(0);
    }
}
