package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class EyeCollector extends CardImpl {

    public EyeCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Eye Collector deals combat damage to a player, each player puts the top card of their library into their graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new EyeCollectorEffect(), false));
    }

    private EyeCollector(final EyeCollector card) {
        super(card);
    }

    @Override
    public EyeCollector copy() {
        return new EyeCollector(this);
    }
}

class EyeCollectorEffect extends OneShotEffect {

    EyeCollectorEffect() {
        super(Outcome.Benefit);
        staticText = "each player puts the top card of their library into their graveyard";
    }

    private EyeCollectorEffect(final EyeCollectorEffect effect) {
        super(effect);
    }

    @Override
    public EyeCollectorEffect copy() {
        return new EyeCollectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        return controller.moveCards(new CardsImpl(game.getState()
                .getPlayersInRange(controller.getId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(player -> player != null)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .collect(Collectors.toSet())
        ), Zone.GRAVEYARD, source, game);
    }
}