package mage.filter;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

public class FilterPermanentThisOrAnother extends FilterPermanent {

    final FilterPermanent otherFilter;
    final boolean onlyControlled;

    public FilterPermanentThisOrAnother(FilterPermanent otherFilter, boolean onlyControlled) {
        this(otherFilter, onlyControlled, generateFilterMessage(otherFilter));
    }
    public FilterPermanentThisOrAnother(FilterPermanent otherFilter, boolean onlyControlled, String name) {
        super(name);
        this.otherFilter = otherFilter;
        this.onlyControlled = onlyControlled;
    }
    @Override
    public boolean match(Permanent permanent, UUID playerId, Ability source, Game game) {
        if (!super.match(permanent, playerId, source, game)) {
            return false;
        }
        if (onlyControlled && !permanent.isControlledBy(source.getControllerId())) {
            return false;
        }
        if (permanent.getId().equals(source.getSourceId())) {
            return true;
        } else {
            return otherFilter.match(permanent, playerId, source, game);
        }
    }

    private FilterPermanentThisOrAnother(FilterPermanentThisOrAnother filter) {
        super(filter);
        this.otherFilter = filter.otherFilter.copy();
        this.onlyControlled = filter.onlyControlled;
    }

    @Override
    public FilterPermanentThisOrAnother copy() {
        return new FilterPermanentThisOrAnother(this);
    }

    protected static String generateFilterMessage(FilterPermanent otherFilter) {
        // Remove the indefinite article from the beginning of the message:
        String otherFilterMessage = otherFilter.getMessage();
        if (otherFilterMessage.startsWith("a ")) {
            otherFilterMessage = otherFilterMessage.substring(2);
        } else if (otherFilterMessage.startsWith("an ")) {
            otherFilterMessage = otherFilterMessage.substring(3);
        }

        return "{this} or another " + otherFilterMessage;
    }
}
