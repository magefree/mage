package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.costs.OptionalAdditionalModeSourceCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;

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

    protected OptionalAdditionalCost additionalCost;
    protected Set<String> activations = new HashSet<>(); // same logic as KickerAbility: activations per zoneChangeCounter

    public EntwineAbility(String manaString) {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(keywordText, reminderText, new ManaCostsImpl(manaString));
    }

    public EntwineAbility(Cost cost) {
        this(cost, reminderText);
    }

    public EntwineAbility(Cost cost, String reminderText) {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(keywordText, "-", reminderText, cost);
        setRuleAtTheTop(true);
    }

    public EntwineAbility(final EntwineAbility ability) {
        super(ability);
        if (ability.additionalCost != null) {
            this.additionalCost = ability.additionalCost.copy();
        }
        this.activations.addAll(ability.activations);
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

        this.resetCosts(game, ability);
        if (additionalCost == null) {
            return;
        }

        if (additionalCost.canPay(ability, ability.getSourceId(), ability.getControllerId(), game)
                && player.chooseUse(Outcome.Benefit, "Pay " + additionalCost.getText(false) + " ?", ability, game)) {
            addCostsToAbility(additionalCost, ability);
            activateCost(game, ability);
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

    public void changeModes(Ability ability, Game game) {
        if (!costWasActivated(ability, game)) {
            return;
        }

        // activate max modes all the time
        int maxModes = ability.getModes().size();
        ability.getModes().setMinModes(maxModes);
        ability.getModes().setMaxModes(maxModes);
    }

    private void addCostsToAbility(Cost cost, Ability ability) {
        ability.addCost(cost.copy());
    }

    private void resetCosts(Game game, Ability source) {
        if (additionalCost != null) {
            additionalCost.reset();
        }

        String key = getActivationKey(source, game);
        this.activations.remove(key);
    }

    private void activateCost(Game game, Ability source) {
        String key = getActivationKey(source, game);
        this.activations.add(key);
    }

    public boolean costWasActivated(Ability ability, Game game) {
        String key = getActivationKey(ability, game);
        return this.activations.contains(key);
    }

    private String getActivationKey(Ability source, Game game) {
        // same logic as KickerAbility, uses for source ability only
        int zcc = 0;
        if (source.getAbilityType() == AbilityType.TRIGGERED) {
            zcc = source.getSourceObjectZoneChangeCounter();
        }
        if (zcc == 0) {
            zcc = game.getState().getZoneChangeCounter(source.getSourceId());
        }
        if (zcc > 0 && (source.getAbilityType() == AbilityType.TRIGGERED)) {
            --zcc;
        }
        return String.valueOf(zcc);
    }
}
