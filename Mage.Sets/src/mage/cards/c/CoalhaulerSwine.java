
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author vereena42
 */
public final class CoalhaulerSwine extends CardImpl {

    public CoalhaulerSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Coalhauler Swine is dealt damage, it deals that much damage to each player.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new CoalhaulerSwineEffect(), false, false, true));
    }

    public CoalhaulerSwine(final CoalhaulerSwine card) {
        super(card);
    }

    @Override
    public CoalhaulerSwine copy() {
        return new CoalhaulerSwine(this);
    }

    static class CoalhaulerSwineEffect extends OneShotEffect {

        public CoalhaulerSwineEffect() {
            super(Outcome.Damage);
            staticText = "it deals that much damage to each player";
        }

        public CoalhaulerSwineEffect(final CoalhaulerSwineEffect effect) {
            super(effect);
        }

        @Override
        public CoalhaulerSwineEffect copy() {
            return new CoalhaulerSwineEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            for (UUID playerId : game.getPlayers().keySet()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.damage((Integer) this.getValue("damage"), source.getSourceId(), game, false, true);
                }
            }
            return true;
        }

    }
}
