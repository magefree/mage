package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetAmount;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlessingOfFrost extends CardImpl {

    public BlessingOfFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        this.addSuperType(SuperType.SNOW);

        // Distribute X +1/+1 counters among any number of creatures you control, where X is the amount of {S} spent to cast this spell. Then draw a card for each creature you control with power 4 or greater.
        this.getSpellAbility().addEffect(new BlessingOfFrostEffect());
    }

    private BlessingOfFrost(final BlessingOfFrost card) {
        super(card);
    }

    @Override
    public BlessingOfFrost copy() {
        return new BlessingOfFrost(this);
    }
}

class BlessingOfFrostEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    BlessingOfFrostEffect() {
        super(Outcome.Benefit);
        staticText = "Distribute X +1/+1 counters among any number of creatures you control, " +
                "where X is the amount of {S} spent to cast this spell. " +
                "Then draw a card for each creature you control with power 4 or greater.";
    }

    private BlessingOfFrostEffect(final BlessingOfFrostEffect effect) {
        super(effect);
    }

    @Override
    public BlessingOfFrostEffect copy() {
        return new BlessingOfFrostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int snow = ManaPaidSourceWatcher.getSnowPaid(source.getId(), game);
        if (snow > 0) {
            TargetAmount target = new TargetCreaturePermanentAmount(snow, StaticFilters.FILTER_CONTROLLED_CREATURE);
            target.setNotTarget(true);
            target.chooseTarget(outcome, player.getId(), source, game);
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    continue;
                }
                permanent.addCounters(CounterType.P1P1.createInstance(target.getTargetAmount(targetId)), source.getControllerId(), source, game);
            }
        }
        game.getState().processAction(game);
        player.drawCards(game.getBattlefield().count(
                filter, source.getSourceId(), source.getControllerId(), game
        ), source, game);
        return true;
    }
}
