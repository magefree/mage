package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RancorousArchaic extends CardImpl {

    public RancorousArchaic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Converge -- This creature enters with a +1/+1 counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(), ColorsOfManaSpentToCastCount.getInstance()
                ), null, AbilityWord.CONVERGE.formatWord() + "{this} enters with a +1/+1 counter " +
                "on it for each color of mana spent to cast it.", null
        ));
        this.getSpellAbility().addHint(ColorsOfManaSpentToCastCount.getHint());
    }

    private RancorousArchaic(final RancorousArchaic card) {
        super(card);
    }

    @Override
    public RancorousArchaic copy() {
        return new RancorousArchaic(this);
    }
}
