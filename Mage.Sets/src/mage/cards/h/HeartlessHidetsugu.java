
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HeartlessHidetsugu extends CardImpl {

    public HeartlessHidetsugu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {tap}: Heartless Hidetsugu deals damage to each player equal to half that player's life total, rounded down.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new HeartlessHidetsuguDamageEffect(), new TapSourceCost()));

    }

    public HeartlessHidetsugu(final HeartlessHidetsugu card) {
        super(card);
    }

    @Override
    public HeartlessHidetsugu copy() {
        return new HeartlessHidetsugu(this);
    }
}

class HeartlessHidetsuguDamageEffect extends OneShotEffect {

    public HeartlessHidetsuguDamageEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals damage to each player equal to half that player's life total, rounded down";
    }

    public HeartlessHidetsuguDamageEffect(final HeartlessHidetsuguDamageEffect effect) {
        super(effect);
    }

    @Override
    public HeartlessHidetsuguDamageEffect copy() {
        return new HeartlessHidetsuguDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int damage = player.getLife() / 2;
                    player.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
