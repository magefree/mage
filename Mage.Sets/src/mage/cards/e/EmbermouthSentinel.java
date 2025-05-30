package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmbermouthSentinel extends CardImpl {

    public EmbermouthSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, you may search your library for a basic land card, reveal it, then shuffle and put that card on top. If you control a Dragon, put that card onto the battlefield tapped instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmbermouthSentinelEffect(), true));
    }

    private EmbermouthSentinel(final EmbermouthSentinel card) {
        super(card);
    }

    @Override
    public EmbermouthSentinel copy() {
        return new EmbermouthSentinel(this);
    }
}

class EmbermouthSentinelEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON);

    EmbermouthSentinelEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a basic land card, reveal it, then shuffle and put that card on top. " +
                "If you control a Dragon, put that card onto the battlefield tapped instead";
    }

    private EmbermouthSentinelEffect(final EmbermouthSentinelEffect effect) {
        super(effect);
    }

    @Override
    public EmbermouthSentinelEffect copy() {
        return new EmbermouthSentinelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        player.shuffleLibrary(source, game);
        if (card == null) {
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (game.getBattlefield().contains(filter, source, game, 1)) {
            player.moveCards(
                    card, Zone.BATTLEFIELD, source, game, true,
                    false, false, null
            );
        } else {
            player.putCardsOnTopOfLibrary(card, game, source, false);
        }
        return true;
    }
}
