package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SharesColorWithSourcePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/*
 * 702.77. Conspire
 * 702.77a Conspire is a keyword that represents two abilities.
 * The first is a static ability that functions while the spell with conspire is on the stack.
 * The second is a triggered ability that functions while the spell with conspire is on the stack.
 * "Conspire" means "As an additional cost to cast this spell,
 * you may tap two untapped creatures you control that each share a color with it"
 * and "When you cast this spell, if its conspire cost was paid, copy it.
 * If the spell has any targets, you may choose new targets for the copy."
 * Paying a spell's conspire cost follows the rules for paying additional costs in rules 601.2b and 601.2e–g.
 * 702.77b If a spell has multiple instances of conspire, each is paid separately and triggers
 * based on its own payment, not any other instance of conspire. *
 *
 * @author LevelX
 */
public class ConspireAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Conspire";
    protected static final String CONSPIRE_ACTIVATION_KEY = "ConspireActivation";
    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped creatures you control that share a color with it");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SharesColorWithSourcePredicate.instance);
        filter.add(CardType.CREATURE.getPredicate());
    }

    public enum ConspireTargets {
        NONE(""),
        ONE(" and you may choose a new target for the copy"),
        MORE(" and you may choose new targets for the copy");
        private final String message;

        ConspireTargets(String message) {
            this.message = message;
        }

        public String getReminder() {
            return "as you cast this spell, you may tap two untapped creatures you control " +
                    "that share a color with it. When you do, copy it" + message;
        }
    }

    private final String reminderText;
    private final OptionalAdditionalCost conspireCost;

    /**
     * Unique Id for a ConspireAbility but may not change while a continuous
     * effect gives Conspire
     *
     * @param conspireTargets controls the content of the reminder text
     */
    public ConspireAbility(ConspireTargets conspireTargets) {
        super(Zone.STACK, null);
        reminderText = conspireTargets.getReminder();
        this.conspireCost = new OptionalAdditionalCostImpl(
                keywordText, " ", reminderText,
                new TapTargetCost(new TargetControlledPermanent(2, filter))
        );
        this.conspireCost.setCostType(VariableCostType.ADDITIONAL);
        addSubAbility(new ConspireTriggeredAbility());
    }

    public ConspireAbility(final ConspireAbility ability) {
        super(ability);
        this.conspireCost = ability.conspireCost.copy();
        this.reminderText = ability.reminderText;
    }

    @Override
    public ConspireAbility copy() {
        return new ConspireAbility(this);
    }

    @Override
    public void addCost(Cost cost) {
        if (conspireCost != null) {
            ((Costs<Cost>) conspireCost).add(cost);
        }
    }

    @Override
    public boolean isActivated() {
        throw new UnsupportedOperationException("Use ConspireAbility.isActivated(Ability ability, Game game) method instead!");
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }
        Player player = game.getPlayer(getControllerId());
        if (player == null) {
            return;
        }
        // AI supports conspire
        if (!conspireCost.canPay(ability, this, getControllerId(), game)
                || !player.chooseUse(Outcome.Benefit, "Pay " + conspireCost.getText(false) + " ?", ability, game)) {
            return;
        }
        for (Iterator<Cost> it = ((Costs<Cost>) conspireCost).iterator(); it.hasNext(); ) {
            Cost cost = (Cost) it.next();
            if (cost instanceof ManaCostsImpl) {
                ability.getManaCostsToPay().add((ManaCostsImpl<?>) cost.copy());
            } else {
                ability.getCosts().add(cost.copy());
            }
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (conspireCost != null) {
            sb.append(conspireCost.getText(false));
            sb.append(' ').append(conspireCost.getReminderText());
        }
        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        return conspireCost != null ? conspireCost.getCastSuffixMessage(0) : "";
    }
}

class ConspireTriggeredAbility extends CastSourceTriggeredAbility {

    public ConspireTriggeredAbility() {
        super(new CopySourceSpellEffect(), false);
        this.setRuleVisible(false);
    }

    private ConspireTriggeredAbility(final ConspireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ConspireTriggeredAbility copy() {
        return new ConspireTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getSourceId());
        return spell != null && spell
                .getSpellAbility()
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("tappedPermanents"))
                .filter(Objects::nonNull)
                .map(x -> (List<Permanent>) x)
                .flatMap(Collection::stream)
                .count() >= 2;
    }

    @Override
    public String getRule() {
        return "When you pay the conspire costs, copy it and you may choose a new target for the copy.";
    }
}
