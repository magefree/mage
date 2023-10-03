package mage.cards.r;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class RealityScramble extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("permanent you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public RealityScramble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Put target permanent you own on the bottom of your library. Reveal cards from the top of your library
        // until you reveal a card that shares a card type with that permanent. Put that card onto the battlefield
        // and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new RealityScrambleEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private RealityScramble(final RealityScramble card) {
        super(card);
    }

    @Override
    public RealityScramble copy() {
        return new RealityScramble(this);
    }
}

class RealityScrambleEffect extends OneShotEffect {

    public RealityScrambleEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target permanent you own "
                + "on the bottom of your library. Reveal cards from "
                + "the top of your library until you reveal a card "
                + "that shares a card type with that permanent. "
                + "Put that card onto the battlefield and the rest "
                + "on the bottom of your library in a random order.";
    }

    private RealityScrambleEffect(final RealityScrambleEffect effect) {
        super(effect);
    }

    @Override
    public RealityScrambleEffect copy() {
        return new RealityScrambleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null || controller == null) {
            return false;
        }
        Set<CardType> types = EnumSet.noneOf(CardType.class);
        types.addAll(permanent.getCardType(game));
        controller.putCardsOnBottomOfLibrary(
                new CardsImpl(permanent), game, source, false
        );
        Cards toReveal = new CardsImpl();
        Card cardToPlay = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            toReveal.add(card);
            for (CardType type : types) {
                if (card.getCardType(game).contains(type)) {
                    cardToPlay = card;
                    break;
                }
            }
            if (cardToPlay != null) {
                break;
            }
        }
        controller.revealCards(source, toReveal, game);
        if (cardToPlay != null) {
            controller.moveCards(cardToPlay, Zone.BATTLEFIELD, source, game);
            toReveal.remove(cardToPlay);
        }
        controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
