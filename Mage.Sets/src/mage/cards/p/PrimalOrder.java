package mage.cards.p;

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

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PrimalOrder extends CardImpl {

    public PrimalOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // At the beginning of each player's upkeep, Primal Order deals damage to that player equal to the number of nonbasic lands they control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PrimalOrderDamageTargetEffect(), TargetController.ANY, false, true));
    }

    private PrimalOrder(final PrimalOrder card) {
        super(card);
    }

    @Override
    public PrimalOrder copy() {
        return new PrimalOrder(this);
    }
}

class PrimalOrderDamageTargetEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = FilterLandPermanent.nonbasicLands();

    public PrimalOrderDamageTargetEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to that player equal to the number of nonbasic lands they control";
    }

    public PrimalOrderDamageTargetEffect(PrimalOrderDamageTargetEffect copy) {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = game.getBattlefield().getAllActivePermanents(filter, targetPointer.getFirst(game, source), game).size();
            player.damage(damage, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public PrimalOrderDamageTargetEffect copy() {
        return new PrimalOrderDamageTargetEffect(this);
    }
}
