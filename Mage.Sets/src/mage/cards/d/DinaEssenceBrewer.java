package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DinaEssenceBrewer extends CardImpl {

    public DinaEssenceBrewer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you sacrifice a creature, draw a card. This ability only triggers once each turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(
            new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE
        ).setTriggersLimitEachTurn(1));

        // {2}, {T}, Sacrifice another creature: You gain X life and put X +1/+1 counters on target creature you control, where X is the sacrificed creature's power.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(SacrificeCostCreaturesPower.instance)
            .setText("you gain X life"), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(), SacrificeCostCreaturesPower.instance)
            .setText("and put X +1/+1 counters on target creature you control, where X is the sacrificed creature's power"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private DinaEssenceBrewer(final DinaEssenceBrewer card) {
        super(card);
    }

    @Override
    public DinaEssenceBrewer copy() {
        return new DinaEssenceBrewer(this);
    }
}
