
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Loki
 */
public final class JacesArchivist extends CardImpl {

    public JacesArchivist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}, {T}: Each player discards their hand, then draws cards equal to the greatest number of cards a player discarded this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JacesArchivistEffect(), new ColoredManaCost(ColoredManaSymbol.U));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public JacesArchivist(final JacesArchivist card) {
        super(card);
    }

    @Override
    public JacesArchivist copy() {
        return new JacesArchivist(this);
    }
}

class JacesArchivistEffect extends OneShotEffect {

    JacesArchivistEffect() {
        super(Outcome.Discard);
        staticText = "Each player discards their hand, then draws cards equal to the greatest number of cards a player discarded this way";
    }

    JacesArchivistEffect(final JacesArchivistEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxDiscarded = 0;
        Player controller = game.getPlayer(source.getControllerId());
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int discarded = player.getHand().size();
                player.discard(discarded, false, source, game);
                if (discarded > maxDiscarded) {
                    maxDiscarded = discarded;
                }
            }
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(maxDiscarded, game);
            }
        }

        return true;
    }

    @Override
    public JacesArchivistEffect copy() {
        return new JacesArchivistEffect(this);
    }
}
