
package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
 * @author spjspj & L_J
 */
public final class ThelonsCurse extends CardImpl {

    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("blue creatures");

    static {
        filterCreature.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ThelonsCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // Blue creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filterCreature)));

        // At the beginning of each player's upkeep, that player may choose any number of tapped blue creatures they control and pay {U} for each creature chosen this way. If the player does, untap those creatures.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ThelonsCurseEffect(), TargetController.ANY, false));
    }

    private ThelonsCurse(final ThelonsCurse card) {
        super(card);
    }

    @Override
    public ThelonsCurse copy() {
        return new ThelonsCurse(this);
    }
}

class ThelonsCurseEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("tapped blue creature");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    ThelonsCurseEffect() {
        super(Outcome.Benefit);
        staticText = "that player may choose any number of tapped blue creatures they control and pay {U} for each creature chosen this way. If the player does, untap those creatures.";
    }

    ThelonsCurseEffect(ThelonsCurseEffect effect) {
        super(effect);
    }

    @Override
    public ThelonsCurseEffect copy() {
        return new ThelonsCurseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game).size();
            while (player.canRespond() && countBattlefield > 0 && player.chooseUse(Outcome.AIDontUseIt, "Pay {U} and untap a tapped blue creature under your control?", source, game)) {
                Target tappedCreatureTarget = new TargetControlledCreaturePermanent(1, 1, filter, true);
                if (player.choose(Outcome.Detriment, tappedCreatureTarget, source, game)) {
                    Cost cost = new ManaCostsImpl<>("{U}");
                    Permanent tappedCreature = game.getPermanent(tappedCreatureTarget.getFirstTarget());

                    if (cost.pay(source, game, source, player.getId(), false)) {
                        tappedCreature.untap(game);
                    }
                }
                countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game).size();
            }
            return true;
        }
        return false;
    }
}
