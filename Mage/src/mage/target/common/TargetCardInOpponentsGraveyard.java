package mage.target.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetCard;

import java.util.UUID;


public class TargetCardInOpponentsGraveyard extends TargetCard<TargetCardInOpponentsGraveyard> {

    protected boolean allFromOneOpponent;

    public TargetCardInOpponentsGraveyard(FilterCard filter) {
        this(1, 1, filter, false);
    }

    public TargetCardInOpponentsGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        this(minNumTargets, maxNumTargets, filter, false);
    }

    public TargetCardInOpponentsGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter, boolean allFromOneOpponent) {
        super(minNumTargets, maxNumTargets, Constants.Zone.GRAVEYARD, filter);
        this.targetName = filter.getMessage();
        this.allFromOneOpponent = allFromOneOpponent;
    }

    public TargetCardInOpponentsGraveyard(final TargetCardInOpponentsGraveyard target) {
        super(target);
        this.allFromOneOpponent = target.allFromOneOpponent;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Constants.Zone.GRAVEYARD) {
            if (game.getOpponents(source.getControllerId()).contains(card.getOwnerId())) {
                if (allFromOneOpponent && !targets.isEmpty()) {
                    Card firstCard = game.getCard((UUID)targets.keySet().iterator().next());
                    if (firstCard != null && !card.getOwnerId().equals(firstCard.getOwnerId())) {
                        return false;
                    }
                }
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return true;
    }

    @Override
    public TargetCardInOpponentsGraveyard copy() {
        return new TargetCardInOpponentsGraveyard(this);
    }
}
