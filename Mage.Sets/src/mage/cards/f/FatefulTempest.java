package mage.cards.f;

import java.util.Objects;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class FatefulTempest extends CardImpl {

    public FatefulTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Council's dilemma -- Starting with you, each player votes for past or present.
        // You mill a card for each past vote, then Fateful Tempest deals damage to each opponent equal to the total mana value of cards milled this way.
        // Exile the top card of your library for each present vote. Until the end of your next turn, you may play the exiled cards.
        this.getSpellAbility().addEffect(new FatefulTempestEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.COUNCILS_DILEMMA);
    }

    private FatefulTempest(final FatefulTempest card) {
        super(card);
    }

    @Override
    public FatefulTempest copy() {
        return new FatefulTempest(this);
    }
}

class FatefulTempestEffect extends OneShotEffect {

    FatefulTempestEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for past or present. " +
                "You mill a card for each past vote, then Fateful Tempest deals damage to each opponent equal to the total mana value of cards milled this way. " +
                "Exile the top card of your library for each present vote. Until the end of your next turn, you may play the exiled cards.";
    }

    private FatefulTempestEffect(final FatefulTempestEffect effect) {
        super(effect);
    }

    @Override
    public FatefulTempestEffect copy() {
        return new FatefulTempestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote voteHandler = new TwoChoiceVote(
                "Past (mill and deal damage)",
                "Present (exile and play this turn)",
                Outcome.Detriment
        );
        voteHandler.doVotes(source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int pastVotes = voteHandler.getVoteCount(true);
        int presentVotes = voteHandler.getVoteCount(false);

        if (pastVotes > 0) {
            int totalValue = player
                .millCards(pastVotes, source, game)
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
            for (UUID opponentId : game.getOpponents(player.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.damage(totalValue, source.getSourceId(), source, game);
                }
            }
        }

        if (presentVotes > 0) {
            Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, presentVotes));
            if (!cards.isEmpty()) {
                player.moveCardsToExile(
                        cards.getCards(game), source, game, true,
                        CardUtil.getExileZoneId(game, source),
                        CardUtil.getSourceName(game, source)
                );
                cards.retainZone(Zone.EXILED, game);
                if (!cards.isEmpty()) {
                    ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.UntilEndOfYourNextTurn);
                    effect.setTargetPointer(new FixedTargets(cards, game));
                    game.addEffect(effect, source);
                }
            }
        }

        return true;
    }
}
