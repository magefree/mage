package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * @author grimreap124
 */
public enum LegendaryEquippedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .allMatch(attachment -> attachment.hasSubtype(SubType.EQUIPMENT, game) && attachment.isLegendary(game));

    }

    @Override
    public String toString() {
        return "legendary Equipment attached";
    }
}
