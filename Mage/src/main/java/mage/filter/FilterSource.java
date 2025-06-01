
package mage.filter;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public class FilterSource extends FilterObject<MageObject> {

    protected final List<ObjectSourcePlayerPredicate<MageObject>> extraPredicates = new ArrayList<>();

    public FilterSource() {
        super("source");
    }

    public FilterSource(String name) {
        super(name);
    }

    private FilterSource(final FilterSource filter) {
        super(filter);
        this.extraPredicates.addAll(filter.extraPredicates);
    }

    @Override
    public FilterSource copy() {
        return new FilterSource(this);
    }

    public FilterSource add(ObjectSourcePlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }

        // verify check -- make sure predicates work with all 3 Class that could be a Source
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, Permanent.class, Card.class, StackObject.class, CommandObject.class);

        extraPredicates.add(predicate);
        return this;
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent
                || object instanceof Card
                || object instanceof StackObject
                || object instanceof CommandObject;
    }

    public boolean match(MageObject object, UUID sourceControllerId, Ability source, Game game) {
        if (!this.match(object, game)) {
            return false;
        }
        ObjectSourcePlayer<MageObject> osp = new ObjectSourcePlayer<>(object, sourceControllerId, source);
        return extraPredicates.stream().allMatch(p -> p.apply(osp, game));
    }

    @Override
    public List<Predicate> getExtraPredicates() {
        return new ArrayList<>(extraPredicates);
    }
}
