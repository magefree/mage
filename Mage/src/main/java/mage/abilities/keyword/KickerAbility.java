package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Stream;

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

    protected KickerAbility(final KickerAbility ability) {
        super(ability);
        for (OptionalAdditionalCost cost : ability.kickerCosts) {
            this.kickerCosts.add(cost.copy());
        }
        this.keywordText = ability.keywordText;
        this.reminderText = ability.reminderText;
    }

    @Override
    public KickerAbility copy() {
        return new KickerAbility(this);
    }

    public final OptionalAdditionalCost addKickerCost(String manaString) {
        OptionalAdditionalCost newCost = new OptionalAdditionalCostImpl(
                keywordText, reminderText, new ManaCostsImpl<>(manaString));
        addKickerCostAndSetup(newCost);
        return newCost;
    }

    public final OptionalAdditionalCost addKickerCost(Cost cost) {
        OptionalAdditionalCost newCost = new OptionalAdditionalCostImpl(
                keywordText, "&mdash;", reminderText, cost);
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
    }

    private static String getActivationKey(String needKickerCost){
        return "kickerActivation"+needKickerCost;
    }

    /**
     * Return total kicker activations with the specified Cost (blank for all kickers/multikickers)
     * Checks the start of the tags, to work for that blank method, which requires direct access
     *
     * @param game
     * @param source
     * @param needKickerCost use cost.getText(true)
     * @return
     */
    public static int getKickedCounterStrict(Game game, Ability source, String needKickerCost) {
        Map<String, Object> costsTags = CardUtil.getSourceCostsTagsMap(game, source);
        if (costsTags == null) {
            return 0;
        }
        String finalActivationKey = getActivationKey(needKickerCost);
        Stream<Map.Entry<String, Object>> tagStream = costsTags.entrySet().stream()
                .filter(x -> x.getKey().startsWith(finalActivationKey));
        return tagStream.mapToInt(x -> {
                Object value = x.getValue();
                if (!(value instanceof Integer)){
                    throw new IllegalStateException("Wrong code usage: Kicker tag "+x.getKey()+" needs Integer but has "+(value==null?"null":value.getClass().getName()));
                }
                return (int) value;
            }).sum();
    }

    /**
     * Return total kicker activations (kicker + multikicker)
     *
     * @param game
     * @param source
     * @return
     */
    public static int getKickedCounter(Game game, Ability source) {
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
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.KICKED, source.getSourceId(), source, source.getControllerId()));

        String activationKey = getActivationKey(kickerCost.getText(true));
        Integer next = CardUtil.getSourceCostsTag(game, source, activationKey,0)+1;
        source.setCostsTag(activationKey,next);
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

    private void addKickerCostsToAbility(Cost cost, Ability ability, Game game) {
        // can contain multiple costs from multikicker ability
        // must be additional cost type
        if (cost instanceof ManaCostsImpl) {
            ability.addManaCostsToPay((ManaCostsImpl) cost.copy());
        } else {
            ability.addCost(cost.copy());
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
        Spell spell = game.getSpellOrLKIStack(spellId);
        if (spell != null) {
            return KickerAbility.getKickedCounter(game, spell.getSpellAbility());
        }
        return 0;
    }
}
