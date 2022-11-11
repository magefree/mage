package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YotianDissident extends CardImpl {

    public YotianDissident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an artifact enters the battlefield under your control, put +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private YotianDissident(final YotianDissident card) {
        super(card);
    }

    @Override
    public YotianDissident copy() {
        return new YotianDissident(this);
    }
}
