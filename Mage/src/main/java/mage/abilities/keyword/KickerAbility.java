package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 20121001 702.31. Kicker 702.31a Kicker is a static ability that functions
 * while the spell with kicker is on the stack. "Kicker [cost]" means "You may
 * pay an additional [cost] as you cast this spell." Paying a spell's kicker
 * cost(s) follows the rules for paying additional costs in rules 601.2b and
 * 601.2e-g. 702.31b The phrase "Kicker [cost 1] and/or [cost 2]" means the same
 * thing as "Kicker [cost 1], kicker [cost 2]." 702.31c Multikicker is a variant
 * of the kicker ability. "Multikicker [cost]" means "You may pay an additional
 * [cost] any number of times as you cast this spell." A multikicker cost is a
 * kicker cost. 702.31d If a spell's controller declares the intention to pay
 * any of that spell's kicker costs, that spell has been "kicked." If a spell
 * has two kicker costs or has multikicker, it may be kicked multiple times. See
 * rule 601.2b. 702.31e Objects with kicker or multikicker have additional
 * abilities that specify what happens if they are kicked. These abilities are
 * linked to the kicker or multikicker abilities printed on that object: they
 * can refer only to those specific kicker or multikicker abilities. See rule
 * 607, "Linked Abilities." 702.31f Objects with more than one kicker cost have
 * abilities that each correspond to a specific kicker cost. They contain the
 * phrases "if it was kicked with its [A] kicker" and "if it was kicked with its
 * [B] kicker," where A and B are the first and second kicker costs listed on
 * the card, respectively. Each of those abilities is linked to the appropriate
 * kicker ability. 702.31g If part of a spell's ability has its effect only if
 * that spell was kicked, and that part of the ability includes any targets, the
 * spell's controller chooses those targets only if that spell was kicked.
 * Otherwise, the spell is cast as if it did not have those targets. See rule
 * 601.2c.
 *
 * @author LevelX2
 */
public class KickerAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    protected static final String KICKER_KEYWORD = "Kicker";
    protected static final String KICKER_REMINDER_MANA = "You may pay an additional "
            + "{cost} as you cast this spell.";
    protected static final String KICKER_REMINDER_COST = "You may {cost} in addition "
            + "to any other costs as you cast this spell.";

    protected Map<String, Integer> activations = new ConcurrentHashMap<>(); // zoneChangeCounter, activations

    protected String keywordText;
    protected String reminderText;
    protected List<OptionalAdditionalCost> kickerCosts = new LinkedList<>();

    public KickerAbility(String manaString) {
        this(KICKER_KEYWORD, KICKER_REMINDER_MANA);
        this.addKickerCost(manaString);
    }

    public KickerAbility(Cost cost) {
        this(KICKER_KEYWORD, KICKER_REMINDER_COST);
        this.addKickerCost(cost);
    }

    public KickerAbility(String keywordText, String reminderText) {
        super(Zone.STACK, null);
        name = keywordText;
        this.keywordText = keywordText;
        this.reminderText = reminderText;
        setRuleAtTheTop(true);
    }

    public KickerAbility(final KickerAbility ability) {
        super(ability);
        for (OptionalAdditionalCost cost : ability.kickerCosts) {
            this.kickerCosts.add(cost.copy());
        }
        this.keywordText = ability.keywordText;
        this.reminderText = ability.reminderText;
        this.activations.putAll(ability.activations);
    }

    @Override
    public KickerAbility copy() {
        return new KickerAbility(this);
    }

    public final OptionalAdditionalCost addKickerCost(String manaString) {
        OptionalAdditionalCost kickerCost = new OptionalAdditionalCostImpl(
                keywordText, reminderText, new ManaCostsImpl(manaString));
        kickerCosts.add(kickerCost);
        return kickerCost;
    }

    public final OptionalAdditionalCost addKickerCost(Cost cost) {
        OptionalAdditionalCost kickerCost = new OptionalAdditionalCostImpl(
                keywordText, "-", reminderText, cost);
        kickerCosts.add(kickerCost);
        return kickerCost;
    }

    public void resetKicker(Game game, Ability source) {
        for (OptionalAdditionalCost cost : kickerCosts) {
            cost.reset();
        }
        String key = getActivationKey(source, "", game);
        for (Iterator<String> iterator = activations.keySet().iterator(); iterator.hasNext(); ) {
            String activationKey = iterator.next();
            if (activationKey.startsWith(key)
                    && activations.get(activationKey) > 0) {
                activations.put(key, 0);
            }
        }
    }

    public int getKickedCounter(Game game, Ability source) {
        String key = getActivationKey(source, "", game);
        return activations.getOrDefault(key, 0);
    }

    public boolean isKicked(Game game, Ability source, String costText) {
        String key = getActivationKey(source, costText, game);
        if (kickerCosts.size() > 1) {
            for (String activationKey : activations.keySet()) {
                if (activationKey.startsWith(key)
                        && activations.get(activationKey) > 0) {
                    return true;
                }
            }
        } else {
            if (activations.containsKey(key)) {
                return activations.get(key) > 0;

            }
        }
        return false;
    }

    public List<OptionalAdditionalCost> getKickerCosts() {
        return kickerCosts;
    }

    private void activateKicker(OptionalAdditionalCost kickerCost, Ability source, Game game) {
        int amount = 1;
        String key = getActivationKey(source, kickerCost.getText(true), game);
        if (activations.containsKey(key)) {
            amount += activations.get(key);
        }
        activations.put(key, amount);
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.KICKED,
                source.getSourceId(), source.getSourceId(), source.getControllerId()));
    }

    private String getActivationKey(Ability source, String costText, Game game) {
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
        return zcc + ((kickerCosts.size() > 1) ? costText : "");
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null) {
                this.resetKicker(game, ability);
                for (OptionalAdditionalCost kickerCost : kickerCosts) {
                    boolean again = true;
                    while (player.canRespond() && again) {
                        String times = "";
                        if (kickerCost.isRepeatable()) {
                            int activatedCount = getKickedCounter(game, ability);
                            times = (activatedCount + 1) + (activatedCount == 0 ? " time " : " times ");
                        }
                        // TODO: add AI support to find max number of possible activations (from available mana)
                        //  canPay checks only single mana available, not total mana usage
                        if (kickerCost.canPay(ability, sourceId, ability.getControllerId(), game)
                                && player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt,
                                "Pay " + times + kickerCost.getText(false) + " ?", ability, game)) {
                            this.activateKicker(kickerCost, ability, game);
                            if (kickerCost instanceof Costs) {
                                for (Iterator itKickerCost = ((Costs) kickerCost).iterator(); itKickerCost.hasNext(); ) {
                                    Object kickerCostObject = itKickerCost.next();
                                    if ((kickerCostObject instanceof Costs)
                                            || (kickerCostObject instanceof CostsImpl)) {
                                        for (@SuppressWarnings("unchecked") Iterator<Cost> itDetails
                                             = ((Costs) kickerCostObject).iterator(); itDetails.hasNext(); ) {
                                            addKickerCostsToAbility(itDetails.next(), ability, game);
                                        }
                                    } else {
                                        addKickerCostsToAbility((Cost) kickerCostObject, ability, game);
                                    }
                                }
                            } else {
                                addKickerCostsToAbility(kickerCost, ability, game);
                            }
                            again = kickerCost.isRepeatable();
                        } else {
                            again = false;
                        }
                    }
                }
            }
        }
    }

    private void addKickerCostsToAbility(Cost cost, Ability ability, Game game) {
        // can contains multiple costs from multikicker ability
        if (cost instanceof ManaCostsImpl) {
            ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
        } else {
            ability.getCosts().add(cost.copy());
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        int numberKicker = 0;
        String remarkText = "";
        for (OptionalAdditionalCost kickerCost : kickerCosts) {
            if (numberKicker == 0) {
                sb.append(kickerCost.getText(false));
                remarkText = kickerCost.getReminderText();
            } else {
                sb.append(" and/or ").append(kickerCost.getText(true));
            }
            ++numberKicker;
        }
        if (numberKicker == 1) {
            sb.append(' ').append(remarkText);
        }

        return sb.toString().replace(" .",".");
    }

    @Override
    public String getCastMessageSuffix() {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        for (OptionalAdditionalCost cost : kickerCosts) {
            if (cost.isActivated()) {
                sb.append(cost.getCastSuffixMessage(position));
                ++position;
            }
        }
        return sb.toString();
    }
}
