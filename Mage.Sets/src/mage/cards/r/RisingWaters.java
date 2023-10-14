package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class RisingWaters extends CardImpl {

    public RisingWaters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");

        // Lands don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, StaticFilters.FILTER_LANDS)));
        
        // At the beginning of each player's upkeep, that player untaps a land they control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new RisingWatersUntapEffect(), TargetController.ANY, false));
    }

    private RisingWaters(final RisingWaters card) {
        super(card);
    }

    @Override
    public RisingWaters copy() {
        return new RisingWaters(this);
    }
}

class RisingWatersUntapEffect extends OneShotEffect {

    RisingWatersUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "that player untaps a land they control";
    }

    private RisingWatersUntapEffect(final RisingWatersUntapEffect effect) {
        super(effect);
    }

    @Override
    public RisingWatersUntapEffect copy() {
        return new RisingWatersUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        FilterLandPermanent filter = new FilterLandPermanent("land you control");
        filter.add(new ControllerIdPredicate(game.getActivePlayerId()));
        Target target = new TargetLandPermanent(filter);
        if (player != null && player.chooseTarget(Outcome.Untap, target, source, game)) {
            for (UUID landId : target.getTargets()) {
                Permanent land = game.getPermanent(landId);
                if (land != null) {
                    land.untap(game);
                }
            }
            return true;
        }
        return false;
    }
}
