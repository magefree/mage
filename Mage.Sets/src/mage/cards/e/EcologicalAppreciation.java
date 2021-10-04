package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author htrajan
 */
public final class EcologicalAppreciation extends CardImpl {

    public EcologicalAppreciation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{G}");

        // Search your library and graveyard for up to four creature cards with different names that each have mana value X or less and reveal them. An opponent chooses two of those cards. Shuffle the chosen cards into your library and put the rest onto the battlefield. Exile Ecological Appreciation.
        this.getSpellAbility().addEffect(new EcologicalAppreciationEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private EcologicalAppreciation(final EcologicalAppreciation card) {
        super(card);
    }

    @Override
    public EcologicalAppreciation copy() {
        return new EcologicalAppreciation(this);
    }
}

class EcologicalAppreciationEffect extends OneShotEffect {

    EcologicalAppreciationEffect() {
        super(Outcome.Benefit);
        staticText = "search your library and graveyard for up to four creature cards with different names that each have mana value X or less and reveal them. An opponent chooses two of those cards. Shuffle the chosen cards into your library and put the rest onto the battlefield";
    }

    private EcologicalAppreciationEffect(final EcologicalAppreciationEffect effect) {
        super(effect);
    }

    @Override
    public EcologicalAppreciationEffect copy() {
        return new EcologicalAppreciationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        TargetCard targetCardsInLibrary = new TargetCardInLibrary(0, 4, filter) {
            @Override
            public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
                if (!super.canTarget(playerId, id, source, game)) {
                    return false;
                }
                Card card = game.getCard(id);
                Set<Card> disallowedCards = this.getTargets().stream()
                    .map(game::getCard)
                    .collect(Collectors.toSet());
                return isValidTarget(card, disallowedCards);
            }
        };
        targetCardsInLibrary.setNotTarget(true);
        targetCardsInLibrary.withChooseHint("Step 1 of 2: Search library");
        player.choose(Outcome.PutCreatureInPlay, new CardsImpl(player.getLibrary().getCards(game)), targetCardsInLibrary, game);
        Cards cards = new CardsImpl(targetCardsInLibrary.getTargets());

        boolean status = !cards.isEmpty();

        if (status) {
            int remainingCards = 4 - cards.size();
            if (remainingCards > 0) {
                TargetCard targetCardsInGY = new TargetCardInYourGraveyard(0, remainingCards, filter) {
                    @Override
                    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
                        if (!super.canTarget(playerId, id, source, game)) {
                            return false;
                        }
                        Card card = game.getCard(id);
                        Set<Card> disallowedCards = this.getTargets().stream()
                            .map(game::getCard)
                            .collect(Collectors.toSet());
                        Set<Card> checkList = new HashSet<>();
                        checkList.addAll(disallowedCards);
                        checkList.addAll(cards.getCards(game));
                        return isValidTarget(card, checkList);
                    }
                };
                targetCardsInGY.setNotTarget(true);
                targetCardsInGY.withChooseHint("Step 2 of 2: Search graveyard");
                player.choose(Outcome.PutCreatureInPlay, new CardsImpl(player.getGraveyard().getCards(game)), targetCardsInGY, game);
                cards.addAll(targetCardsInGY.getTargets());
            }

            TargetOpponent targetOpponent = new TargetOpponent();
            targetOpponent.setNotTarget(true);
            player.choose(outcome, targetOpponent, source.getSourceId(), game);
            Player opponent = game.getPlayer(targetOpponent.getFirstTarget());

            if (opponent == null) {
                status = false;
            }

            if (status) {
                TargetCard chosenCards = new TargetCard(2, Zone.ALL, StaticFilters.FILTER_CARD);
                chosenCards.setNotTarget(true);
                opponent.choose(outcome, cards, chosenCards, game);
                Cards toShuffle = new CardsImpl(chosenCards.getTargets().stream()
                    .map(game::getCard)
                    .collect(Collectors.toList()));

                player.putCardsOnTopOfLibrary(toShuffle, game, source, false);
                cards.removeAll(toShuffle);

                player.moveCards(cards, Zone.BATTLEFIELD, source, game);
            }
        }

        player.shuffleLibrary(source, game);

        return status;
    }

    private boolean isValidTarget(Card target, Set<Card> disallowedCards) {
        return target != null &&
            disallowedCards.stream()
                .filter(Objects::nonNull)
                .map(MageObject::getName)
                .noneMatch(name -> CardUtil.haveSameNames(name, target.getName()));
    }
}