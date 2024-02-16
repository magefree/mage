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
import mage.util.CardUtil;

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
 * @author JayDi85
 */
public class EntwineAbility extends StaticAbility implements OptionalAdditionalModeSourceCosts {

    private static final String keywordText = "Entwine";
    protected static final String reminderText = "You may {cost} in addition to any other costs to use all modes.";
    protected static final String ENTWINE_ACTIVATION_VALUE_KEY = "entwineActivation";

    protected OptionalAdditionalCost entwineCost;

    public EntwineAbility(String manaString) {
        super(Zone.STACK, null);
        addEntwineCostAndSetup(new OptionalAdditionalCostImpl(keywordText, reminderText, new ManaCostsImpl<>(manaString)));
    }

    public EntwineAbility(Cost cost) {
        this(cost, reminderText);
    }

    public EntwineAbility(Cost cost, String reminderText) {
        super(Zone.STACK, null);

        addEntwineCostAndSetup(new OptionalAdditionalCostImpl(keywordText, "&mdash;", reminderText, cost));
        setRuleAtTheTop(true);
    }

    private void addEntwineCostAndSetup(OptionalAdditionalCost newCost) {
        this.entwineCost = newCost;
        this.entwineCost.setCostType(VariableCostType.ADDITIONAL);
    }

    protected EntwineAbility(final EntwineAbility ability) {
        super(ability);
        if (ability.entwineCost != null) {
            this.entwineCost = ability.entwineCost.copy();
        }
    }

    @Override
    public EntwineAbility copy() {
        return new EntwineAbility(this);
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }

        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }

        this.resetEntwine(game, ability);
        if (entwineCost == null) {
            return;
        }

        // AI can use it
        if (entwineCost.canPay(ability, this, ability.getControllerId(), game)
                && player.chooseUse(Outcome.Benefit, "Pay " + entwineCost.getText(false) + " ?", ability, game)) {
            for (Iterator it = ((Costs) entwineCost).iterator(); it.hasNext(); ) {
                Cost cost = (Cost) it.next();
                if (cost instanceof ManaCostsImpl) {
                    ability.addManaCostsToPay((ManaCostsImpl) cost.copy());
                } else {
                    ability.addCost(cost.copy());
                }
            }
            ability.setCostsTag(ENTWINE_ACTIVATION_VALUE_KEY, null);
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (entwineCost != null) {
            sb.append(entwineCost.getText(false));
            sb.append(' ').append(entwineCost.getReminderText());
        }
        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        if (entwineCost != null) {
            return entwineCost.getCastSuffixMessage(0);
        } else {
            return "";
        }
    }

    public void changeModes(Ability ability, Game game) {
        if (!costWasActivated(ability, game)) {
            return;
        }

        // activate max modes all the time
        int maxModes = ability.getModes().size();
        ability.getModes().setMinModes(maxModes);
        ability.getModes().setMaxModes(maxModes);
    }

    private void resetEntwine(Game game, Ability source) {
        if (entwineCost != null) {
            entwineCost.reset();
        }
    }

    public boolean costWasActivated(Ability ability, Game game) {
        return CardUtil.checkSourceCostsTagExists(game, ability, ENTWINE_ACTIVATION_VALUE_KEY);
    }
}
