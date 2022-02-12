
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BlessedReincarnation extends CardImpl {

    public BlessedReincarnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Exile target creature an opponent controls.
        // That player reveals cards from the top of their library until a creature card is revealed.
        // The player puts that card onto the battlefield, then shuffles the rest into their library.
        this.getSpellAbility().addEffect(new BlessedReincarnationEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private BlessedReincarnation(final BlessedReincarnation card) {
        super(card);
    }

    @Override
    public BlessedReincarnation copy() {
        return new BlessedReincarnation(this);
    }
}

class BlessedReincarnationEffect extends OneShotEffect {

    public BlessedReincarnationEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile target creature an opponent controls. That player reveals cards from the top of their library until a creature card is revealed. The player puts that card onto the battlefield, then shuffles the rest into their library";
    }

    public BlessedReincarnationEffect(final BlessedReincarnationEffect effect) {
        super(effect);
    }

    @Override
    public BlessedReincarnationEffect copy() {
        return new BlessedReincarnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && controller != null) {
            controller.moveCards(permanent, Zone.EXILED, source, game);
            game.getState().processAction(game);

            Player permanentController = game.getPlayer(permanent.getControllerId());
            if (permanentController != null) {
                Library library = permanentController.getLibrary();
                if (library.hasCards()) {
                    Cards toReveal = new CardsImpl();
                    for (Card card : library.getCards(game)) {
                        toReveal.add(card);
                        if (card.isCreature(game)) {
                            permanentController.moveCards(card, Zone.BATTLEFIELD, source, game);
                            break;
                        }
                    }
                    permanentController.revealCards(source, toReveal, game);
                    if (toReveal.size() > 1) {
                      permanentController.shuffleLibrary(source, game);                    }
                }
            }
            return true;
        }
        return false;
    }
}
