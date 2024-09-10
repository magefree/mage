package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * @author Xanderhall
 */
public enum EnchantedBySourceControllerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(p -> p.hasSubtype(SubType.AURA, game) && p.isControlledBy(input.getPlayerId()));
    }

    @Override
    public String toString() {
        return "Enchanted by source";
    }
}
