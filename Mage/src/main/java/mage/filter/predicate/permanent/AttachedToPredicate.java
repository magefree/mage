package mage.filter.predicate.permanent;

import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author LoneFox
 */
public class AttachedToPredicate implements ObjectSourcePlayerPredicate<Permanent> {

    private final FilterPermanent filter;

    public AttachedToPredicate(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input)
                .map(ObjectSourcePlayer::getObject)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .map(permanent -> filter.match(permanent, input.getPlayerId(), input.getSource(), game))
                .orElse(false);
    }

    @Override
    public String toString() {
        return "attached to " + filter.getMessage();
    }

}
