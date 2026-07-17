package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.keyword.FlashAbility;
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
public final class SwoopingProtector extends CardImpl {

    public SwoopingProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Swooping Protector enters the battlefield with a shield counter on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.SHIELD.createInstance(1)));
    }

    private SwoopingProtector(final SwoopingProtector card) {
        super(card);
    }

    @Override
    public SwoopingProtector copy() {
        return new SwoopingProtector(this);
    }
}
