package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Xanderhall
 */
public class EnchantedByPlayerPredicate implements Predicate<Permanent> {
    private final UUID playerId;
    
    public EnchantedByPlayerPredicate(UUID playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(p -> p.hasSubtype(SubType.AURA, game) && p.isControlledBy(playerId));
    }

    @Override
    public String toString() {
        return "Enchanted by player";
    }

}
