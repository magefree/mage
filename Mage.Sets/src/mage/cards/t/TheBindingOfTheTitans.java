package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheBindingOfTheTitans extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or land card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public TheBindingOfTheTitans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Each player puts the top three cards of their library into their graveyard.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new MillCardsEachPlayerEffect(
                        3, TargetController.ANY
                )
        );

        // II — Exile up to two target cards from graveyards. For each creature card exiled this way, you gain 1 life.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II, new TheBindingOfTheTitansEffect(),
                new TargetCardInGraveyard(0, 2, StaticFilters.FILTER_CARD)
        );

        // III — Return target creature or land card from your graveyard to your hand.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new ReturnFromGraveyardToHandTargetEffect(), new TargetCardInYourGraveyard(filter)
        );
        this.addAbility(sagaAbility);
    }

    private TheBindingOfTheTitans(final TheBindingOfTheTitans card) {
        super(card);
    }

    @Override
    public TheBindingOfTheTitans copy() {
        return new TheBindingOfTheTitans(this);
    }
}

class TheBindingOfTheTitansEffect extends OneShotEffect {

    TheBindingOfTheTitansEffect() {
        super(Outcome.Benefit);
        staticText = "Exile up to two target cards from graveyards. " +
                "For each creature card exiled this way, you gain 1 life.";
    }

    private TheBindingOfTheTitansEffect(final TheBindingOfTheTitansEffect effect) {
        super(effect);
    }

    @Override
    public TheBindingOfTheTitansEffect copy() {
        return new TheBindingOfTheTitansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(
                source.getTargets()
                        .stream()
                        .map(Target::getTargets)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
        player.moveCards(cards, Zone.EXILED, source, game);
        int lifeToGain = cards
                .getCards(game)
                .stream()
                .filter(card -> card.isCreature(game))
                .map(Card::getId)
                .map(game.getState()::getZone)
                .map(Zone.EXILED::equals)
                .mapToInt(b -> b ? 1 : 0)
                .sum();
        return player.gainLife(lifeToGain, game, source) > 0;
    }
}