package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.SquadDynamicValue;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rulling at 2022-11-15
 * 702.157. Squad
 * 702.157a Squad is a keyword that represents two linked abilities.
 * The first is a static ability that functions while the creature
 * spell with squad is on the stack. The second is a triggered ability
 * that functions when the creature with squad enters the battlefield.
 * “Squad [cost]” means “As an additional cost to cast this spell,
 * youmay pay [cost] any number of times” and “When this creature
 * enters the battlefield, if its squad cost was paid, create a token
 * that’s a copy of it for each time its squad cost was paid.” Paying
 * a spell’s squad cost follows the rules for paying additional costs
 * in rules 601.2b and 601.2f–h.
 * 702.157b If a spell has multiple instances of squad, each is paid separately.
 * If a permanent has multiple instances of squad, each triggers based
 * on the payments made for that squad ability as it was cast, not based
 * on payments for any other instance of squad.
 *
 * @author TheElk801, Susucre
 */
public class SquadAbility extends StaticAbility implements OptionalAdditionalSourceCosts  {

    protected Map<String, Integer> activations = new ConcurrentHashMap<>(); // zoneChangeCounter, activations

    protected static final String SQUAD_KEYWORD = "Squad";
    protected static final String SQUAD_REMINDER = "You may pay an additional "
            + "{cost} as you cast this spell.\n"
            + "When this creature enters the battlefield, "
            + "if its squad cost was paid, create a token "
            + "that's a copy of it for each time its squad cost was paid.";

    protected OptionalAdditionalCost squadCost;

    public SquadAbility() {
        this(new GenericManaCost(2));
    }

    public SquadAbility(Cost cost) {
        super(Zone.STACK, null);
        this.squadCost = new OptionalAdditionalCostImpl(SQUAD_KEYWORD, SQUAD_REMINDER, cost);
        this.squadCost.setCostType(VariableCostType.ADDITIONAL);
        this.squadCost.setRepeatable(true);

        setRuleAtTheTop(true);
        this.addSubAbility(new SquadTriggeredAbility());
    }

    private SquadAbility(final SquadAbility ability) {
        super(ability);
        this.squadCost = ability.squadCost.copy();

        setRuleAtTheTop(true);
        this.activations.putAll(ability.activations);
    }

    private void resetSquad() {
        squadCost.reset();
        activations.clear();
    }

    /**
     * Return activation zcc key for searching spell's
     * settings in source object
     */
    public static String getActivationKey(Ability source, Game game) {
        // Squad activates in STACK zone so all zcc must be from "stack moment"
        // Use cases:
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

        return zcc + "|" + source.getSourceId();
    }

    private void activateSquad(int count, Ability source, Game game) {
        int amount = count;
        String key = getActivationKey(source, game);
        if (activations.containsKey(key)) {
            amount += activations.get(key);
        }
        activations.put(key, amount);
    }

    private int getSquadCounter(Game game, Ability source) {
        String key = getActivationKey(source, game);

        int totalActivations = 0;
        if(source.getSourceId().equals(this.getSourceId())) {
            for (String activationKey : activations.keySet()) {
                if (activationKey.startsWith(key) && activations.get(activationKey) > 0) {
                    totalActivations += activations.get(activationKey);
                }
            }
        }
        return totalActivations;
    }

    @Override
    public SquadAbility copy() { return new SquadAbility(this); }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        sb.append(squadCost.getText(false));
        sb.append(squadCost.getReminderText());
        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        if (squadCost.isActivated()) {
            return squadCost.getCastSuffixMessage(0);
        }
        return "";
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

        this.resetSquad();
        int count = 0;
        boolean again = true;
        while (player.canRespond() && again) {
            String times = (count + 1) + (count == 0 ? " time " : " times ");
            // TODO: add AI support to find max number of possible activations (from available mana)
            //  canPay checks only single mana available, not total mana usage
            if (squadCost.canPay(ability, this, ability.getControllerId(), game)
                    && player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt,
                    "Pay " + times + squadCost.getText(false) + " ?", ability, game)) {
                ability.getCosts().add(squadCost.copy());
                count += 1;
            } else {
                again = false;
            }
        }
        if(count > 0) this.activateSquad(count, ability, game);
    }

    /**
     * Find source object's kicked stats. Can be used in any places, e.g. in the linked ETB effects
     */
    public static int getSourceObjectSquadCount(Game game, Ability abilityToCheck) {
        MageObject sourceObject = abilityToCheck.getSourceObject(game);
        int count = 0;
        if (sourceObject instanceof Card) {
            for (Ability ability : ((Card) sourceObject).getAbilities(game)) {
                if (ability instanceof SquadAbility) {
                    count += ((SquadAbility) ability).getSquadCounter(game, abilityToCheck);
                }
            }
        }
        return count;
    }
    // The linked triggered ability for Squad.
    protected class SquadTriggeredAbility extends EntersBattlefieldTriggeredAbility {

        public SquadTriggeredAbility(){
            super(new CreateTokenCopySourceEffect(SquadDynamicValue.instance));
        }

        public SquadTriggeredAbility(final SquadTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public String getRule() { return "When this creature enters the battlefield, "
            + "if its squad cost was paid, create a token "
            + "that's a copy of it for each time its squad cost was paid."; }

        @Override
        public SquadTriggeredAbility copy() { return new SquadTriggeredAbility(this); }

        @Override
        public boolean checkTrigger(GameEvent event, Game game){
            if(!super.checkTrigger(event, game)) return false;
            return SquadDynamicValue.instance.calculate(game, this, null) > 0;
        }
    }
}