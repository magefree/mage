package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;
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
 * @author LevelX2, JayDi85
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
        OptionalAdditionalCost newCost = new OptionalAdditionalCostImpl(
                keywordText, reminderText, new ManaCostsImpl(manaString));
        addKickerCostAndSetup(newCost);
        return newCost;
    }

    public final OptionalAdditionalCost addKickerCost(Cost cost) {
        OptionalAdditionalCost newCost = new OptionalAdditionalCostImpl(
                keywordText, "-", reminderText, cost);
        addKickerCostAndSetup(newCost);
        return newCost;
    }

    private void addKickerCostAndSetup(OptionalAdditionalCost newCost) {
        this.kickerCosts.add(newCost);
        this.kickerCosts.forEach(cost -> {
            cost.setCostType(VariableCostType.ADDITIONAL);
        });
    }

    private void resetKicker() {
        for (OptionalAdditionalCost cost : kickerCosts) {
            cost.reset();
        }
        activations.clear();
    }

    private int getKickedCounterStrict(Game game, Ability source, String needKickerCost) {
        String key;
        if (needKickerCost.isEmpty()) {
            // need all kickers
            key = getActivationKey(source, "", game);
        } else {
            // need only cost related kickers
            key = getActivationKey(source, needKickerCost, game);
        }

        int totalActivations = 0;
        if (kickerCosts.size() > 1) {
            for (String activationKey : activations.keySet()) {
                if (activationKey.startsWith(key) && activations.get(activationKey) > 0) {
                    totalActivations += activations.get(activationKey);
                }
            }
        } else {
            if (activations.containsKey(key) && activations.get(key) > 0) {
                totalActivations += activations.get(key);
            }
        }
        return totalActivations;
    }

    /**
     * Return total kicker activations (kicker + multikicker)
     *
     * @param game
     * @param source
     * @return
     */
    public int getKickedCounter(Game game, Ability source) {
        return getKickedCounterStrict(game, source, "");
    }

    /**
     * If spell was kicked
     *
     * @param game
     * @param source
     * @return
     */
    public boolean isKicked(Game game, Ability source) {
        return isKicked(game, source, "");
    }

    /**
     * If spell was kicked by specific kicker cost
     *
     * @param game
     * @param source
     * @param needKickerCost use cost.getText(true)
     * @return
     */
    public boolean isKicked(Game game, Ability source, String needKickerCost) {
        return getKickedCounterStrict(game, source, needKickerCost) > 0;
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
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.KICKED, source.getSourceId(), source, source.getControllerId()));
    }

    /**
     * Return activation zcc key for searching spell's settings in source object
     *
     * @param source
     * @param game
     * @return
     */
    public static String getActivationKey(Ability source, Game game) {
        // Kicker activates in STACK zone so all zcc must be from "stack moment"
        // Use cases:
        // * resolving spell have same zcc (example: check kicker status in sorcery/instant);
        // * copied spell have same zcc as source spell (see Spell.copySpell and zcc sync);
        // * creature/token from resolved spell have +1 zcc after moved to battlefield (example: check kicker status in ETB triggers/effects);

        // find object info from the source ability (it can be a permanent or a spell on stack, on the moment of trigger/resolve)
        MageObject sourceObject = source.getSourceObject(game);
        Zone sourceObjectZone = game.getState().getZone(sourceObject.getId());
        int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, source);

        // find "stack moment" zcc:
        // * permanent cards enters from STACK to BATTLEFIELD (+1 zcc)
        // * permanent tokens enters from OUTSIDE to BATTLEFIELD (+1 zcc, see prepare code in TokenImpl.putOntoBattlefieldHelper)
        // * spells and copied spells resolves on STACK (zcc not changes)
        if (sourceObjectZone != Zone.STACK) {
            --zcc;
        }

        return zcc + "";
    }

    private String getActivationKey(Ability source, String costText, Game game) {
        return getActivationKey(source, game) + ((kickerCosts.size() > 1) ? costText : "");
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null) {
                this.resetKicker();
                for (OptionalAdditionalCost kickerCost : kickerCosts) {
                    boolean again = true;
                    while (player.canRespond() && again) {
                        String times = "";
                        if (kickerCost.isRepeatable()) {
                            int activatedCount = getKickedCounterStrict(game, ability, kickerCost.getText(true));
                            times = (activatedCount + 1) + (activatedCount == 0 ? " time " : " times ");
                        }
                        // TODO: add AI support to find max number of possible activations (from available mana)
                        //  canPay checks only single mana available, not total mana usage
                        if (kickerCost.canPay(ability, this, ability.getControllerId(), game)
                                && player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt,
                                "Pay " + times + kickerCost.getText(false) + " ?", ability, game)) {
                            this.activateKicker(kickerCost, ability, game);
                            if (kickerCost instanceof Costs) {
                                // as multiple costs
                                for (Iterator itKickerCost = ((Costs) kickerCost).iterator(); itKickerCost.hasNext(); ) {
                                    Object kickerCostObject = itKickerCost.next();
                                    if ((kickerCostObject instanceof Costs)) {
                                        for (@SuppressWarnings("unchecked") Iterator<Cost> itDetails
                                             = ((Costs) kickerCostObject).iterator(); itDetails.hasNext(); ) {
                                            addKickerCostsToAbility(itDetails.next(), ability, game);
                                        }
                                    } else {
                                        addKickerCostsToAbility((Cost) kickerCostObject, ability, game);
                                    }
                                }
                            } else {
                                // as single cost
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
        // can contain multiple costs from multikicker ability
        // must be additional cost type
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

        return sb.toString().replace(" .", ".");
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

    /**
     * Find spell's kicked stats. Must be used on stack only, e.g. for SPELL_CAST events
     *
     * @param game
     * @param spellId
     * @return
     */
    public static int getSpellKickedCount(Game game, UUID spellId) {
        int count = 0;
        Spell spell = game.getSpellOrLKIStack(spellId);
        if (spell != null) {
            for (Ability ability : spell.getAbilities(game)) {
                if (ability instanceof KickerAbility) {
                    count += ((KickerAbility) ability).getKickedCounter(game, spell.getSpellAbility());
                }
            }
        }
        return count;
    }

    /**
     * Find source object's kicked stats. Can be used in any places, e.g. in ETB effects
     *
     * @param game
     * @param abilityToCheck
     * @return
     */
    public static int getSourceObjectKickedCount(Game game, Ability abilityToCheck) {
        MageObject sourceObject = abilityToCheck.getSourceObject(game);
        int count = 0;
        if (sourceObject instanceof Card) {
            for (Ability ability : ((Card) sourceObject).getAbilities(game)) {
                if (ability instanceof KickerAbility) {
                    count += ((KickerAbility) ability).getKickedCounter(game, abilityToCheck);
                }
            }
        }
        return count;
    }
}
