package mage.target.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author xenohedron
 */
public class TargetCardAndOrSubTypeInLibrary extends TargetCardInLibrary {

    private static FilterCard makeFilter(SubType firstType, SubType secondType) {
        FilterCard filter = new FilterCard(CardUtil.addArticle(firstType.toString()) + " card and/or "
                + CardUtil.addArticle(secondType.toString()) + " card");
        filter.add(Predicates.or(
                firstType.getPredicate(), secondType.getPredicate()
        ));
        return filter;
    }

    private final SubTypeAssignment assignment;

    /**
     * a [firstType] card and/or a [secondType] card
     */
    public TargetCardAndOrSubTypeInLibrary(SubType firstType, SubType secondType) {
        super(0, 2, makeFilter(firstType, secondType));
        this.assignment = new SubTypeAssignment(firstType, secondType);
    }

    protected TargetCardAndOrSubTypeInLibrary(final TargetCardAndOrSubTypeInLibrary target) {
        super(target);
        this.assignment = target.assignment;
    }

    @Override
    public TargetCardAndOrSubTypeInLibrary copy() {
        return new TargetCardAndOrSubTypeInLibrary(this);
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
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return assignment.getRoleCount(cards, game) >= cards.size();
    }

    @Override
    public String getDescription() {
        return filter.getMessage();
    }
}
