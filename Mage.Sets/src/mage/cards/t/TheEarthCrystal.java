package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEarthCrystal extends CardImpl {

    private static final FilterCard filter = new FilterCard("green spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public TheEarthCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // Green spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // If one or more +1/+1 counters would be put on a creature you control, twice that many +1/+1 counters are put on that creature instead.
        this.addAbility(new SimpleStaticAbility(new TheEarthCrystalEffect()));

        // {4}{G}{G}, {T}: Distribute two +1/+1 counters among one or two target creatures you control.
        Ability ability = new SimpleActivatedAbility(
                new DistributeCountersEffect(CounterType.P1P1), new ManaCostsImpl<>("{4}{G}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanentAmount(
                2, 1, 2,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ));
        this.addAbility(ability);
    }

    private TheEarthCrystal(final TheEarthCrystal card) {
        super(card);
    }

    @Override
    public TheEarthCrystal copy() {
        return new TheEarthCrystal(this);
    }
}

class TheEarthCrystalEffect extends ReplacementEffectImpl {

    TheEarthCrystalEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "if one or more +1/+1 counters would be put on a creature you control, " +
                "twice that many +1/+1 counters are put on that creature instead";
    }

    private TheEarthCrystalEffect(final TheEarthCrystalEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowMultiply(event.getAmount(), 2), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!CounterType.P1P1.getName().equals(event.getData()) || event.getAmount() < 1) {
            return false;
        }
        Permanent permanent = Optional
                .ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getPermanent)
                .orElseGet(() -> game.getPermanentEntering(event.getTargetId()));
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.isCreature(game);
    }

    @Override
    public TheEarthCrystalEffect copy() {
        return new TheEarthCrystalEffect(this);
    }
}
