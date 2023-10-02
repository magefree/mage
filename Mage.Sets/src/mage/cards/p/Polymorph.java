
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Polymorph extends CardImpl {

    public Polymorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Destroy target creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        // Its controller reveals cards from the top of their library until they reveal a creature card.
        // The player puts that card onto the battlefield, then shuffles all other cards revealed this way into their library.
        this.getSpellAbility().addEffect(new PolymorphEffect());
    }

    private Polymorph(final Polymorph card) {
        super(card);
    }

    @Override
    public Polymorph copy() {
        return new Polymorph(this);
    }
}

class PolymorphEffect extends OneShotEffect {

    public PolymorphEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Its controller reveals cards from the top of their library until they reveal a creature card. The player puts that card onto the battlefield, then shuffles all other cards revealed this way into their library";
    }

    private PolymorphEffect(final PolymorphEffect effect) {
        super(effect);
    }

    @Override
    public PolymorphEffect copy() {
        return new PolymorphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                Library library = player.getLibrary();
                if (library.hasCards()) {
                    Cards cards = new CardsImpl();
                    Card toBattlefield = null;
                    for (Card card : library.getCards(game)) {
                        cards.add(card);
                        if (card.isCreature(game)) {
                            toBattlefield = card;
                            break;
                        }
                    }
                    if (toBattlefield != null) {
                        player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
                    }
                    player.revealCards(source, cards, game);
                    cards.remove(toBattlefield);
                    if (!cards.isEmpty()) {
                        player.shuffleLibrary(source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
