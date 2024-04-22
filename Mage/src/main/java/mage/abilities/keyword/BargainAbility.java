
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.hint.common.BargainCostWasPaidHint;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * Written before ruling was clarified. Feel free to put the ruling once it gets there.
 * <p>
 * Bargain is a keyword static ability that adds an optional additional cost.
 * <p>
 * Bargain means "You may sacrifice an artifact, enchantment, or token as you cast this spell".
 * <p>
 * If a spell bargain cost is paid, the spell or the permanent it becomes is bargained.
 *
 * @author Susucr
 */
public class BargainAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final FilterControlledPermanent bargainFilter = new FilterControlledPermanent("an artifact, enchantment, or token");
    private static final String promptString = "Bargain? (To Bargain, sacrifice an artifact, enchantment, or token)";
    private static final String keywordText = "Bargain";
    private static final String reminderText = "You may sacrifice an artifact, enchantment, or token as you cast this spell.";
    private final String rule;

    public static final String BARGAIN_ACTIVATION_VALUE_KEY = "bargainActivation";

    protected OptionalAdditionalCost additionalCost;

    static {
        bargainFilter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                TokenPredicate.TRUE
        ));
    }

    public static OptionalAdditionalCost makeBargainCost(){
        OptionalAdditionalCost cost = new OptionalAdditionalCostImpl(keywordText, reminderText, new SacrificeTargetCost(bargainFilter));
        cost.setRepeatable(false);
        return cost;
    }

    public BargainAbility() {
        super(Zone.STACK, null);
        this.additionalCost = makeBargainCost();
        this.rule = additionalCost.getName() + ' ' + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
        this.addHint(BargainCostWasPaidHint.instance);
    }

    private BargainAbility(final BargainAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
    }

    @Override
    public BargainAbility copy() {
        return new BargainAbility(this);
    }

    public void resetBargain() {
        if (additionalCost != null) {
            additionalCost.reset();
        }
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

        this.resetBargain();
        boolean canPay = additionalCost.canPay(ability, this, ability.getControllerId(), game);
        if (!canPay || !player.chooseUse(Outcome.Sacrifice, promptString, ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        ability.setCostsTag(BARGAIN_ACTIVATION_VALUE_KEY, null);
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost.getCastSuffixMessage(0);
    }

    @Override
    public String getRule() {
        return rule;
    }
}
