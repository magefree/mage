package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;


/**
 * @author LevelX2
 */
public final class WaxmaneBaku extends CardImpl {

    public WaxmaneBaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Waxmane Baku.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));

        // {1}, Remove X ki counters from Waxmane Baku: Tap X target creatures.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect("tap X target creatures"), new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new XTargetsCountAdjuster());
        this.addAbility(ability);
    }

    private WaxmaneBaku(final WaxmaneBaku card) {
        super(card);
    }

    @Override
    public WaxmaneBaku copy() {
        return new WaxmaneBaku(this);
    }
}
