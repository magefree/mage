package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoiceOfTheVermin extends CardImpl {

    public VoiceOfTheVermin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Voice of the Vermin enters the battlefield with a shield counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance(1)),
                "with a shield counter on it. <i>(If it would be dealt damage " +
                        "or destroyed, remove a shield counter from it instead.)</i>"
        ));

        // Whenever Voice of the Vermin attacks, target creature you control has base power and toughness 4/4 until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new SetBasePowerToughnessTargetEffect(4, 4, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private VoiceOfTheVermin(final VoiceOfTheVermin card) {
        super(card);
    }

    @Override
    public VoiceOfTheVermin copy() {
        return new VoiceOfTheVermin(this);
    }
}
