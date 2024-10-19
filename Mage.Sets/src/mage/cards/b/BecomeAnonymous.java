package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BecomeAnonymous extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creature you own");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public BecomeAnonymous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Exile target nontoken creature you own and the top two cards of your library in a face-down pile, shuffle that pile, then cloak those cards. They enter the battlefield tapped.
        this.getSpellAbility().addEffect(new BecomeAnonymousEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private BecomeAnonymous(final BecomeAnonymous card) {
        super(card);
    }

    @Override
    public BecomeAnonymous copy() {
        return new BecomeAnonymous(this);
    }
}

class BecomeAnonymousEffect extends OneShotEffect {

    BecomeAnonymousEffect() {
        super(Outcome.Benefit);
        staticText = "exile target nontoken creature you own and the top two cards of your library in " +
                "a face-down pile, shuffle that pile, then cloak those cards. They enter tapped";
    }

    private BecomeAnonymousEffect(final BecomeAnonymousEffect effect) {
        super(effect);
    }

    @Override
    public BecomeAnonymousEffect copy() {
        return new BecomeAnonymousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        cards.add(permanent);
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        if (cards.isEmpty()) {
            return true;
        }
        Set<Card> cardSet = cards.getCards(game);
        cardSet.stream().forEach(card -> card.setFaceDown(true, game));
        game.processAction();
        return !ManifestEffect.doManifestCards(game, source, player, cardSet, true, true).isEmpty();
    }
}
