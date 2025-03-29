package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.RenewAbility;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SageOfTheFang extends CardImpl {

    public SageOfTheFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Renew -- {3}{G}, Exile this card from your graveyard: Put a +1/+1 counter on target creature, then double the number of +1/+1 counters on that creature.
        ability = new RenewAbility("{3}{G}", CounterType.P1P1.createInstance());
        ability.addEffect(new DoubleCountersTargetEffect(CounterType.P1P1)
                .setText(", then double the number of +1/+1 counters on that creature"));
        this.addAbility(ability);
    }

    private SageOfTheFang(final SageOfTheFang card) {
        super(card);
    }

    @Override
    public SageOfTheFang copy() {
        return new SageOfTheFang(this);
    }
}
