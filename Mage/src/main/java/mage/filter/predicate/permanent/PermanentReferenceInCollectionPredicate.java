
package mage.filter.predicate.permanent;

import mage.MageObjectReference;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 * @author notgreat
 */
public class PermanentReferenceInCollectionPredicate implements Predicate<Permanent> {
    private final Collection<MageObjectReference> references;

    public PermanentReferenceInCollectionPredicate(Collection<MageObjectReference> references) {
        //Note: it is assumed that the collection passed in isn't ever mutated afterwards
        this.references = references;
    }
    public PermanentReferenceInCollectionPredicate(Collection<Permanent> permanents, Game game) {
        this.references = permanents.stream().map((p) -> new MageObjectReference(p, game))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return (references.contains(new MageObjectReference(input, game)));
    }
}
