package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Viserion
 */
public final class PhyrexianVatmother extends CardImpl {

    public PhyrexianVatmother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersPlayersEffect(
                        CounterType.POISON.createInstance(), TargetController.YOU
                ), TargetController.YOU, false
        ));
    }

    public PhyrexianVatmother(final PhyrexianVatmother card) {
        super(card);
    }

    @Override
    public PhyrexianVatmother copy() {
        return new PhyrexianVatmother(this);
    }
}
