package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CalamityOfTheTitans extends CardImpl {

    private static final FilterCreatureCard filterCard = new FilterCreatureCard("colorless creature card from your hand");

    static {
        filterCard.add(ColorlessPredicate.instance);
    }

    public CalamityOfTheTitans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{C}{C}");


        // As an additional cost to cast this spell, reveal a colorless creature card from your hand.
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(
            new TargetCardInHand(filterCard)
        ));

        // Exile each creature and planeswalker with mana value less than the revealed card's mana value.
        this.getSpellAbility().addEffect(new CalamityOfTheTitansEffect());
    }

    private CalamityOfTheTitans(final CalamityOfTheTitans card) {
        super(card);
    }

    @Override
    public CalamityOfTheTitans copy() {
        return new CalamityOfTheTitans(this);
    }
}

class CalamityOfTheTitansEffect extends OneShotEffect {

    CalamityOfTheTitansEffect() {
        super(Outcome.Exile);
        staticText = "Exile each creature and planeswalker with mana value less than the revealed card's mana value";
    }

    private CalamityOfTheTitansEffect(final CalamityOfTheTitansEffect effect) {
        super(effect);
    }

    @Override
    public CalamityOfTheTitansEffect copy() {
        return new CalamityOfTheTitansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        if (cost == null) {
            return false;
        }
        Card card = cost.getRevealedCards().get(0);
        if (card == null) {
            return false;
        }
        int mv = card.getManaValue();

        FilterPermanent filter = new FilterPermanent();
        filter.add(
            Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
            )
        );
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, mv));

        return new ExileAllEffect(filter).apply(game, source);
    }
}