package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * FAQ 2013/01/11
 * <p>
 * 702.98. Evolve
 * <p>
 * 702.98a Evolve is a triggered ability. "Evolve" means "Whenever a creature
 * enters the battlefield under your control, if that creature's power is
 * greater than this creature's power and/or that creature's toughness is
 * greater than this creature's toughness, put a +1/+1 counter on this
 * creature."
 * <p>
 * 702.98b If a creature has multiple instances of evolve, each triggers
 * separately
 * <p>
 * Rulings
 * <p>
 * When comparing the stats of the two creatures, you always compare power to
 * power and toughness to toughness. Whenever a creature enters the battlefield
 * under your control, check its power and toughness against the power and
 * toughness of the creature with evolve. If neither stat of the new creature is
 * greater, evolve won't trigger at all. For example, if you control a 2/3
 * creature with evolve and a 2/2 creature enters the battlefield under your
 * control, you won't have the opportunity to cast a spell like Giant Growth to
 * make the 2/2 creature large enough to cause evolve to trigger. If evolve
 * triggers, the stat comparison will happen again when the ability tries to
 * resolve. If neither stat of the new creature is greater, the ability will do
 * nothing. If the creature that entered the battlefield leaves the battlefield
 * before evolve tries to resolve, use its last known power and toughness to
 * compare the stats. If a creature enters the battlefield with +1/+1 counters
 * on it, consider those counters when determining if evolve will trigger. For
 * example, a 1/1 creature that enters the battlefield with two +1/+1 counters
 * on it will cause the evolve ability of a 2/2 creature to trigger. If multiple
 * creatures enter the battlefield at the same time, evolve may trigger multiple
 * times, although the stat comparison will take place each time one of those
 * abilities tries to resolve. For example, if you control a 2/2 creature with
 * evolve and two 3/3 creatures enter the battlefield, evolve will trigger
 * twice. The first ability will resolve and put a +1/+1 counter on the creature
 * with evolve. When the second ability tries to resolve, neither the power nor
 * the toughness of the new creature is greater than that of the creature with
 * evolve, so that ability does nothing. When comparing the stats as the evolve
 * ability resolves, it's possible that the stat that's greater changes from
 * power to toughness or vice versa. If this happens, the ability will still
 * resolve and you'll put a +1/+1 counter on the creature with evolve. For
 * example, if you control a 2/2 creature with evolve and a 1/3 creature enters
 * the battlefield under your control, it toughness is greater so evolve will
 * trigger. In response, the 1/3 creature gets +2/-2. When the evolve trigger
 * tries to resolve, its power is greater. You'll put a +1/+1 counter on the
 * creature with evolve.
 *
 * @author LevelX2
 */
public class EvolveAbility extends EntersBattlefieldAllTriggeredAbility {

    public EvolveAbility() {
        super(Zone.BATTLEFIELD, new EvolveEffect(), StaticFilters.FILTER_CONTROLLED_CREATURE, false);
    }

    public EvolveAbility(EvolveAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent sourcePermanent = getSourcePermanentOrLKI(game);
        Permanent permanentEntering = (Permanent) this
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("permanentEnteringBattlefield"))
                .findFirst()
                .orElse(null);
        return sourcePermanent != null
                && permanentEntering != null
                && sourcePermanent.isCreature(game)
                && permanentEntering.isCreature(game)
                && (permanentEntering.getPower().getValue() > sourcePermanent.getPower().getValue()
                || permanentEntering.getToughness().getValue() > sourcePermanent.getToughness().getValue());
    }

    @Override
    public String getRule() {
        return "Evolve <i>(Whenever a creature enters the battlefield under your control, if that creature has greater power or toughness than this creature, put a +1/+1 counter on this creature.)</i>";
    }

    @Override
    public EvolveAbility copy() {
        return new EvolveAbility(this);
    }
}

class EvolveEffect extends OneShotEffect {

    public EvolveEffect() {
        super(Outcome.BoostCreature);
    }

    public EvolveEffect(final EvolveEffect effect) {
        super(effect);
    }

    @Override
    public EvolveEffect copy() {
        return new EvolveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.EVOLVED_CREATURE,
                permanent.getId(), source, source.getControllerId()
        ));
        return false;
    }
}
