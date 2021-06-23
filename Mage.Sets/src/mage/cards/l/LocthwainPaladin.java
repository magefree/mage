package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LocthwainPaladin extends CardImpl {

    public LocthwainPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Adamant — If at least three black mana was spent to cast this spell, Locthwain Paladin enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                AdamantCondition.BLACK, "<br><i>Adamant</i> &mdash; " +
                "If at least three black mana was spent to cast this spell, " +
                "{this} enters the battlefield with a +1/+1 counter on it.", ""
        ));
    }

    private LocthwainPaladin(final LocthwainPaladin card) {
        super(card);
    }

    @Override
    public LocthwainPaladin copy() {
        return new LocthwainPaladin(this);
    }
}
