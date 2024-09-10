package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class IchorclawMyr extends CardImpl {

    public IchorclawMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(InfectAbility.getInstance());
        // Whenever Ichorclaw Myr becomes blocked, it gets +2/+2 until end of turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn, "it"), false
        ));
    }

    private IchorclawMyr(final IchorclawMyr card) {
        super(card);
    }

    @Override
    public IchorclawMyr copy() {
        return new IchorclawMyr(this);
    }
}
