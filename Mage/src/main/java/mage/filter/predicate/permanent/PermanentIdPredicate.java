package mage.filter.predicate.permanent;

import mage.MageItem;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class PermanentIdPredicate implements Predicate<Permanent> {

    private final UUID permanentId;

    public PermanentIdPredicate(UUID permanentId) {
        this.permanentId = permanentId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getId().equals(permanentId);
    }

    @Override
    public String toString() {
        return "PermanentId(" + permanentId + ')';
    }

    public static Predicate<Permanent> makeCompoundPredicate(Collection<Permanent> permanents) {
        return Predicates.or(
                permanents
                        .stream()
                        .map(MageItem::getId)
                        .map(PermanentIdPredicate::new)
                        .collect(Collectors.toSet())
        );
    }
}
