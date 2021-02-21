package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * @author LevelX2
 */
public enum EquippedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(attachment -> attachment.hasSubtype(SubType.EQUIPMENT, game));

    }

    @Override
    public String toString() {
        return "equipped";
    }
}
