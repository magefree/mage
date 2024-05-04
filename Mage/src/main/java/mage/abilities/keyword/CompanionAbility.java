package mage.abilities.keyword;

import mage.abilities.SpecialAction;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.CompanionEffect;
import mage.cards.Card;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;

import java.util.Set;
import java.util.UUID;

/**
 * Allows card to be companion
 *
 * @author emerald000
 */
public class CompanionAbility extends SpecialAction {

    private final CompanionCondition companionCondition;

    public CompanionAbility(CompanionCondition companionCondition) {
        super(Zone.OUTSIDE);
        this.companionCondition = companionCondition;
        this.addCost(new GenericManaCost(3));
        this.addEffect(new CompanionEffect());
        this.setTiming(TimingRule.SORCERY);
    }

    private CompanionAbility(final CompanionAbility ability) {
        super(ability);
        this.companionCondition = ability.companionCondition;
    }

    @Override
    public CompanionAbility copy() {
        return new CompanionAbility(this);
    }

    @Override
    public String getRule() {
        return "Companion &mdash; " + companionCondition.getRule();
    }

    public final boolean isLegal(Set<Card> cards, int minimumDeckSize) {
        return companionCondition.isLegal(cards, minimumDeckSize);
    }

    public final String getLegalRule() {
        return companionCondition.getRule();
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // Check that the card is actually a companion.
        Card card = game.getState().getCompanion().getCard(getSourceId(), game);
        return card != null
                ? super.canActivate(playerId, game)
                : ActivationStatus.getFalse();
    }
}
