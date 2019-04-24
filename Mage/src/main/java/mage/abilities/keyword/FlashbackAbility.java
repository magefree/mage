package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

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
 * @author nantuko
 */
public class FlashbackAbility extends SpellAbility {

    private String abilityName;
    private SpellAbility spellAbilityToResolve;

    public FlashbackAbility(Cost cost, TimingRule timingRule) {
        super(null, "", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.FLASHBACK);
        this.setAdditionalCostsRuleVisible(false);
        this.name = "Flashback " + cost.getText();
        this.addCost(cost);
        this.timing = timingRule;
    }

    public FlashbackAbility(final FlashbackAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
        this.abilityName = ability.abilityName;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        ActivationStatus activationStatus = super.canActivate(playerId, game);
        if (activationStatus.canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                // Cards with no Mana Costs cant't be flashbacked (e.g. Ancestral Vision)
                if (card.getManaCost().isEmpty()) {
                    return ActivationStatus.getFalse();
                }
                // Flashback can never cast a split card by Fuse, because Fuse only works from hand
                if (card.isSplitCard()) {
                    if (((SplitCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        return ((SplitCard) card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((SplitCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        return ((SplitCard) card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
                    }
                }
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return activationStatus;
    }

    @Override
    public SpellAbility getSpellAbilityToResolve(Game game) {
        Card card = game.getCard(getSourceId());
        if (card != null) {
            if (spellAbilityToResolve == null) {
                SpellAbility spellAbilityCopy = null;
                if (card.isSplitCard()) {
                    if (((SplitCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((SplitCard) card).getLeftHalfCard().getSpellAbility().copy();
                    } else if (((SplitCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((SplitCard) card).getRightHalfCard().getSpellAbility().copy();
                    }
                } else {
                    spellAbilityCopy = card.getSpellAbility().copy();
                }
                if (spellAbilityCopy == null) {
                    return null;
                }
                spellAbilityCopy.setId(this.getId());
                spellAbilityCopy.getManaCosts().clear();
                spellAbilityCopy.getManaCostsToPay().clear();
                spellAbilityCopy.getCosts().addAll(this.getCosts().copy());
                spellAbilityCopy.addCost(this.getManaCosts().copy());
                spellAbilityCopy.setSpellAbilityCastMode(this.getSpellAbilityCastMode());
                spellAbilityToResolve = spellAbilityCopy;
                ContinuousEffect effect = new FlashbackReplacementEffect();
                effect.setTargetPointer(new FixedTarget(getSourceId(), game.getState().getZoneChangeCounter(getSourceId())));
                game.addEffect(effect, this);
            }
        }
        return spellAbilityToResolve;
    }

    @Override
    public Costs<Cost> getCosts() {
        if (spellAbilityToResolve == null) {
            return super.getCosts();
        }
        return spellAbilityToResolve.getCosts();
    }

    @Override
    public FlashbackAbility copy() {
        return new FlashbackAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder("Flashback");
        if (!costs.isEmpty()) {
            sbRule.append(" - ");
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
        sbRule.append(" <i>(You may cast this card from your graveyard for its flashback cost. Then exile it.)</i>");
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

class FlashbackReplacementEffect extends ReplacementEffectImpl {

    public FlashbackReplacementEffect() {
        super(Duration.OneUse, Outcome.Exile);
        staticText = "(If the flashback cost was paid, exile this card instead of putting it anywhere else any time it would leave the stack)";
    }

    public FlashbackReplacementEffect(final FlashbackReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FlashbackReplacementEffect copy() {
        return new FlashbackReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                discard();
                return controller.moveCards(
                        card, Zone.EXILED, source, game, false, false, false, event.getAppliedEffects());
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.STACK
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED) {

            int zcc = game.getState().getZoneChangeCounter(source.getSourceId());
            if (((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == zcc) {
                return true;
            }

        }
        return false;
    }
}
