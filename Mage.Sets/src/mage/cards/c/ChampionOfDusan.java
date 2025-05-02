package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.RenewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChampionOfDusan extends CardImpl {

    public ChampionOfDusan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Renew -- {1}{G}, Exile this card from your graveyard: Put a +1/+1 counter and a trample counter on target creature. Activate only as a sorcery.
        this.addAbility(new RenewAbility(
                "{1}{G}",
                CounterType.P1P1.createInstance(),
                CounterType.TRAMPLE.createInstance()
        ));
    }

    private ChampionOfDusan(final ChampionOfDusan card) {
        super(card);
    }

    @Override
    public ChampionOfDusan copy() {
        return new ChampionOfDusan(this);
    }
}
