package mage.target.targetpointer;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Targets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FilterAllPermanentsTargetPointer extends TargetPointerImpl {
    private final FilterPermanent filter;
    // null while the set is still live; holding a list is what makes this pointer a locked-in one
    private List<MageObjectReference> affectedObjectList = null;

    /**
     * Target pointer that always "targets" all permanents that match the given filter. The filter's
     * message becomes the target description, so give it the wording the rules text needs.
     */
    public FilterAllPermanentsTargetPointer(FilterPermanent filter) {
        super();
        this.filter = filter;
        setTargetDescription(filter.getMessage());
    }

    public FilterAllPermanentsTargetPointer(final FilterAllPermanentsTargetPointer other) {
        super(other);
        this.filter = other.filter;
        // a copy that re-derived this would break the "targets are locked in" contract
        this.affectedObjectList = other.affectedObjectList == null
                ? null
                : new ArrayList<>(other.affectedObjectList);
    }


    @Override
    public void fixTargets(Game game, Ability source) {
        affectedObjectList = game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(permanent -> new MageObjectReference(permanent, game))
                .collect(Collectors.toList());
    }

    @Override
    public void init(Game game, Ability source) {
        if (isInitialized()) {
            return;
        }
        setInitialized();
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        if (affectedObjectList != null) {
            return affectedObjectList.stream()
                    .filter(mor -> mor.zoneCounterIsCurrent(game))
                    .map(MageObjectReference::getSourceId)
                    .collect(Collectors.toList());
        }
        return game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(Permanent::getId)
                .collect(Collectors.toList());
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        throw new IllegalStateException("Attempted to get first target on FilterAllPermanentsTargetPointer (bad Effect usage)");
    }

    @Override
    public FilterAllPermanentsTargetPointer copy() {
        return new FilterAllPermanentsTargetPointer(this);
    }

    @Override
    public Permanent getFirstTargetPermanentOrLKI(Game game, Ability source) {
        throw new IllegalStateException("Attempted to get first target (or LKI) on FilterAllPermanentsTargetPointer (bad Effect usage)");
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean isPlural(Targets targets) {
        return true;
    }
}
