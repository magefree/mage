package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDestinedWhiteMage extends CardImpl {

    public TheDestinedWhiteMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {W}, {T}: Another target creature you control gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(LifelinkAbility.getInstance()), new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // Whenever you gain life, put a +1/+1 counter on target creature you control. If you have a full party, put three +1/+1 counters on that creature instead.
        ability = new GainLifeControllerTriggeredAbility(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)),
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)),
                FullPartyCondition.instance, "put a +1/+1 counter on target creature you control. " +
                "If you have a full party, put three +1/+1 counters on that creature instead"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(PartyCountHint.instance));
    }

    private TheDestinedWhiteMage(final TheDestinedWhiteMage card) {
        super(card);
    }

    @Override
    public TheDestinedWhiteMage copy() {
        return new TheDestinedWhiteMage(this);
    }
}
