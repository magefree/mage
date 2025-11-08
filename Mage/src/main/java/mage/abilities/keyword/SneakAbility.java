package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.abilities.SpellAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.Set;

/**
 * @author TheElk801
 */
public class SneakAbility extends SpellAbility {

    public static final String SNEAK_ACTIVATION_VALUE_KEY = "sneakActivation";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("unblocked attacker you control");

    static {
        filter.add(UnblockedPredicate.instance);
        filter.add(AttackingPredicate.instance);
    }

    public SneakAbility(Card card, String manaString) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with Sneak");
        timing = TimingRule.INSTANT;
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaString));
        this.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));

        this.setRuleAtTheTop(true);
    }

    protected SneakAbility(final SneakAbility ability) {
        super(ability);
    }

    @Override
    public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
        if (!super.activate(game, allowedIdentifiers, noMana)
                || game.getStep().getType() != PhaseStep.DECLARE_BLOCKERS) {
            return false;
        }
        this.setCostsTag(SNEAK_ACTIVATION_VALUE_KEY, null);
        return true;
    }

    @Override
    public SneakAbility copy() {
        return new SneakAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Sneak ");
        sb.append(getManaCosts().getText());
        sb.append(" <i>(You may cast this spell for ");
        sb.append(getManaCosts().getText());
        sb.append(" if you also return an unblocked attacker you control to hand during the declare blockers step.)</i>");
        return sb.toString();
    }
}
