package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VentureForth extends CardImpl {

    public VentureForth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Exile cards from the top of your library until you exile a land card. Put that onto the battlefield and the rest on the bottom of your library in a random order. Exile Venture Forth with three time counters on it.
        this.getSpellAbility().addEffect(new VentureForthEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addEffect(new AddCountersSourceEffect(
                CounterType.TIME.createInstance(), StaticValue.get(3), false, true
        ).setText("with three time counters on it"));

        // Suspend 3—{1}{G}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{G}"), this));
    }

    private VentureForth(final VentureForth card) {
        super(card);
    }

    @Override
    public VentureForth copy() {
        return new VentureForth(this);
    }
}

class VentureForthEffect extends OneShotEffect {

    VentureForthEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a land card. Put that card " +
                "onto the battlefield and the rest on the bottom of your library in a random order";
    }

    private VentureForthEffect(final VentureForthEffect effect) {
        super(effect);
    }

    @Override
    public VentureForthEffect copy() {
        return new VentureForthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            if (card.isLand(game)) {
                player.moveCards(card, Zone.BATTLEFIELD, source, game);
                break;
            }
            cards.add(card);
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
