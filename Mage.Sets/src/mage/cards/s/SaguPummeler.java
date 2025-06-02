package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.RenewAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaguPummeler extends CardImpl {

    public SaguPummeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Renew -- {4}{G}, Exile this card from your graveyard: Put two +1/+1 counters and a reach counter on target creature. Activate only as a sorcery.
        this.addAbility(new RenewAbility(
                "{4}{G}",
                CounterType.P1P1.createInstance(2),
                CounterType.REACH.createInstance()
        ));
    }

    private SaguPummeler(final SaguPummeler card) {
        super(card);
    }

    @Override
    public SaguPummeler copy() {
        return new SaguPummeler(this);
    }
}
