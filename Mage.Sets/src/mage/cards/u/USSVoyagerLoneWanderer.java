package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author DejviFiala
 */
public final class USSVoyagerLoneWanderer extends CardImpl {

    public USSVoyagerLoneWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // When U.S.S. Voyager enters, reveal cards from the top of your library until you reveal two land cards. Put those land cards onto the battlefield tapped and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new USSVoyagerLoneWandererEffect()));

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // Vigilance
        // 3/3
        this.addAbility(new StationLevelAbility(8)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(VigilanceAbility.getInstance())
            .withPT(3, 3));
    }

    private USSVoyagerLoneWanderer(final USSVoyagerLoneWanderer card) {
        super(card);
    }

    @Override
    public USSVoyagerLoneWanderer copy() {
        return new USSVoyagerLoneWanderer(this);
    }
}

class USSVoyagerLoneWandererEffect extends OneShotEffect {

    USSVoyagerLoneWandererEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "reveal cards from the top of your library until you reveal two land cards. "
            + "Put those land cards onto the battlefield tapped and the rest on the bottom of your library in a random order";
    }

    private USSVoyagerLoneWandererEffect(final USSVoyagerLoneWandererEffect effect) {
        super(effect);
    }

    @Override
    public USSVoyagerLoneWandererEffect copy() {
        return new USSVoyagerLoneWandererEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Cards nonlands = new CardsImpl();
        Cards lands = new CardsImpl();
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card.isLand(game)) {
                lands.add(card);
                if (lands.size() == 2) {
                    break;
                }
            } else {
                nonlands.add(card);
            }
        }
        Cards revealed = new CardsImpl(nonlands);
        revealed.addAll(lands);
        controller.revealCards(source, revealed, game);
        PutCards.BATTLEFIELD_TAPPED.moveCards(controller, lands, source, game);
        PutCards.BOTTOM_RANDOM.moveCards(controller, nonlands, source, game);
        return true;
    }
}
