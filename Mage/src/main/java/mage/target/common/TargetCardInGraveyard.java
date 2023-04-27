package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInGraveyard extends TargetCard {

    private static final FilterCard defaultFilter = new FilterCard("card from a graveyard");

    public TargetCardInGraveyard() {
        this(1, 1);
    }

    public TargetCardInGraveyard(FilterCard filter) {
        this(1, filter);
    }

    public TargetCardInGraveyard(int numTargets, FilterCard filter) {
        this(numTargets, numTargets, filter, false);
    }

    public TargetCardInGraveyard(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, defaultFilter, false);
    }
    
    public TargetCardInGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        this(minNumTargets, maxNumTargets, filter, false);
    }

    public TargetCardInGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter);
        this.setNotTarget(notTarget);
    }

    public TargetCardInGraveyard(final TargetCardInGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        return card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD && filter.match(card, game);
    }

    @Override
    public TargetCardInGraveyard copy() {
        return new TargetCardInGraveyard(this);
    }
}
