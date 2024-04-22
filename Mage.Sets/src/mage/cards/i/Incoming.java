
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public final class Incoming extends CardImpl {

    public Incoming(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}{G}{G}");

        // Each player searches their library for any number of artifact, creature, enchantment, and/or land cards, puts them onto the battlefield, then shuffles their library.
        this.getSpellAbility().addEffect(new IncomingEffect());
    }

    private Incoming(final Incoming card) {
        super(card);
    }

    @Override
    public Incoming copy() {
        return new Incoming(this);
    }
}

class IncomingEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("artifact, creature, enchantment, and/or land cards");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public IncomingEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player searches their library for any number of artifact, creature, enchantment, and/or land cards, puts them onto the battlefield, then shuffles";
    }

    private IncomingEffect(final IncomingEffect effect) {
        super(effect);
    }

    @Override
    public IncomingEffect copy() {
        return new IncomingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
                    if (player.searchLibrary(target, source, game)) {
                        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
                        player.shuffleLibrary(source, game);
                    }
                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
