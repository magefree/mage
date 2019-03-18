
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class PrimalOrder extends CardImpl {

    public PrimalOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // At the beginning of each player's upkeep, Primal Order deals damage to that player equal to the number of nonbasic lands he or she controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PrimalOrderDamageTargetEffect(), TargetController.ANY, false, true));
    }

    public PrimalOrder(final PrimalOrder card) {
        super(card);
    }

    @Override
    public PrimalOrder copy() {
        return new PrimalOrder(this);
    }
}

class PrimalOrderDamageTargetEffect extends OneShotEffect{
    
    private static final FilterLandPermanent filter = FilterLandPermanent.nonbasicLands();
    
    public PrimalOrderDamageTargetEffect()
    {
        super(Outcome.Damage);
    }
    
    public PrimalOrderDamageTargetEffect(PrimalOrderDamageTargetEffect copy)
    {
        super(copy);
    }
        
    @Override
    public String getText(Mode mode) {
        return "{this} deals damage to that player equal to the number of nonbasic lands he or she controls";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = game.getBattlefield().getAllActivePermanents(filter, targetPointer.getFirst(game, source), game).size();
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public PrimalOrderDamageTargetEffect copy() {
        return new PrimalOrderDamageTargetEffect(this);
    }
}
