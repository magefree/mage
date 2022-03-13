package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NoeticScales extends CardImpl {

    public NoeticScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of each player's upkeep, return to its owner's hand each creature that player controls with power greater than the number of cards in their hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new NoeticScalesEffect(), TargetController.ANY, false
        ));
    }

    private NoeticScales(final NoeticScales card) {
        super(card);
    }

    @Override
    public NoeticScales copy() {
        return new NoeticScales(this);
    }
}

class NoeticScalesEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(NoeticScalesPredicate.instance);
    }

    NoeticScalesEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to its owner's hand each creature that player controls with power greater than the number of cards in their hand";
    }

    private NoeticScalesEffect(final NoeticScalesEffect effect) {
        super(effect);
    }

    @Override
    public NoeticScalesEffect copy() {
        return new NoeticScalesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getBattlefield().getActivePermanents(
                filter, game.getActivePlayerId(), source, game
        ).stream().filter(Objects::nonNull).forEach(cards::add);
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}

enum NoeticScalesPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        Player player = game.getPlayer(input.getControllerId());
        return player != null && player.getHand().size() < input.getPower().getValue();
    }
}
