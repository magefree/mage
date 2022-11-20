package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CastFromHandWatcher;

import java.util.Iterator;

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

    protected static final String SQUAD_KEYWORD = "Squad";
    protected static final String SQUAD_REMINDER = "You may pay an additional "
            + "{cost} as you cast this spell.\n"
            + "When this creature enters the battlefield, "
            + "if its squad cost was paid, create a token "
            + "that's a copy of it for each time its squad cost was paid.";

    protected OptionalAdditionalCost squadCost;

    protected SquadTriggeredAbility linkedTrigger;

    protected int activations;

    public SquadAbility() {
        this(new GenericManaCost(2));
    }

    public SquadAbility(Cost cost) {
        super(Zone.STACK, null);
        this.squadCost = new OptionalAdditionalCostImpl(SQUAD_KEYWORD, SQUAD_REMINDER, cost);
        this.squadCost.setCostType(VariableCostType.ADDITIONAL);
        this.squadCost.setRepeatable(true);

        setRuleAtTheTop(true);
        this.activations = 0;

        this.linkedTrigger = new SquadTriggeredAbility(this);
        this.addSubAbility(this.linkedTrigger);
    }

    private SquadAbility(final SquadAbility ability) {
        super(ability);
        this.squadCost = ability.squadCost;
        this.activations = ability.activations;

        this.linkedTrigger = new SquadTriggeredAbility(this);
        this.addSubAbility(this.linkedTrigger);
    }

    @Override
    public SquadAbility copy() { return new SquadAbility(this); }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.squadCost.getText(false));
        sb.append(this.squadCost.getReminderText());
        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        if (this.squadCost.isActivated()) {
            return this.squadCost.getCastSuffixMessage(0);
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

        squadCost.reset();
        this.activations = 0;
        boolean again = true;
        while (player.canRespond() && again) {
            String times = (this.activations + 1) + (this.activations == 0 ? " time " : " times ");
            // TODO: add AI support to find max number of possible activations (from available mana)
            //  canPay checks only single mana available, not total mana usage
            if (squadCost.canPay(ability, this, ability.getControllerId(), game)
                    && player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt,
                    "Pay " + times + squadCost.getText(false) + " ?", ability, game)) {
                this.activations += 1;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.SQUAD_PAID,
                                                    ability.getSourceId(),
                                                    ability,
                                                    ability.getControllerId()));
                ability.getCosts().add(squadCost.copy());
            } else {
                again = false;
            }
        }
    }

    protected enum SquadPaidCount implements DynamicValue {
        instance;

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            return SquadAbility.getSourceObjectSquadCount(game, sourceAbility);
        }

        @Override
        public SquadPaidCount copy() {
            return SquadPaidCount.instance;
        }

        @Override
        public String toString() {
            return "1";
        }

        @Override
        public String getMessage() {
            return "time its squad cost was paid.";
        }
    }

    /**
     * Find source object's squad status. Can be used in any places, e.g. in ETB effects
     */
    protected static int getSourceObjectSquadCount(Game game, Ability abilityToCheck) {
        if(abilityToCheck == null) return 0;

        MageObject sourceObject = abilityToCheck.getSourceObject(game);
        int count = 0;
        if (sourceObject instanceof Card) {
            for (Ability ability : ((Card) sourceObject).getAbilities(game)) {
                if (ability instanceof SquadAbility) {
                    count += ((SquadAbility) ability).activations;
                }
            }
        }
        return count;
    }

    // The linked triggered ability for Squad.
    protected class SquadTriggeredAbility extends EntersBattlefieldTriggeredAbility {

        protected SquadAbility staticAbility;

        public SquadTriggeredAbility(SquadAbility staticAbility){
            super(new CreateTokenCopySourceEffect(SquadPaidCount.instance));
            this.staticAbility = staticAbility;
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
            return SquadAbility.getSourceObjectSquadCount(game, this.staticAbility) > 0;
        }
    }
}