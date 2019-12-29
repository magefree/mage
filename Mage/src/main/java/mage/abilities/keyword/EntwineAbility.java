
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Iterator;

/**
 * 702.40. Entwine
 * <p>
 * 702.40a Entwine is a static ability of modal spells (see rule 700.2) that
 * functions while the spell is on the stack. "Entwine [cost]" means "You may
 * choose all modes of this spell instead of just one. If you do, you pay an
 * additional [cost]." Using the entwine ability follows the rules for choosing
 * modes and paying additional costs in rules 601.2b and 601.2e-g.
 * <p>
 * 702.40b If the entwine cost was paid, follow the text of each of the modes in
 * the order written on the card when the spell resolves.
 *
 * @author LevelX2
 */
public class EntwineAbility extends StaticAbility implements OptionalAdditionalModeSourceCosts {

    private static final String keywordText = "Entwine";
    protected OptionalAdditionalCost additionalCost;

    public EntwineAbility(String manaString) {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(keywordText, "Choose both if you pay the entwine cost.", new ManaCostsImpl(manaString));
    }

    public EntwineAbility(Cost cost) {
        this(cost, "Choose both if you pay the entwine cost.");
    }

    public EntwineAbility(Cost cost, String reminderText) {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(keywordText, "-", reminderText, cost);
        setRuleAtTheTop(true);
    }

    public EntwineAbility(final EntwineAbility ability) {
        super(ability);
        additionalCost = ability.additionalCost;
    }

    @Override
    public EntwineAbility copy() {
        return new EntwineAbility(this);
    }

    @Override
    public void addCost(Cost cost) {
        if (additionalCost != null) {
            ((Costs) additionalCost).add(cost);
        }
    }

    @Override
    public boolean isActivated() {
        if (additionalCost != null) {
            return additionalCost.isActivated();
        }
        return false;
    }

    public void resetCosts() {
        if (additionalCost != null) {
            additionalCost.reset();
        }
    }

    @Override
    public void changeModes(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return;
        }
        this.resetCosts();
        if (additionalCost == null) {
            return;
        }
        if (additionalCost.canPay(ability, ability.getSourceId(), ability.getControllerId(), game)
                && player.chooseUse(Outcome.Benefit, "Pay " + additionalCost.getText(false) + " ?", ability, game)) {

            additionalCost.activate();
            int modeCount = ability.getModes().size();
            ability.getModes().setAdditionalCost(this);
            ability.getModes().setMinModes(modeCount);
            ability.getModes().setMaxModes(modeCount);
        }
    }

    @Override
    public void addOptionalAdditionalModeCosts(Ability ability, Game game) {
        if (additionalCost.isActivated()) {
            for (Iterator it = ((Costs) additionalCost).iterator(); it.hasNext(); ) {
                Cost cost = (Cost) it.next();
                if (cost instanceof ManaCostsImpl) {
                    ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                } else {
                    ability.getCosts().add(cost.copy());
                }
            }
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (additionalCost != null) {
            sb.append(additionalCost.getText(false));
            sb.append(' ').append(additionalCost.getReminderText());
        }
        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        if (additionalCost != null) {
            return additionalCost.getCastSuffixMessage(0);
        } else {
            return "";
        }
    }

    public String getReminderText() {
        if (additionalCost != null) {
            return additionalCost.getReminderText();
        } else {
            return "";
        }
    }
}
