package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrialOfATimeLord extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("nontoken creature an opponent controls");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public TrialOfATimeLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III -- Exile target nontoken creature an opponent controls until Trial of a Time Lord leaves the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new ExileUntilSourceLeavesEffect(), new TargetPermanent(filter)
        );

        // IV -- Starting with you, each player votes for innocent or guilty. If guilty gets more votes, the owner of each card exiled with Trial of a Time Lord puts that card on the bottom of their library.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, new TrialOfATimeLordEffect());
        this.addAbility(sagaAbility);
    }

    private TrialOfATimeLord(final TrialOfATimeLord card) {
        super(card);
    }

    @Override
    public TrialOfATimeLord copy() {
        return new TrialOfATimeLord(this);
    }
}

class TrialOfATimeLordEffect extends OneShotEffect {

    TrialOfATimeLordEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for innocent or guilty. If guilty gets more votes, " +
                "the owner of each card exiled with {this} puts that card on the bottom of their library";
    }

    private TrialOfATimeLordEffect(final TrialOfATimeLordEffect effect) {
        super(effect);
    }

    @Override
    public TrialOfATimeLordEffect copy() {
        return new TrialOfATimeLordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote vote = new TwoChoiceVote("Innocent", "Guilty", Outcome.Detriment);
        vote.doVotes(source, game);
        if (vote.getMostVoted().contains(Boolean.TRUE)) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player != null && exileZone != null && !exileZone.isEmpty()) {
            player.putCardsOnBottomOfLibrary(exileZone, game, source, false);
        }
        return true;
    }
}
