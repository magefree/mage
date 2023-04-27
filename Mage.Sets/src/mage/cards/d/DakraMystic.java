
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DakraMystic extends CardImpl {

    public DakraMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U},{T}:Each player reveals the top card of their library. You may put the revealed cards into their owners graveyard. If you don't, each player draws a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DakraMysticEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private DakraMystic(final DakraMystic card) {
        super(card);
    }

    @Override
    public DakraMystic copy() {
        return new DakraMystic(this);
    }
}

class DakraMysticEffect extends OneShotEffect {

    public DakraMysticEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player reveals the top card of their library. You may put the revealed cards into their owners' graveyard. If you don't, each player draws a card";
    }

    public DakraMysticEffect(final DakraMysticEffect effect) {
        super(effect);
    }

    @Override
    public DakraMysticEffect copy() {
        return new DakraMysticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.getLibrary().hasCards()) {
                    player.revealCards(player.getLogName(), new CardsImpl(player.getLibrary().getFromTop(game)), game);
                }
            }
            if (controller.chooseUse(outcome, "Put revealed cards into graveyard?", source, game)) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && player.getLibrary().hasCards()) {
                        player.moveCards(player.getLibrary().getFromTop(game), Zone.GRAVEYARD, source, game);
                    }
                }
            } else {
                new DrawCardAllEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
