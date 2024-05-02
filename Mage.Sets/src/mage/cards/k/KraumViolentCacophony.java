package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class KraumViolentCacophony extends CardImpl {

    public KraumViolentCacophony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast your second spell each turn, put a +1/+1 counter on Kraum, Violent Cacophony and draw a card.
        Ability ability = new CastSecondSpellTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private KraumViolentCacophony(final KraumViolentCacophony card) {
        super(card);
    }

    @Override
    public KraumViolentCacophony copy() {
        return new KraumViolentCacophony(this);
    }
}
