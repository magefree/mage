package mage.target.common;

import mage.abilities.Ability;
import mage.abilities.assignment.common.PredicateCardAssignment;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author xenohedron
 */
public class TargetCardAndOrCardInLibrary extends TargetCardInLibrary {

    private static FilterCard makeFilter(Predicate<? super Card> firstPredicate,
                                         Predicate<? super Card> secondPredicate,
                                         String filterText) {
        FilterCard filter = new FilterCard(filterText);
        filter.add(Predicates.or(
                firstPredicate, secondPredicate
        ));
        return filter;
    }

    private static String makeFilterText(String first, String second) {
        return CardUtil.addArticle(first) + " card and/or " + CardUtil.addArticle(second) + " card";
    }

    private final PredicateCardAssignment assignment;

    public TargetCardAndOrCardInLibrary(CardType firstType, CardType secondType) {
        this(firstType.getPredicate(), secondType.getPredicate(), makeFilterText(
                CardUtil.getTextWithFirstCharLowerCase(firstType.toString()),
                CardUtil.getTextWithFirstCharLowerCase(secondType.toString())));
    }

    public TargetCardAndOrCardInLibrary(String firstName, String secondName) {
        this(new NamePredicate(firstName), new NamePredicate(secondName), "a card named " + firstName + " and/or a card named " + secondName);
    }

    public TargetCardAndOrCardInLibrary(SubType firstType, SubType secondType) {
        this(firstType.getPredicate(), secondType.getPredicate(), makeFilterText(firstType.getDescription(), secondType.getDescription()));
    }

    /**
     * a [firstType] card and/or a [secondType] card
     */
    public TargetCardAndOrCardInLibrary(Predicate<? super Card> firstPredicate, Predicate<? super Card> secondPredicate, String filterText) {
        super(0, 2, makeFilter(firstPredicate, secondPredicate, filterText));
        this.assignment = new PredicateCardAssignment(firstPredicate, secondPredicate);
    }

    protected TargetCardAndOrCardInLibrary(final TargetCardAndOrCardInLibrary target) {
        super(target);
        this.assignment = target.assignment;
    }

    @Override
    public TargetCardAndOrCardInLibrary copy() {
        return new TargetCardAndOrCardInLibrary(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        // only valid roles
        Cards existingTargets = new CardsImpl(this.getTargets());
        possibleTargets.removeIf(id -> {
            Card card = game.getCard(id);
            if (card == null) {
                return true;
            }
            Cards newTargets = existingTargets.copy();
            newTargets.add(card);
            return assignment.getRoleCount(newTargets, game) < newTargets.size();
        });

        return possibleTargets;
    }

    @Override
    public String getDescription() {
        return filter.getMessage();
    }
}
