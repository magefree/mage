package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoomsdayExcruciator extends CardImpl {

    public DoomsdayExcruciator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}{B}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Doomsday Excruciator enters, if it was cast, each player exiles all but the bottom six cards of their library face down.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DoomsdayExcruciatorEffect()),
                CastFromEverywhereSourceCondition.instance, "When {this} enters, if it was cast, " +
                "each player exiles all but the bottom six cards of their library face down."
        ));

        // At the beginning of your upkeep, draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.YOU, false
        ));
    }

    private DoomsdayExcruciator(final DoomsdayExcruciator card) {
        super(card);
    }

    @Override
    public DoomsdayExcruciator copy() {
        return new DoomsdayExcruciator(this);
    }
}

class DoomsdayExcruciatorEffect extends OneShotEffect {

    DoomsdayExcruciatorEffect() {
        super(Outcome.Benefit);
    }

    private DoomsdayExcruciatorEffect(final DoomsdayExcruciatorEffect effect) {
        super(effect);
    }

    @Override
    public DoomsdayExcruciatorEffect copy() {
        return new DoomsdayExcruciatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int amount = player.getLibrary().size() - 6;
            if (amount > 0) {
                cards.addAllCards(player.getLibrary().getTopCards(game, amount));
            }
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.getCards(game)
                .stream()
                .forEach(card -> card.setFaceDown(true, game));
        return true;
    }
}
