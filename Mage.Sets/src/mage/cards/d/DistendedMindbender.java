package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DistendedMindbender extends CardImpl {

    public DistendedMindbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Emerge {5}{B}{B}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{5}{B}{B}")));

        // When controller cast Distended Mindbender, target opponent reveals their hand. You choose from it a nonland card with converted mana cost 3 or less
        // and a card with converted mana cost 4 or greater. That player discards those cards.
        Ability ability = new CastSourceTriggeredAbility(new DistendedMindbenderEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public DistendedMindbender(final DistendedMindbender card) {
        super(card);
    }

    @Override
    public DistendedMindbender copy() {
        return new DistendedMindbender(this);
    }
}

class DistendedMindbenderEffect extends OneShotEffect {

    private static final FilterCard filterFourOrGreater = new FilterCard("a card from it with converted mana cost 4 or greater");
    private static final FilterCard filterThreeOrLess = new FilterCard("a nonland card from it with converted mana cost 3 or less");

    static {
        filterFourOrGreater.add(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, 3));
        filterThreeOrLess.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
        filterThreeOrLess.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public DistendedMindbenderEffect() {
        super(Outcome.Discard);
        this.staticText = "target opponent reveals their hand. You choose from it a nonland card with converted mana cost 3 or less and a card with "
                + "converted mana cost 4 or greater. That player discards those cards.";
    }

    public DistendedMindbenderEffect(final DistendedMindbenderEffect effect) {
        super(effect);
    }

    @Override
    public DistendedMindbenderEffect copy() {
        return new DistendedMindbenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent == null || controller == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        TargetCard targetThreeOrLess = new TargetCardInHand(1, filterThreeOrLess);
        TargetCard targetFourOrGreater = new TargetCardInHand(1, filterFourOrGreater);
        Cards toDiscard = new CardsImpl();
        if (controller.choose(Outcome.Benefit, opponent.getHand(), targetThreeOrLess, game)) {
            toDiscard.addAll(targetThreeOrLess.getTargets());
        }
        if (controller.choose(Outcome.Benefit, opponent.getHand(), targetFourOrGreater, game)) {
            toDiscard.addAll(targetFourOrGreater.getTargets());
        }
        opponent.discard(toDiscard, source, game);
        return true;
    }
}
