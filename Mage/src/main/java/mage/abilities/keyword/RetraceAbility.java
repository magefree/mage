package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.OptionalAdditionalSourceCosts;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * 702.81. Retrace
 * 702.81a Retrace is a static ability that functions while the card with retrace is in a player’s
 * graveyard. “Retrace” means “You may cast this card from your graveyard by discarding a land
 * card as an additional cost to cast it.” Casting a spell using its retrace ability follows the rules for
 * paying additional costs in rules 601.2b and 601.2f–h.
 *
 * @author LevelX2
 */
public class RetraceAbility extends StaticAbility {

    public RetraceAbility(Card card) {
        super(Zone.GRAVEYARD, null);
        this.addSubAbility(new RetraceSpellAbility(card));
        this.setRuleAtTheTop(true);
    }

    protected RetraceAbility(final RetraceAbility ability) {
        super(ability);
    }

    @Override
    public RetraceAbility copy() {
        return new RetraceAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Retrace <i>(You may cast this card from your graveyard by discarding a land card in addition to paying its other costs.)</i>";
    }

}

class RetraceSpellAbility extends SpellAbility implements OptionalAdditionalSourceCosts {

    private static final FilterLandCard filter = new FilterLandCard();
    protected OptionalAdditionalCost additionalCost;

    RetraceSpellAbility(Card card) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with retrace");
        this.zone = Zone.GRAVEYARD;
        this.setRuleVisible(false);
    }

    private RetraceSpellAbility(final RetraceSpellAbility ability) {
        super(ability);
        this.additionalCost = ability.additionalCost == null ? null : ability.additionalCost.copy();
    }

    @Override
    public RetraceSpellAbility copy() {
        return new RetraceSpellAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        ActivationStatus status = super.canActivate(playerId, game);
        if (!status.canActivate()) {
            return status;
        }
        Player player = game.getPlayer(playerId);
        return player != null && player.getHand().count(filter, game) > 0
                ? ActivationStatus.withoutApprovingObject(true)
                : ActivationStatus.getFalse();
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        additionalCost = new OptionalAdditionalCostImpl(
                "Retrace",
                "Retrace <i>(You may cast this card from your graveyard by discarding a land card in addition to paying its other costs.)</i>",
                new DiscardTargetCost(new TargetCardInHand(filter))
        );
        additionalCost.setCostType(VariableCostType.ADDITIONAL);
        additionalCost.activate();
        ability.addCost(additionalCost);
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost == null ? "" : additionalCost.getCastSuffixMessage(0);
    }
}
