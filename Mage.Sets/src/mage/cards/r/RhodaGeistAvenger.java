package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.TappedNotAttackingTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class RhodaGeistAvenger extends CardImpl {

    public RhodaGeistAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Timin, Youthful Geist
        this.addAbility(new PartnerWithAbility("Timin, Youthful Geist"));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a creature an opponent controls becomes tapped, if it isn't being declared as an attacker, put a +1/+1 counter on Rhoda, Geist Avenger.
        this.addAbility(new TappedNotAttackingTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private RhodaGeistAvenger(final RhodaGeistAvenger card) {
        super(card);
    }

    @Override
    public RhodaGeistAvenger copy() {
        return new RhodaGeistAvenger(this);
    }
}
