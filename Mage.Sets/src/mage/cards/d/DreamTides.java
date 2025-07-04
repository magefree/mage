package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author spjspj & L_J
 */
public final class DreamTides extends CardImpl {

    public DreamTides(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, StaticFilters.FILTER_PERMANENT_CREATURES)));

        // At the beginning of each player's upkeep, that player may choose any number of tapped nongreen creatures they control and pay {2} for each creature chosen this way. If the player does, untap those creatures.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(TargetController.EACH_PLAYER, new DreamTidesEffect(), false));
    }

    private DreamTides(final DreamTides card) {
        super(card);
    }

    @Override
    public DreamTides copy() {
        return new DreamTides(this);
    }
}

class DreamTidesEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("tapped nongreen creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.GREEN)));
        filter.add(TappedPredicate.TAPPED);
    }

    DreamTidesEffect() {
        super(Outcome.Benefit);
        staticText = "that player may choose any number of tapped nongreen creatures they control and pay {2} for each creature chosen this way. If the player does, untap those creatures";
    }

    private DreamTidesEffect(final DreamTidesEffect effect) {
        super(effect);
    }

    @Override
    public DreamTidesEffect copy() {
        return new DreamTidesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player == null || sourcePermanent == null) {
            return false;
        }
        int countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game).size();
        while (player.canRespond() && countBattlefield > 0 && player.chooseUse(Outcome.AIDontUseIt, "Pay {2} and untap a tapped nongreen creature under your control?", source, game)) {
            Target tappedCreatureTarget = new TargetPermanent(filter);
            tappedCreatureTarget.withNotTarget(true);
            if (player.choose(Outcome.Detriment, tappedCreatureTarget, source, game)) {
                Cost cost = ManaUtil.createManaCost(2, false);
                Permanent tappedCreature = game.getPermanent(tappedCreatureTarget.getFirstTarget());

                if (cost.pay(source, game, source, player.getId(), false)) {
                    tappedCreature.untap(game);
                }
            }
            countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game).size();
        }
        return true;
    }
}
