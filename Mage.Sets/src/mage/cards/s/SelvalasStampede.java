
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.CouncilsDilemmaVoteEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author JRHerlehy
 */
public final class SelvalasStampede extends CardImpl {

    public SelvalasStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // <i>Council's dilemma</i> &mdash Starting with you, each player votes for wild or free. Reveal cards from the top of your library until you reveal a creature card for each wild vote. Put those creature cards onto the battlefield, then shuffle the rest into your library. You may put a permanent card from your hand onto the battlefield for each free vote.
        this.getSpellAbility().addEffect(new SelvalasStampedeDilemmaEffect());
    }

    public SelvalasStampede(final SelvalasStampede card) {
        super(card);
    }

    @Override
    public SelvalasStampede copy() {
        return new SelvalasStampede(this);
    }
}

class SelvalasStampedeDilemmaEffect extends CouncilsDilemmaVoteEffect {

    public SelvalasStampedeDilemmaEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "<i>Council's dilemma</i> &mdash; Starting with you, each player votes for wild or free. Reveal cards from the top of your library until you reveal a creature card for each wild vote. Put those creature cards onto the battlefield, then shuffle the rest into your library. "
                + "You may put a permanent card from your hand onto the battlefield for each free vote";
    }

    public SelvalasStampedeDilemmaEffect(final SelvalasStampedeDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        //If no controller, exit here and do not vote.
        if (controller == null) {
            return false;
        }

        this.vote("wild", "free", controller, game, source);

        //Wild Votes
        if (voteOneCount > 0) {
            Cards revealedCards = new CardsImpl();
            Cards toBattlefield = new CardsImpl();
            for (Card card : controller.getLibrary().getCards(game)) {
                revealedCards.add(card);
                if (card.isCreature()) {
                    toBattlefield.add(card);
                    if (toBattlefield.size() == voteOneCount) {
                        break;
                    }
                }
            }
            controller.revealCards(source, revealedCards, game);
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            revealedCards.removeAll(toBattlefield);
            if (!revealedCards.isEmpty()) {
                controller.shuffleLibrary(source, game);
            }
        }

        //Free Votes
        if (voteTwoCount > 0) {
            TargetCardInHand target = new TargetCardInHand(0, voteTwoCount, new FilterPermanentCard("permanent cards"));
            if (controller.choose(outcome, target, source.getSourceId(), game)) {
                controller.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
            }
        }

        return true;
    }

    @Override
    public SelvalasStampedeDilemmaEffect copy() {
        return new SelvalasStampedeDilemmaEffect(this);
    }
}
