package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class GorgingVulture extends CardImpl {

    public GorgingVulture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Gorging Vulture enters the battlefield, put the top four cards of your library into your graveyard. You gain 1 life for each creature card put into your graveyard this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GorgingVultureEffect()));
    }

    private GorgingVulture(final GorgingVulture card) {
        super(card);
    }

    @Override
    public GorgingVulture copy() {
        return new GorgingVulture(this);
    }
}

class GorgingVultureEffect extends OneShotEffect {

    GorgingVultureEffect() {
        super(Outcome.Benefit);
        staticText = "put the top four cards of your library into your graveyard. " +
                "You gain 1 life for each creature card put into your graveyard this way.";
    }

    private GorgingVultureEffect(final GorgingVultureEffect effect) {
        super(effect);
    }

    @Override
    public GorgingVultureEffect copy() {
        return new GorgingVultureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        int lifeToGain = cards
                .getCards(game)
                .stream()
                .filter(Card::isCreature)
                .mapToInt(card -> game.getState().getZone(card.getId()) == Zone.GRAVEYARD ? 1 : 0)
                .sum();
        return player.gainLife(lifeToGain, game, source) > 0;
    }
}