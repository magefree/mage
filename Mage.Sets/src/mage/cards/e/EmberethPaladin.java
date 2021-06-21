package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberethPaladin extends CardImpl {

    public EmberethPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Adamant — If at least three red mana was spent to cast this spell, Embereth Paladin enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, AdamantCondition.RED,
                "<br><i>Adamant</i> &mdash; If at least three red mana was spent to cast this spell, " +
                        "{this} enters the battlefield with a +1/+1 counter on it.", ""
        ));
    }

    private EmberethPaladin(final EmberethPaladin card) {
        super(card);
    }

    @Override
    public EmberethPaladin copy() {
        return new EmberethPaladin(this);
    }
}
