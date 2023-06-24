package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuinousIntrusion extends CardImpl {

    public RuinousIntrusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Exile target artifact or enchantment. Put X +1/+1 counters on target creature you control, where X is the mana value of the permanent exiled this way.
        this.getSpellAbility().addEffect(new RuinousIntrusionEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private RuinousIntrusion(final RuinousIntrusion card) {
        super(card);
    }

    @Override
    public RuinousIntrusion copy() {
        return new RuinousIntrusion(this);
    }
}

class RuinousIntrusionEffect extends OneShotEffect {

    RuinousIntrusionEffect() {
        super(Outcome.Benefit);
        staticText = "exile target artifact or enchantment. Put X +1/+1 counters on target creature you control, " +
                "where X is the mana value of the permanent exiled this way";
    }

    private RuinousIntrusionEffect(final RuinousIntrusionEffect effect) {
        super(effect);
    }

    @Override
    public RuinousIntrusionEffect copy() {
        return new RuinousIntrusionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        int mv = permanent.getManaValue();
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (mv < 1) {
            return true;
        }
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(mv), source, game);
        }
        return true;
    }
}
