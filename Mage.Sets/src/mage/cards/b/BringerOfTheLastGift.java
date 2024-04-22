package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class BringerOfTheLastGift extends CardImpl {

    public BringerOfTheLastGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Bringer of the Last Gift enters the battlefield, if you cast it, each player sacrifices all other creatures they control. Then each player returns all creature cards from their graveyard that weren't put there this way to the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BringerOfTheLastGiftEffect()),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, each player sacrifices all other creatures they control. "
                        + "Then each player returns all creature cards from their graveyard that weren't put there this way to the battlefield."
        ));
    }

    private BringerOfTheLastGift(final BringerOfTheLastGift card) {
        super(card);
    }

    @Override
    public BringerOfTheLastGift copy() {
        return new BringerOfTheLastGift(this);
    }
}

class BringerOfTheLastGiftEffect extends OneShotEffect {

    BringerOfTheLastGiftEffect() {
        super(Outcome.Benefit);
    }

    private BringerOfTheLastGiftEffect(final BringerOfTheLastGiftEffect effect) {
        super(effect);
    }

    @Override
    public BringerOfTheLastGiftEffect copy() {
        return new BringerOfTheLastGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // per player affected, all cards that should not return on the second part.
        List<Permanent> toSacrifice = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            toSacrifice.addAll(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game));
        }

        Set<UUID> notReturned = new HashSet<>();
        for (Permanent p : toSacrifice) {
            if (p.getId().equals(source.getSourceId())) {
                continue;
            }
            p.sacrifice(source, game);
            notReturned.add(p.getMainCard().getId());
        }

        // Make sure the sacrifices are processed.
        game.getState().processAction(game);

        Set<Card> toReturn = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            toReturn.addAll(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        }

        toReturn.removeAll(notReturned.stream().map(game::getCard).collect(Collectors.toSet()));
        controller.moveCards(toReturn, Zone.BATTLEFIELD, source, game, false, false, true, null);
        return true;
    }

}