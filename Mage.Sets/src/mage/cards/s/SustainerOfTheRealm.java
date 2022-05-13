package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class SustainerOfTheRealm extends CardImpl {

    public SustainerOfTheRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Sustainer of the Realm blocks, it gets +0/+2 until end of turn.
        this.addAbility(new BlocksSourceTriggeredAbility(new BoostSourceEffect(0, 2, Duration.EndOfTurn, "it")));
    }

    private SustainerOfTheRealm(final SustainerOfTheRealm card) {
        super(card);
    }

    @Override
    public SustainerOfTheRealm copy() {
        return new SustainerOfTheRealm(this);
    }
}
