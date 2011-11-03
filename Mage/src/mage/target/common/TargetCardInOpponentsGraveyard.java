package mage.target.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetCard;

import java.util.UUID;


public class TargetCardInOpponentsGraveyard extends TargetCard<TargetCardInOpponentsGraveyard> {

    public TargetCardInOpponentsGraveyard(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInOpponentsGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Constants.Zone.GRAVEYARD, filter);
        this.targetName = filter.getMessage();
    }

    public TargetCardInOpponentsGraveyard(final TargetCardInOpponentsGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Constants.Zone.GRAVEYARD) {
            if (game.getOpponents(source.getControllerId()).contains(card.getOwnerId())) {
                return filter.match(card);
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
