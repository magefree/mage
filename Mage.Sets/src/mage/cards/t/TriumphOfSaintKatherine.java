package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TriumphOfSaintKatherine extends CardImpl {

    public TriumphOfSaintKatherine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Praesidium Protectiva -- When Triumph of Saint Katherine dies, exile it and the top six cards of your library in a face-down pile. If you do, shuffle that pile and put it back on top of your library.
        this.addAbility(new DiesSourceTriggeredAbility(new TriumphOfSaintKatherineEffect()).withFlavorWord("Praesidium Protectiva"));

        // Miracle {1}{W}
        this.addAbility(new MiracleAbility("{1}{W}"));
    }

    private TriumphOfSaintKatherine(final TriumphOfSaintKatherine card) {
        super(card);
    }

    @Override
    public TriumphOfSaintKatherine copy() {
        return new TriumphOfSaintKatherine(this);
    }
}

class TriumphOfSaintKatherineEffect extends OneShotEffect {

    TriumphOfSaintKatherineEffect() {
        super(Outcome.Benefit);
        staticText = "exile it and the top six cards of your library in a face-down pile. " +
                "If you do, shuffle that pile and put it back on top of your library";
    }

    private TriumphOfSaintKatherineEffect(final TriumphOfSaintKatherineEffect effect) {
        super(effect);
    }

    @Override
    public TriumphOfSaintKatherineEffect copy() {
        return new TriumphOfSaintKatherineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        if (card.getZoneChangeCounter(game) == source.getSourceObjectZoneChangeCounter()) {
            cards.add(card);
        }
        cards.addAllCards(player.getLibrary().getTopCards(game, 6));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.getCards(game)
                .stream()
                .forEach(c -> c.setFaceDown(true, game));
        player.putCardsOnTopOfLibrary(cards, game, source, false);
        return true;
    }
}
