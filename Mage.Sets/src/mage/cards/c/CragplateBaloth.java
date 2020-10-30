package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CragplateBaloth extends CardImpl {

    public CragplateBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Kicker {2}{G}
        this.addAbility(new KickerAbility("{2}{G}"));

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredAbility());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If Cragplate Baloth was kicked, it enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)), KickedCondition.instance,
                "If {this} was kicked, it enters the battlefield with four +1/+1 counters on it.", ""
        ));
    }

    private CragplateBaloth(final CragplateBaloth card) {
        super(card);
    }

    @Override
    public CragplateBaloth copy() {
        return new CragplateBaloth(this);
    }
}
