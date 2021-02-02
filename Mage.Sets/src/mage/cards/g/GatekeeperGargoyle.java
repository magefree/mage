package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.GateYouControlCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.GateYouControlHint;
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
public final class GatekeeperGargoyle extends CardImpl {

    public GatekeeperGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Gatekeeper Gargoyle enters the battlefield with a +1/+1 counter on it for each Gate you control.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(),
                        GateYouControlCount.instance, true
                ), "with a +1/+1 counter on it for each Gate you control"
        ).addHint(GateYouControlHint.instance));
    }

    private GatekeeperGargoyle(final GatekeeperGargoyle card) {
        super(card);
    }

    @Override
    public GatekeeperGargoyle copy() {
        return new GatekeeperGargoyle(this);
    }
}
