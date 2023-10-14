package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OathswornKnight extends CardImpl {

    public OathswornKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Oathsworn Knight enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(4)
        ), "with four +1/+1 counters on it"));

        // Oathsworn Knight attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // If damage would be dealt to Oathsworn Knight while it has a +1/+1 counter on it, prevent that damage and remove a +1/+1 counter from it.
        this.addAbility(new SimpleStaticAbility(new PreventDamageAndRemoveCountersEffect(false, true, true)));
    }

    private OathswornKnight(final OathswornKnight card) {
        super(card);
    }

    @Override
    public OathswornKnight copy() {
        return new OathswornKnight(this);
    }
}
