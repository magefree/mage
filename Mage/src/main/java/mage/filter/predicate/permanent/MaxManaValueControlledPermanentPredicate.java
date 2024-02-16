package mage.filter.predicate.permanent;

import mage.MageObject;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * @author TheElk801
 */
public enum MaxManaValueControlledPermanentPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        int cmc = game.getBattlefield()
                .getActivePermanents(filter, input.getPlayerId(), input.getSource(), game)
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
        return input.getObject().getManaValue() >= cmc;
    }
}
