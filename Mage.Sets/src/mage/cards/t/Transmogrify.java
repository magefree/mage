package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class Transmogrify extends CardImpl {

    public Transmogrify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Exile target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        // That creature’s controller reveals cards from the top of their library until they reveal a creature card.
        // That player puts that card onto the battlefield, then shuffles the rest into their library.
        this.getSpellAbility().addEffect(new TransmogrifyEffect());
    }

    private Transmogrify(final Transmogrify card) {
        super(card);
    }

    @Override
    public Transmogrify copy() {
        return new Transmogrify(this);
    }
}

class TransmogrifyEffect extends OneShotEffect {

    public TransmogrifyEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "That creature's controller reveals cards from the top of their library until they reveal a creature card. That player puts that card onto the battlefield, then shuffles the rest into their library";
    }

    private TransmogrifyEffect(final TransmogrifyEffect effect) {
        super(effect);
    }

    @Override
    public TransmogrifyEffect copy() {
        return new TransmogrifyEffect(this);
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
