package mage.target.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PredicateCardAssignment;
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

import java.util.HashSet;
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

    /**
     * a [firstType] card and/or a [secondType] card
     */
    protected TargetCardAndOrCardInLibrary(Predicate<? super Card> firstPredicate, Predicate<? super Card> secondPredicate, String filterText) {
        super(0, 2, makeFilter(firstPredicate, secondPredicate, filterText));
        this.assignment = new PredicateCardAssignment(firstPredicate, secondPredicate);
    }

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

    protected TargetCardAndOrCardInLibrary(final TargetCardAndOrCardInLibrary target) {
        super(target);
        this.assignment = target.assignment;
    }

    @Override
    public TargetCardAndOrCardInLibrary copy() {
        return new TargetCardAndOrCardInLibrary(this);
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
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        // assuming max targets = 2, need to expand this code if not
        Card card = game.getCard(this.getFirstTarget());
        if (card == null) {
            return possibleTargets; // no further restriction if no target yet chosen
        }
        Cards cards = new CardsImpl(card);
        if (assignment.getRoleCount(cards, game) == 2) {
            // if the first chosen target is both types, no further restriction
            return possibleTargets;
        }
        Set<UUID> leftPossibleTargets = new HashSet<>();
        for (UUID possibleId : possibleTargets) {
            Card possibleCard = game.getCard(possibleId);
            Cards checkCards = cards.copy();
            checkCards.add(possibleCard);
            if (assignment.getRoleCount(checkCards, game) == 2) {
                // if the possible target and the existing target have both types, it's legal
                // but this prevents the case of both targets with the same type
                leftPossibleTargets.add(possibleId);
            }
        }
        return leftPossibleTargets;
    }

    @Override
    public String getDescription() {
        return filter.getMessage();
    }
}
