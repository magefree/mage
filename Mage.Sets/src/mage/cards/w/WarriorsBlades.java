package mage.cards.w;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WarriorsBlades extends CardImpl {

    public WarriorsBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, it deals 3 damage to any target and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {3}. This ability costs {1} less to activate for each +1/+1 counter on the creature it targets.
        EquipAbility equipAbility = new EquipAbility(3, false);
        equipAbility.setCostAdjuster(WarriorsBladesCostAdjuster.instance);
        equipAbility.setCostReduceText("This ability costs {1} less to activate for each +1/+1 counter on the creature it targets.");
        this.addAbility(equipAbility);
    }

    private WarriorsBlades(final WarriorsBlades card) {
        super(card);
    }

    @Override
    public WarriorsBlades copy() {
        return new WarriorsBlades(this);
    }
}

enum WarriorsBladesCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        int reduceCount = 0;
        if (game.inCheckPlayableState()) {
            reduceCount = game.getBattlefield().getAllActivePermanents(ability.getControllerId())
                    .stream()
                    .filter(Permanent::isCreature)
                    .mapToInt(permanent -> permanent.getCounters(game).getCount(CounterType.P1P1))
                    .max()
                    .orElse(reduceCount);
        }
        else {
            Permanent target = game.getPermanent(ability.getFirstTarget());
            if (target != null) {
                reduceCount = target.getCounters(game).getCount(CounterType.P1P1);
            }
        }
        CardUtil.reduceCost(ability, reduceCount);
    }
}
