package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterBySubtypeCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PulsarSquadronAce extends CardImpl {

    public PulsarSquadronAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When this creature enters, look at the top five cards of your library. You may reveal a Spacecraft card from among them and put it into your hand. Put the rest on the bottom of your library in a random order. If you didn't put a card into your hand this way, put a +1/+1 counter on this creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PulsarSquadronAceEffect()));
    }

    private PulsarSquadronAce(final PulsarSquadronAce card) {
        super(card);
    }

    @Override
    public PulsarSquadronAce copy() {
        return new PulsarSquadronAce(this);
    }
}

class PulsarSquadronAceEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterBySubtypeCard(SubType.SPACECRAFT);

    PulsarSquadronAceEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top five cards of your library. You may reveal a Spacecraft card from among them " +
                "and put it into your hand. Put the rest on the bottom of your library in a random order. " +
                "If you didn't put a card into your hand this way, put a +1/+1 counter on {this}";
    }

    private PulsarSquadronAceEffect(final PulsarSquadronAceEffect effect) {
        super(effect);
    }

    @Override
    public PulsarSquadronAceEffect copy() {
        return new PulsarSquadronAceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        TargetCard target = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
            Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                    .filter(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game))
                    .isPresent();
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        player.moveCards(card, Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
