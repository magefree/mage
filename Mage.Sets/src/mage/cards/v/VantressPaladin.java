package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VantressPaladin extends CardImpl {

    public VantressPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Adamant — If at least three blue mana was spent to cast this spell, Vantress Paladin enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                AdamantCondition.BLUE, "<br><i>Adamant</i> &mdash; " +
                "If at least three blue mana was spent to cast this spell, " +
                "{this} enters the battlefield with a +1/+1 counter on it.", ""
        ));
    }

    private VantressPaladin(final VantressPaladin card) {
        super(card);
    }

    @Override
    public VantressPaladin copy() {
        return new VantressPaladin(this);
    }
}
