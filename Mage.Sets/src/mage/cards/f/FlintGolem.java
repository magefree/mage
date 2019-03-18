
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes & L_J
 */
public final class FlintGolem extends CardImpl {

    public FlintGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Flint Golem becomes blocked, defending player puts the top three cards of their library into their graveyard.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new FlintGolemEffect(), false));
    }

    public FlintGolem(final FlintGolem card) {
        super(card);
    }

    @Override
    public FlintGolem copy() {
        return new FlintGolem(this);
    }
}

class FlintGolemEffect extends OneShotEffect {

    public FlintGolemEffect() {
        super(Outcome.Detriment);
        this.staticText = "defending player puts the top three cards of their library into their graveyard";
    }

    public FlintGolemEffect(final FlintGolemEffect effect) {
        super(effect);
    }

    @Override
    public FlintGolemEffect copy() {
        return new FlintGolemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent blockingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (blockingCreature != null) {
            Player opponent = game.getPlayer(blockingCreature.getControllerId());
            if (opponent != null) {
                opponent.moveCards(opponent.getLibrary().getTopCards(game, 3), Zone.GRAVEYARD, source, game);
                return true;
            }
        }
        return false;
    }
}
