package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

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
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
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
        TargetCard targetCardsInLibrary = new EcologicalAppreciationTarget(Zone.LIBRARY, 4, xValue);
        targetCardsInLibrary.setNotTarget(true);
        boolean searched = player.choose(Outcome.PutCreatureInPlay, new CardsImpl(player.getLibrary().getCards(game)), targetCardsInLibrary, game);
        Cards cards = new CardsImpl(targetCardsInLibrary.getTargets());

        if (cards.isEmpty()) {
            if (searched) {
                player.shuffleLibrary(source, game);
            }
            return false;
        }

        int remainingCards = 4 - cards.size();
        if (remainingCards > 0) {
            TargetCard targetCardsInGY = new EcologicalAppreciationTarget(Zone.GRAVEYARD, remainingCards, xValue);
            targetCardsInGY.setNotTarget(true);
            player.choose(Outcome.PutCreatureInPlay, new CardsImpl(player.getGraveyard().getCards(game)), targetCardsInGY, game);
            cards.addAll(targetCardsInGY.getTargets());
        }

        TargetOpponent targetOpponent = new TargetOpponent();
        targetOpponent.setNotTarget(true);
        player.choose(outcome, targetOpponent, source.getSourceId(), game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            if (searched) {
                player.shuffleLibrary(source, game);
            }
            return false;
        }

        TargetCard chosenCards = new TargetCard(2, Zone.ALL, StaticFilters.FILTER_CARD);
        chosenCards.setNotTarget(true);
        opponent.choose(outcome, cards, chosenCards, game);
        Cards toShuffle = new CardsImpl(chosenCards.getTargets().stream()
            .map(game::getCard)
            .collect(Collectors.toList()));

        player.putCardsOnTopOfLibrary(toShuffle, game, source, false);
        player.shuffleLibrary(source, game);
        cards.removeAll(toShuffle);

        player.moveCards(cards, Zone.BATTLEFIELD, source, game);

        return true;
    }
}

class EcologicalAppreciationTarget extends TargetCard {

    private static final FilterCard filter = new FilterCreatureCard("creature cards with different names that each have mana value X or less");

    private final int xValue;

    EcologicalAppreciationTarget(Zone zone, int maxTargets, int xValue) {
        super(0, maxTargets, zone, filter);
        this.xValue = xValue;
    }

    private EcologicalAppreciationTarget(final EcologicalAppreciationTarget target) {
        super(target);
        this.xValue = target.xValue;
    }

    @Override
    public EcologicalAppreciationTarget copy() {
        return new EcologicalAppreciationTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null && card.getConvertedManaCost() <= xValue &&
            this.getTargets().stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .noneMatch(card.getName()::equals);
    }
}