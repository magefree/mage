package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Codex
 */
public final class FatefulTempest extends CardImpl {

    public FatefulTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Council's dilemma -- Starting with you, each player votes for past or present. You mill a card for each past vote, then Fateful Tempest deals damage to each opponent equal to the total mana value of cards milled this way. Exile the top card of your library for each present vote. Until the end of your next turn, you may play the exiled cards.
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
        staticText = "starting with you, each player votes for past or present. "
                + "You mill a card for each past vote, then {this} deals damage to each opponent equal to the total mana value of cards milled this way. "
                + "Exile the top card of your library for each present vote. Until the end of your next turn, you may play the exiled cards";
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
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        TwoChoiceVote vote = new TwoChoiceVote("Past (mill a card)", "Present (exile top card)", Outcome.Detriment);
        vote.doVotes(source, game);

        int pastVotes = vote.getVoteCount(true);
        int presentVotes = vote.getVoteCount(false);

        if (pastVotes > 0) {
            Cards cards = player.millCards(pastVotes, source, game);
            int damage = cards
                    .getCards(game)
                    .stream()
                    .filter(Objects::nonNull)
                    .mapToInt(Card::getManaValue)
                    .sum();
            new DamagePlayersEffect(damage, TargetController.OPPONENT).apply(game, source);
        }
        if (presentVotes > 0) {
            new ExileTopXMayPlayUntilEffect(presentVotes, Duration.UntilEndOfYourNextTurn).apply(game, source);
        }
        return true;
    }
}
