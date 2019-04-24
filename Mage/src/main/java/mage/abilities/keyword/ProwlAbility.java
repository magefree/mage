
package mage.abilities.keyword;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.AlternativeCost2;
import mage.abilities.costs.AlternativeCost2Impl;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.ProwlWatcher;

/**
 * 702.74. Prowl #
 *
 * 702.74a Prowl is a static ability that functions on the stack. "Prowl [cost]"
 * means "You may pay [cost] rather than pay this spell's mana cost if a player
 * was dealt combat damage this turn by a source that, at the time it dealt that
 * damage, was under your control and had any of this spell's creature types."
 * Paying a spell's prowl cost follows the rules for paying alternative costs in
 * rules 601.2b and 601.2e-g
 *
 * @author LevelX2
 */
public class ProwlAbility extends StaticAbility implements AlternativeSourceCosts {

    private static final String PROWL_KEYWORD = "Prowl";
    private final List<AlternativeCost2> prowlCosts = new LinkedList<>();
    private String reminderText;

    public ProwlAbility(Card card, String manaString) {
        super(Zone.STACK, null);
        setRuleAtTheTop(true);
        name = PROWL_KEYWORD;
        setReminderText(card);
        this.addProwlCost(manaString);
        addWatcher(new ProwlWatcher());

    }

    public ProwlAbility(final ProwlAbility ability) {
        super(ability);
        this.prowlCosts.addAll(ability.prowlCosts);
        this.reminderText = ability.reminderText;
    }

    @Override
    public ProwlAbility copy() {
        return new ProwlAbility(this);
    }

    public final AlternativeCost2 addProwlCost(String manaString) {
        AlternativeCost2 prowlCost = new AlternativeCost2Impl(PROWL_KEYWORD, reminderText, new ManaCostsImpl(manaString));
        prowlCosts.add(prowlCost);
        return prowlCost;
    }

    public void resetProwl() {
        for (AlternativeCost2 cost : prowlCosts) {
            cost.reset();
        }
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        for (AlternativeCost2 cost : prowlCosts) {
            if (cost.isActivated(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return true;
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(controllerId);
            ProwlWatcher prowlWatcher = (ProwlWatcher) game.getState().getWatchers().get(ProwlWatcher.class.getSimpleName());
            Card card = game.getCard(ability.getSourceId());
            if (player == null || prowlWatcher == null || card == null) {
                throw new IllegalArgumentException("Params can't be null");
            }
            boolean canProwl = false;
            for (SubType subtype : card.getSubtype(game)) {
                if (prowlWatcher.hasSubtypeMadeCombatDamage(ability.getControllerId(), subtype)) {
                    canProwl = true;
                    break;
                }
            }
            if (canProwl) {
                this.resetProwl();
                for (AlternativeCost2 prowlCost : prowlCosts) {
                    if (prowlCost.canPay(ability, sourceId, controllerId, game)
                            && player.chooseUse(Outcome.Benefit, new StringBuilder("Cast for ").append(PROWL_KEYWORD).append(" cost ").append(prowlCost.getText(true)).append(" ?").toString(), ability, game)) {
                        prowlCost.activate();
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = ((Costs) prowlCost).iterator(); it.hasNext();) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCostsImpl) {
                                ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                    }
                }
            }
        }
        return isActivated(ability, game);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        int numberCosts = 0;
        String remarkText = "";
        for (AlternativeCost2 prowlCost : prowlCosts) {
            if (numberCosts == 0) {
                sb.append(prowlCost.getText(false));
                remarkText = prowlCost.getReminderText();
            } else {
                sb.append(" and/or ").append(prowlCost.getText(true));
            }
            ++numberCosts;
        }
        if (numberCosts == 1) {
            sb.append(' ').append(remarkText);
        }

        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix(Game game) {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        for (AlternativeCost2 cost : prowlCosts) {
            if (cost.isActivated(game)) {
                sb.append(cost.getCastSuffixMessage(position));
                ++position;
            }
        }
        return sb.toString();
    }

    private void setReminderText(Card card) {
        reminderText = 
                "(You may cast this for its prowl cost if you dealt combat damage to a player this turn with a creature that shared a creature type with {this}";
    }

    @Override
    public Costs<Cost> getCosts() {
        Costs<Cost> alterCosts = new CostsImpl<>();
        for (AlternativeCost2 aCost : prowlCosts) {
            alterCosts.add(aCost.getCost());
        }
        return alterCosts;
    }
}
