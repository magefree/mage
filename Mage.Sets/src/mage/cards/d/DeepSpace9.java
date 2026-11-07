package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author muz
 */
public final class DeepSpace9 extends CardImpl {

    public DeepSpace9(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{9}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // When Deep Space 9 enters and at the beginning of your upkeep, exile the top four cards of your library. Put all permanent cards exiled this way onto the battlefield.
        this.addAbility(new OrTriggeredAbility(
            Zone.BATTLEFIELD, new DeepSpace9Effect(), false,
            "When {this} enters and at the beginning of your upkeep, ",
            new EntersBattlefieldTriggeredAbility(null),
            new BeginningOfUpkeepTriggeredAbility(null)
        ));

        // Station
        this.addAbility(new StationAbility());

        // STATION 9+
        // Creatures you control get +3/+3.
        this.addAbility(new StationLevelAbility(9).withLevelAbility(
            new SimpleStaticAbility(new BoostControlledEffect(3, 3, Duration.WhileOnBattlefield))
        ));
    }

    private DeepSpace9(final DeepSpace9 card) {
        super(card);
    }

    @Override
    public DeepSpace9 copy() {
        return new DeepSpace9(this);
    }
}

class DeepSpace9Effect extends OneShotEffect {

    DeepSpace9Effect() {
        super(Outcome.PutCardInPlay);
        staticText = "exile the top four cards of your library. Put all permanent cards exiled this way onto the battlefield";
    }

    private DeepSpace9Effect(final DeepSpace9Effect effect) {
        super(effect);
    }

    @Override
    public DeepSpace9Effect copy() {
        return new DeepSpace9Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        controller.moveCards(cards, Zone.EXILED, source, game);
        game.processAction();
        cards.retainZone(Zone.EXILED, game);
        cards.removeIf(cardId -> {
            Card card = game.getCard(cardId);
            return card == null || !card.isPermanent(game);
        });
        controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
