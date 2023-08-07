
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class ProteusStaff extends CardImpl {

    public ProteusStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}{U}, {T}: Put target creature on the bottom of its owner's library. That creature's controller reveals cards from the top of their library until they reveal a creature card. The player puts that card onto the battlefield and the rest on the bottom of their library in any order. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new ProteusStaffEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ProteusStaff(final ProteusStaff card) {
        super(card);
    }

    @Override
    public ProteusStaff copy() {
        return new ProteusStaff(this);
    }
}

class ProteusStaffEffect extends OneShotEffect {

    ProteusStaffEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put target creature on the bottom of its owner's library. That creature's controller reveals cards from the top of their library until they reveal a creature card. The player puts that card onto the battlefield and the rest on the bottom of their library in any order.";
    }

    ProteusStaffEffect(final ProteusStaffEffect effect) {
        super(effect);
    }

    @Override
    public ProteusStaffEffect copy() {
        return new ProteusStaffEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            Player controller = game.getPlayer(permanent.getControllerId());
            if (owner != null && controller != null) {
                // Put target creature on the bottom of its owner's library.
                owner.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, false, true);

                // That creature's controller reveals cards from the top of their library until they reveal a creature card.
                Cards cards = new CardsImpl();
                for (Card card : controller.getLibrary().getCards(game)) {
                    if (card != null) {
                        if (card.isCreature(game)) {
                            // The player puts that card onto the battlefield
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            break;
                        } else {
                            cards.add(card);
                        }
                    }
                }
                controller.revealCards("Proteus Staff", cards, game);

                // and the rest on the bottom of their library in any order.
                while (!cards.isEmpty() && controller.canRespond()) {
                    if (cards.size() == 1) {
                        Card card = cards.get(cards.iterator().next(), game);
                        if (card != null) {
                            controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, false, false);
                            cards.remove(card);
                        }
                    } else {
                        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put on bottom of your library (last chosen will be on bottom)"));
                        controller.choose(Outcome.Neutral, cards, target, source, game);
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, false, false);
                            cards.remove(card);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
