
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
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
 * @author LevelX2
 */
public final class HokoriDustDrinker extends CardImpl {

    public HokoriDustDrinker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lands don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, StaticFilters.FILTER_LANDS)));

        // At the beginning of each player's upkeep, that player untaps a land they control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new HokoriDustDrinkerUntapEffect(), TargetController.ANY, false));


    }

    private HokoriDustDrinker(final HokoriDustDrinker card) {
        super(card);
    }

    @Override
    public HokoriDustDrinker copy() {
        return new HokoriDustDrinker(this);
    }
}

class HokoriDustDrinkerUntapEffect extends OneShotEffect {

    public HokoriDustDrinkerUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "that player untaps a land they control";
    }

    public HokoriDustDrinkerUntapEffect(final HokoriDustDrinkerUntapEffect effect) {
        super(effect);
    }

    @Override
    public HokoriDustDrinkerUntapEffect copy() {
        return new HokoriDustDrinkerUntapEffect(this);
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
