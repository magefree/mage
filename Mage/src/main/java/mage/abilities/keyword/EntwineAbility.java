
package mage.abilities.keyword;

import java.util.Iterator;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.costs.OptionalAdditionalModeSourceCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * 702.40. Entwine
 *
 * 702.40a Entwine is a static ability of modal spells (see rule 700.2) that
 * functions while the spell is on the stack. "Entwine [cost]" means "You may
 * choose all modes of this spell instead of just one. If you do, you pay an
 * additional [cost]." Using the entwine ability follows the rules for choosing
 * modes and paying additional costs in rules 601.2b and 601.2e-g.
 *
 * 702.40b If the entwine cost was paid, follow the text of each of the modes in
 * the order written on the card when the spell resolves.
 *
 * @author LevelX2
 */
public class EntwineAbility extends StaticAbility implements OptionalAdditionalModeSourceCosts {

    private static final String keywordText = "Entwine";
    private static final String reminderText = "Choose both if you pay the entwine cost.";
    protected OptionalAdditionalCost additionalCost;

    public EntwineAbility(String manaString) {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(keywordText, reminderText, new ManaCostsImpl(manaString));
    }

    public EntwineAbility(Cost cost) {
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
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                this.resetCosts();
                if (additionalCost != null) {
                    if (additionalCost.canPay(ability, ability.getSourceId(), ability.getControllerId(), game)
                            && player.chooseUse(Outcome.Benefit, "Pay " + additionalCost.getText(false) + " ?", ability, game)) {

                        additionalCost.activate();
                        ability.getModes().setAdditionalCost(this);
                        ability.getModes().setMinModes(2);
                        ability.getModes().setMaxModes(2);
                    }
                }
            }
        }
    }

    @Override
    public void addOptionalAdditionalModeCosts(Ability ability, Game game) {
        if (additionalCost.isActivated()) {
            for (Iterator it = ((Costs) additionalCost).iterator(); it.hasNext();) {
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
