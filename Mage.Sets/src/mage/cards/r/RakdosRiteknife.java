package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 * @author TheElk801
 */
public final class RakdosRiteknife extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.BLOOD);

    public RakdosRiteknife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each blood counter on Rakdos Riteknife and has "{T}, Sacrifice a creature: Put a blood counter on Rakdos Riteknife."
        this.addAbility(new SimpleStaticAbility(new RakdosRiteknifeEffect()));

        // {B}{R}, Sacrifice Rakdos Riteknife: Target player sacrifices a permanent for each blood counter on Rakdos Riteknife.
        Ability ability = new SimpleActivatedAbility(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT, xValue, "Target player")
                        .setText("target player sacrifices a permanent for each blood counter on {this}"),
                new ManaCostsImpl<>("{R}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private RakdosRiteknife(final RakdosRiteknife card) {
        super(card);
    }

    @Override
    public RakdosRiteknife copy() {
        return new RakdosRiteknife(this);
    }
}

class RakdosRiteknifeEffect extends ContinuousEffectImpl {

    RakdosRiteknifeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AddAbility);
        staticText = "equipped creature gets +1/+0 for each blood counter on {this} " +
                "and has \"{T}, Sacrifice a creature: Put a blood counter on {this}.\"";
    }

    private RakdosRiteknifeEffect(final RakdosRiteknifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public RakdosRiteknifeEffect copy() {
        return new RakdosRiteknifeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Permanent creature = game.getPermanent(permanent.getAttachedTo());
        if (creature == null) {
            return false;
        }
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
                creature.addAbility(makeAbility(permanent, game), source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer != SubLayer.ModifyPT_7c) {
                    return false;
                }
                int count = permanent.getCounters(game).getCount(CounterType.BLOOD);
                if (count < 1) {
                    creature.getPower().boostValue(count);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.PTChangingEffects_7;
    }

    private static Ability makeAbility(Permanent permanent, Game game) {
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.BLOOD.createInstance())
                        .setText("put a blood counter on " + permanent.getName())
                        .setTargetPointer(new FixedTarget(permanent, game)),
                new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        return ability;
    }
}
