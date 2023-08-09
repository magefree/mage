package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheRingGoesSouth extends CardImpl {

    private static final Hint hint = new ValueHint(
            "legendary creatures you control",
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY)
    );

    public TheRingGoesSouth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // The Ring tempts you. Then reveal cards from the top of your library until you reveal X land cards, where X is the number of legendary creatures you control. Put those lands onto the battlefield tapped and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
        this.getSpellAbility().addEffect(new TheRingGoesSouthEffect());
        this.getSpellAbility().addHint(hint);
    }

    private TheRingGoesSouth(final TheRingGoesSouth card) {
        super(card);
    }

    @Override
    public TheRingGoesSouth copy() {
        return new TheRingGoesSouth(this);
    }
}

class TheRingGoesSouthEffect extends OneShotEffect {

    TheRingGoesSouthEffect() {
        super(Outcome.Benefit);
        staticText = "Then reveal cards from the top of your library until you reveal X land cards, " +
                "where X is the number of legendary creatures you control. Put those land cards onto " +
                "the battlefield tapped and the rest on the bottom of your library in a random order.";
    }

    private TheRingGoesSouthEffect(final TheRingGoesSouthEffect effect) {
        super(effect);
    }

    @Override
    public TheRingGoesSouthEffect copy() {
        return new TheRingGoesSouthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int count = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY,
                source.getControllerId(), source, game
        );
        if (player == null || count < 1) {
            return false;
        }
        Cards cards = new CardsImpl();
        Cards lands = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isLand(game)) {
                lands.add(card);
            }
            if (lands.size() >= count) {
                break;
            }
        }
        player.revealCards(source, cards, game);
        player.moveCards(
                lands.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, false, null
        );
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
