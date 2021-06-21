package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.AdamantCondition;
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
public final class ArdenvalePaladin extends CardImpl {

    public ArdenvalePaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Adamant — If at least three white mana was spent to cast this spell, Ardenvale Paladin enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                AdamantCondition.WHITE, "<br><i>Adamant</i> &mdash; " +
                "If at least three white mana was spent to cast this spell, " +
                "{this} enters the battlefield with a +1/+1 counter on it.", ""
        ));
    }

    private ArdenvalePaladin(final ArdenvalePaladin card) {
        super(card);
    }

    @Override
    public ArdenvalePaladin copy() {
        return new ArdenvalePaladin(this);
    }
}
