package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.MageObjectReference;
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
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * 702.190a
 * Sneak is a keyword that represents a static ability that functions while the spell with sneak is on the stack.
 * “Sneak [cost]” means “Any time you could cast an instant during your declare blockers step, you may cast this spell
 * by paying [cost] and returning an unblocked creature you control to its owner’s hand rather than paying this
 * spell’s mana cost.”
 * <p>
 * 702.190b
 * A permanent spell whose sneak cost was paid enters the battlefield tapped and attacking (see rule 506.3a).
 * It will be attacking the same player, planeswalker, or battle as the creature that was returned to its owner’s hand
 * to pay the sneak cost of the spell that became that permanent.
 *
 * @author TheElk801, JayDi85
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
        if (card.isInstantOrSorcery() && card.getSpellAbility().getEffects().isEmpty()) {
            // sneak copies the main spell ability, so it must be added after all spell's effects
            throw new IllegalArgumentException("Wrong code usage: sneak ability must be added after spell's effects - " + card.getName());
        }
        this.newId();
        this.setCardName(card.getName() + " with Sneak");
        timing = TimingRule.INSTANT;
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaString));
        this.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)).setText(""));

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
        // remember the returned creature's defender (702.190b)
        this.setCostsTag(SNEAK_ACTIVATION_VALUE_KEY, findReturnedCreatureDefender());
        return true;
    }

    private MageObjectReference findReturnedCreatureDefender() {
        return getCosts().stream()
                .filter(ReturnToHandChosenControlledPermanentCost.class::isInstance)
                .map(ReturnToHandChosenControlledPermanentCost.class::cast)
                .flatMap(cost -> cost.getPermanents().stream())
                .map(Permanent::getAttacking)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    /**
     * Defenders for the permanent the spell becomes (702.190b), use it on the spell's resolve
     *
     * @return null if sneak cost wasn't paid, empty if the returned creature's defender is gone
     */
    public static Set<UUID> getSneakDefenders(Spell spell, Game game) {
        Map<String, Object> costsTags = CardUtil.getSourceCostsTagsMap(game, spell.getSpellAbility());
        if (costsTags == null || !costsTags.containsKey(SNEAK_ACTIVATION_VALUE_KEY)) {
            return null;
        }
        MageObjectReference defender = (MageObjectReference) costsTags.get(SNEAK_ACTIVATION_VALUE_KEY);
        if (defender == null
                || (game.getPlayer(defender.getSourceId()) == null && defender.getPermanent(game) == null)) {
            // the returned creature was attacking nothing, or its planeswalker or battle left the battlefield (506.4c)
            return Collections.emptySet();
        }
        return Collections.singleton(defender.getSourceId());
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
