
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class EnsnaringBridge extends CardImpl {

    public EnsnaringBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Creatures with power greater than the number of cards in your hand can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EnsnaringBridgeRestrictionEffect()));
    }

    public EnsnaringBridge(final EnsnaringBridge card) {
        super(card);
    }

    @Override
    public EnsnaringBridge copy() {
        return new EnsnaringBridge(this);
    }
}

class EnsnaringBridgeRestrictionEffect extends RestrictionEffect {

    public EnsnaringBridgeRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Creatures with power greater than the number of cards in your hand can't attack";
    }

    public EnsnaringBridgeRestrictionEffect(final EnsnaringBridgeRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        return controller.getInRange().contains(permanent.getControllerId())
                && permanent.getPower().getValue() > controller.getHand().size();
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public EnsnaringBridgeRestrictionEffect copy() {
        return new EnsnaringBridgeRestrictionEffect(this);
    }

}
