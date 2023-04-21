package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BountyOfSkemfar extends CardImpl {

    public BountyOfSkemfar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top six cards of your library. You may put a land card from among them onto the battlefield tapped and an Elf card from among them into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new BountyOfSkemfarEffect());
    }

    private BountyOfSkemfar(final BountyOfSkemfar card) {
        super(card);
    }

    @Override
    public BountyOfSkemfar copy() {
        return new BountyOfSkemfar(this);
    }
}

class BountyOfSkemfarEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an Elf card");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    BountyOfSkemfarEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top six cards of your library. You may put a land card from among them " +
                "onto the battlefield tapped and an Elf card from among them into your hand. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private BountyOfSkemfarEffect(final BountyOfSkemfarEffect effect) {
        super(effect);
    }

    @Override
    public BountyOfSkemfarEffect copy() {
        return new BountyOfSkemfarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        player.revealCards(source, cards, game);
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_LAND);
        player.choose(outcome, cards, target, source, game);
        Card land = cards.get(target.getFirstTarget(), game);
        if (land != null) {
            player.moveCards(
                    land, Zone.BATTLEFIELD, source, game, true,
                    false, false, null
            );
        }
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);
        target = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, cards, target, source, game);
        Card elf = cards.get(target.getFirstTarget(), game);
        if (elf != null) {
            player.moveCardToHandWithInfo(elf, source, game, true);
        }
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
