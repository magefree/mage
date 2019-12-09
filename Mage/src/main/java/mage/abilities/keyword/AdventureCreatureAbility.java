package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * 702.32. Flashback
 *
 * 702.32a. Flashback appears on some instants and sorceries. It represents two
 * static abilities: one that functions while the card is in a player‘s
 * graveyard and the other that functions while the card is on the stack.
 * Flashback [cost] means, "You may cast this card from your graveyard by paying
 * [cost] rather than paying its mana cost" and, "If the flashback cost was
 * paid, exile this card instead of putting it anywhere else any time it would
 * leave the stack." Casting a spell using its flashback ability follows the
 * rules for paying alternative costs in rules 601.2b and 601.2e–g.
 *
 * @author phulin
 */
public class AdventureCreatureAbility extends SpellAbility {

    private String abilityName;
    private SpellAbility spellAbilityToResolve;

    public AdventureCreatureAbility(Cost cost) {
        super(null, "", Zone.EXILED, SpellAbilityType.BASE, SpellAbilityCastMode.NORMAL);
        this.setAdditionalCostsRuleVisible(false);
        this.name = "Cast creature " + cost.getText();
        this.addCost(cost);
        this.timing = TimingRule.SORCERY;
    }

    public AdventureCreatureAbility(final AdventureCreatureAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
        this.abilityName = ability.abilityName;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                // Card must be in the exile zone, and it must have been cast as an adventure.
                if (game.getState().getZone(card.getId()) != Zone.EXILED) {
                    return ActivationStatus.getFalse();
                }
                // FIXME: Make sure it was cast as an adventure.
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public Costs<Cost> getCosts() {
        if (spellAbilityToResolve == null) {
            return super.getCosts();
        }
        return spellAbilityToResolve.getCosts();
    }

    @Override
    public AdventureCreatureAbility copy() {
        return new AdventureCreatureAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder("Cast from Adventure");
        /*if (!costs.isEmpty()) {
            sbRule.append("&mdash;");
        } else {
            sbRule.append(' ');
        }
        if (!manaCosts.isEmpty()) {
            sbRule.append(manaCosts.getText());
        }
        if (!costs.isEmpty()) {
            if (!manaCosts.isEmpty()) {
                sbRule.append(", ");
            }
            sbRule.append(costs.getText());
            sbRule.append('.');
        }
        if (abilityName != null) {
            sbRule.append(' ');
            sbRule.append(abilityName);
        }
        sbRule.append(" <i>(You may cast this card from your graveyard for its flashback cost. Then exile it.)</i>");*/
        return sbRule.toString();
    }

    /**
     * Used for split card in PlayerImpl method:
     * getOtherUseableActivatedAbilities
     *
     * @param abilityName
     */
    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

}
