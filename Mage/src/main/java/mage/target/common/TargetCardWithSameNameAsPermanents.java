package mage.target.common;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.assignment.RoleAssignment;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class TargetCardWithSameNameAsPermanents extends TargetCardInLibrary {

    private static final class SameNameAsPermanentAssignment extends RoleAssignment<UUID> {

        public SameNameAsPermanentAssignment(UUID... uuids) {
            super(uuids);
        }

        @Override
        protected Set<UUID> makeSet(Card card, Game game) {
            return attributes
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .filter(permanent -> permanent.sharesName(card, game))
                    .map(MageItem::getId)
                    .collect(Collectors.toSet());
        }
    }

    private final SameNameAsPermanentAssignment assigner;
    private static final FilterCard defaultFilter = new FilterCard("cards with the same name");

    public TargetCardWithSameNameAsPermanents(Collection<UUID> uuids) {
        this(uuids, defaultFilter);
    }

    public TargetCardWithSameNameAsPermanents(Collection<UUID> uuids, FilterCard filter) {
        super(0, uuids.size(), filter);
        this.assigner = new SameNameAsPermanentAssignment(uuids.toArray(new UUID[]{}));
    }

    private TargetCardWithSameNameAsPermanents(final TargetCardWithSameNameAsPermanents target) {
        super(target);
        this.assigner = target.assigner;
    }

    @Override
    public TargetCardWithSameNameAsPermanents copy() {
        return new TargetCardWithSameNameAsPermanents(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return assigner.getRoleCount(cards, game) >= cards.size();
    }
}
