package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 *
 * @author noahg
 */
public final class RakdosRiteknife extends CardImpl {

    public RakdosRiteknife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each blood counter on Rakdos Riteknife and has "{T}, Sacrifice a creature: Put a blood counter on Rakdos Riteknife."
        SimpleStaticAbility staticAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(new CountersSourceCount(CounterType.BLOOD), StaticValue.get(0)).setText("Equipped creature gets +1/+0 for each blood counter on {this}"));
        SimpleActivatedAbility grantedAbility = new SimpleActivatedAbility(new RakdosRiteKnifeEffect(this.getId()), new TapSourceCost());
        grantedAbility.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        staticAbility.addEffect(new GainAbilityAttachedEffect(grantedAbility, AttachmentType.EQUIPMENT).setText("and has \"{T}, Sacrifice a creature: Put a blood counter on {this}.\""));
        this.addAbility(staticAbility);

        // {B}{R}, Sacrifice Rakdos Riteknife: Target player sacrifices a permanent for each blood counter on Rakdos Riteknife.
        SimpleActivatedAbility activatedAbility = new SimpleActivatedAbility(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT, new CountersSourceCount(CounterType.BLOOD), "Target player")
                        .setText("target player sacrifices a permanent for each blood counter on {this}"), new ManaCostsImpl("{R}{B}"));
        activatedAbility.addCost(new SacrificeSourceCost());
        activatedAbility.addTarget(new TargetPlayer());
        this.addAbility(activatedAbility);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private RakdosRiteknife(final RakdosRiteknife card) {
        super(card);
    }

    @Override
    public RakdosRiteknife copy() {
        return new RakdosRiteknife(this);
    }
}

class RakdosRiteKnifeEffect extends OneShotEffect {

    private UUID effectGivingEquipmentId;

    public RakdosRiteKnifeEffect(UUID effectGivingEquipmentId) {
        super(Outcome.Benefit);
        this.effectGivingEquipmentId = effectGivingEquipmentId;
        staticText = "Put a blood counter on Rakdos Riteknife";
    }

    public RakdosRiteKnifeEffect(final RakdosRiteKnifeEffect effect) {
        super(effect);
        this.effectGivingEquipmentId = effect.effectGivingEquipmentId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(this.effectGivingEquipmentId);
        if (equipment != null) {
            equipment.addCounters(CounterType.BLOOD.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }

    @Override
    public RakdosRiteKnifeEffect copy() {
        return new RakdosRiteKnifeEffect(this);
    }


}