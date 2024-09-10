package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes, TheElk801
 */
public final class CoercivePortal extends CardImpl {

    public CoercivePortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Will of the council - At the beginning of your upkeep, starting with you, each player votes for carnage or homage. If carnage gets more votes, sacrifice Coercive Portal and destroy all nonland permanents. If homage gets more votes or the vote is tied, draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new CoercivePortalEffect(), TargetController.YOU,
                false, false, "<i>Will of the council</i> &mdash; " +
                "At the beginning of your upkeep, starting with you, each player votes for carnage or homage. " +
                "If carnage gets more votes, sacrifice {this} and destroy all nonland permanents. " +
                "If homage gets more votes or the vote is tied, draw a card."
        ));
    }

    private CoercivePortal(final CoercivePortal card) {
        super(card);
    }

    @Override
    public CoercivePortal copy() {
        return new CoercivePortal(this);
    }
}

class CoercivePortalEffect extends OneShotEffect {

    CoercivePortalEffect() {
        super(Outcome.Benefit);
    }

    private CoercivePortalEffect(final CoercivePortalEffect effect) {
        super(effect);
    }

    @Override
    public CoercivePortalEffect copy() {
        return new CoercivePortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Outcome.Detriment - AI will draw a card all the time (Homage choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote("Carnage (sacrifice and destroy)", "Homage (draw a card)", Outcome.Detriment);
        vote.doVotes(source, game);

        int carnageCount = vote.getVoteCount(true);
        int homageCount = vote.getVoteCount(false);
        if (carnageCount > homageCount) {
            // carnage
            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
                permanent.sacrifice(source, game);
            }
            new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_NON_LAND).apply(game, source);
        } else {
            // homage or tied
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.drawCards(1, source, game);
            }
        }
        return true;
    }
}
