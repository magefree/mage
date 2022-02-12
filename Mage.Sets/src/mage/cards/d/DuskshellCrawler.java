package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class DuskshellCrawler extends CardImpl {

    public DuskshellCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Duskshell Crawler enters the battlefield, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Each creature you control with a +1/+1 counter on it has trample.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAllEffect(
                        TrampleAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1)
                )
        );
    }

    private DuskshellCrawler(final DuskshellCrawler card) {
        super(card);
    }

    @Override
    public DuskshellCrawler copy() {
        return new DuskshellCrawler(this);
    }
}
