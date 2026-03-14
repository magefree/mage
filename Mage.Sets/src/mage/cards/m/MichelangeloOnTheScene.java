package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MichelangeloOnTheScene extends CardImpl {

    public MichelangeloOnTheScene(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Michelangelo enters with a +1/+1 counter on him for each land you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), LandsYouControlCount.instance
        ), "with a +1/+1 counter on him for each land you control").addHint(LandsYouControlHint.instance));

        // When Michelangelo dies, return this card to your hand.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnSourceFromGraveyardToHandEffect()));
    }

    private MichelangeloOnTheScene(final MichelangeloOnTheScene card) {
        super(card);
    }

    @Override
    public MichelangeloOnTheScene copy() {
        return new MichelangeloOnTheScene(this);
    }
}
