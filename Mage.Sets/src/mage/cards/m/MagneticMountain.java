
package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import static mage.cards.m.MagneticMountain.filter;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public final class MagneticMountain extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public MagneticMountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // Blue creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));

        // At the beginning of each player's upkeep, that player may choose any number of tapped blue creatures he or she controls and pay {4} for each creature chosen this way. If the player does, untap those creatures.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MagneticMountainEffect(), TargetController.ANY, false));
    }

    public MagneticMountain(final MagneticMountain card) {
        super(card);
    }

    @Override
    public MagneticMountain copy() {
        return new MagneticMountain(this);
    }
}

class MagneticMountainEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("tapped blue creature");

    static {
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(new TappedPredicate());
    }

    MagneticMountainEffect() {
        super(Outcome.Benefit);
        staticText = "that player may choose any number of tapped blue creatures he or she controls and pay {4} for each creature chosen this way. If the player does, untap those creatures.";
    }

    MagneticMountainEffect(MagneticMountainEffect effect) {
        super(effect);
    }

    @Override
    public MagneticMountainEffect copy() {
        return new MagneticMountainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int countBattlefield = game.getBattlefield().getAllActivePermanents(filter2, game.getActivePlayerId(), game).size();
            while (player.canRespond() && countBattlefield > 0 && player.chooseUse(Outcome.AIDontUseIt, "Pay {4} and untap a tapped blue creature under your control?", source, game)) {
                Target tappedCreatureTarget = new TargetControlledCreaturePermanent(1, 1, filter2, true);
                if (player.choose(Outcome.Detriment, tappedCreatureTarget, source.getSourceId(), game)) {
                    GenericManaCost cost = new GenericManaCost(4);
                    Permanent tappedCreature = game.getPermanent(tappedCreatureTarget.getFirstTarget());

                    if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                        tappedCreature.untap(game);
                    }
                }
                countBattlefield = game.getBattlefield().getAllActivePermanents(filter2, game.getActivePlayerId(), game).size();
            }
            return true;
        }
        return false;
    }
}
