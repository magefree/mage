package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TravelThroughCaradhras extends CardImpl {

    public TravelThroughCaradhras(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Council's dilemma -- Starting with you, each player votes for Redhorn Pass or Mines of Moria. For each Redhorn Pass vote, search your library for a basic land card and put it onto the battlefield tapped. If you search your library this way, shuffle. For each Mines of Moria vote, return a card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new TravelThroughCaradhrasEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.COUNCILS_DILEMMA);

        // Exile Travel Through Caradhras.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private TravelThroughCaradhras(final TravelThroughCaradhras card) {
        super(card);
    }

    @Override
    public TravelThroughCaradhras copy() {
        return new TravelThroughCaradhras(this);
    }
}

class TravelThroughCaradhrasEffect extends OneShotEffect {

    TravelThroughCaradhrasEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for Redhorn Pass or Mines of Moria. " +
                "For each Redhorn Pass vote, search your library for a basic land card and " +
                "put it onto the battlefield tapped. If you search your library this way, " +
                "shuffle. For each Mines of Moria vote, return a card from your graveyard to your hand";
    }

    private TravelThroughCaradhrasEffect(final TravelThroughCaradhrasEffect effect) {
        super(effect);
    }

    @Override
    public TravelThroughCaradhrasEffect copy() {
        return new TravelThroughCaradhrasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote voteHandler = new TwoChoiceVote(
                "Redhorn Pass (search for land)",
                "Mines of Moria (return from graveyard)",
                Outcome.Detriment
        );
        voteHandler.doVotes(source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int redhornPass = voteHandler.getVoteCount(true);
        int minesOfMoria = voteHandler.getVoteCount(false);
        if (redhornPass > 0) {
            new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                    redhornPass, StaticFilters.FILTER_CARD_BASIC_LAND
            ), true).apply(game, source);
        }
        if (minesOfMoria > 0) {
            TargetCard target = new TargetCardInYourGraveyard(Math.min(minesOfMoria, player.getGraveyard().size()));
            target.setNotTarget(true);
            player.choose(outcome, player.getGraveyard(), target, source, game);
            player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
        }
        return true;
    }
}
