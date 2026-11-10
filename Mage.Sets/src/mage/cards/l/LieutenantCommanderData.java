package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class LieutenantCommanderData extends CardImpl {

    public LieutenantCommanderData(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANDROID);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player casts a noncreature spell, put a +1/+1 counter on Lieutenant Commander Data.
        this.addAbility(new SpellCastAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_SPELL_NON_CREATURE, false
        ));

        // {1}, Remove three counters from among creatures you control: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(1, 3), null, 3));
        this.addAbility(ability);
    }

    private LieutenantCommanderData(final LieutenantCommanderData card) {
        super(card);
    }

    @Override
    public LieutenantCommanderData copy() {
        return new LieutenantCommanderData(this);
    }
}
