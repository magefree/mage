package mage.target.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author xenohedron
 */
public class TargetCardAndOrCardTypeInLibrary extends TargetCardInLibrary {

    private static FilterCard makeFilter(CardType firstType, CardType secondType) {
        FilterCard filter = new FilterCard(
                CardUtil.addArticle(CardUtil.getTextWithFirstCharLowerCase(firstType.toString()))
                + " card and/or "
                + CardUtil.addArticle(CardUtil.getTextWithFirstCharLowerCase(secondType.toString()))
                + " card");
        filter.add(Predicates.or(
                firstType.getPredicate(), secondType.getPredicate()
        ));
        return filter;
    }

    private final CardTypeAssignment assignment;

    /**
     * a [firstType] card and/or a [secondType] card
     */
    public TargetCardAndOrCardTypeInLibrary(CardType firstType, CardType secondType) {
        super(0, 2, makeFilter(firstType, secondType));
        this.assignment = new CardTypeAssignment(firstType, secondType);
    }

    protected TargetCardAndOrCardTypeInLibrary(final TargetCardAndOrCardTypeInLibrary target) {
        super(target);
        this.assignment = target.assignment;
    }

    @Override
    public TargetCardAndOrCardTypeInLibrary copy() {
        return new TargetCardAndOrCardTypeInLibrary(this);
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
