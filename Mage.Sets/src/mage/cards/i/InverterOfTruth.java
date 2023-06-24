package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class InverterOfTruth extends CardImpl {

    public InverterOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Inverter of Truth enters the battlefield, exile all cards from your library face down, then shuffle all cards from your graveyard into your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InverterOfTruthEffect(), false));
    }

    private InverterOfTruth(final InverterOfTruth card) {
        super(card);
    }

    @Override
    public InverterOfTruth copy() {
        return new InverterOfTruth(this);
    }
}

class InverterOfTruthEffect extends OneShotEffect {

    InverterOfTruthEffect() {
        super(Outcome.Benefit);
        staticText = "exile all cards from your library face down, "
                + "then shuffle all cards from your graveyard into your library";
    }

    private InverterOfTruthEffect(final InverterOfTruthEffect effect) {
        super(effect);
    }

    @Override
    public InverterOfTruthEffect copy() {
        return new InverterOfTruthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getCards(game));
        cards.getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .forEach(card -> card.setFaceDown(true, game));
        player.moveCards(cards, Zone.EXILED, source, game);
        player.shuffleCardsToLibrary(player.getGraveyard(), game, source);
        return true;
    }
}
