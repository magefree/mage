package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.UseAttachedCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hankyu extends CardImpl {

    public Hankyu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{T}: Put an aim counter on {this}" and "{T}, Remove all aim counters from {this}: This creature deals damage to any target equal to the number of aim counters removed this way."
        this.addAbility(new SimpleStaticAbility(new HankyuEffect()));

        // Equip {4} ({4}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4)));
    }

    private Hankyu(final Hankyu card) {
        super(card);
    }

    @Override
    public Hankyu copy() {
        return new Hankyu(this);
    }
}

class HankyuEffect extends ContinuousEffectImpl {

    HankyuEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "equipped creature has \"{T}: Put an aim counter on {this}\" and " +
                "\"{T}, Remove all aim counters from {this}: This creature deals damage " +
                "to any target equal to the number of aim counters removed this way.\"";
    }

    private HankyuEffect(final HankyuEffect effect) {
        super(effect);
    }

    @Override
    public HankyuEffect copy() {
        return new HankyuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Permanent creature = game.getPermanent(permanent.getAttachedTo());
        if (creature == null) {
            return false;
        }
        creature.addAbility(new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.AIM.createInstance())
                        .setTargetPointer(new FixedTarget(permanent, game))
                        .setText("put an aim counter on " + permanent.getName()),
                new TapSourceCost()
        ), source.getSourceId(), game);
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(HankyuValue.instance)
                        .setText("this creature deals damage to any target equal " +
                                "to the number of aim counters removed this way"),
                new TapSourceCost()
        );
        ability.addCost(new HankyuCost().setMageObjectReference(source, game));
        ability.addTarget(new TargetAnyTarget());
        creature.addAbility(ability, source.getSourceId(), game);
        return true;
    }
}

enum HankyuValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.castStream(
                sourceAbility.getCosts().stream(), HankyuCost.class
        ).mapToInt(HankyuCost::getRemovedCounters).sum();
    }

    @Override
    public HankyuValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class HankyuCost extends UseAttachedCost {

    private int removedCounters = 0;

    HankyuCost() {
        super();
    }

    private HankyuCost(final HankyuCost cost) {
        super(cost);
        this.removedCounters = cost.removedCounters;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (mageObjectReference == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return paid;
        }
        for (UUID attachmentId : permanent.getAttachments()) {
            if (!this.mageObjectReference.refersTo(attachmentId, game)) {
                continue;
            }
            Permanent equipment = mageObjectReference.getPermanent(game);
            if (equipment == null) {
                continue;
            }
            int count = equipment.getCounters(game).getCount(CounterType.AIM);
            equipment.removeCounters(CounterType.AIM.createInstance(count), source, game);
            paid = true;
            removedCounters = count;
            break;
        }

        return paid;
    }

    @Override
    public HankyuCost copy() {
        return new HankyuCost(this);
    }

    @Override
    public String getText() {
        return "remove all aim counters from " + this.name;
    }

    public int getRemovedCounters() {
        return removedCounters;
    }
}