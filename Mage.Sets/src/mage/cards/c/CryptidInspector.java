package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.card.FaceDownPredicate;

import java.util.UUID;

/**
 * @author jackd149
 */
public final class CryptidInspector extends CardImpl {
    private static final FilterPermanent filter1 = new FilterPermanent("a face-down permanent");
    private static final FilterPermanent filter2 = new FilterControlledPermanent();

    static {
        filter1.add(FaceDownPredicate.instance);
    }

    public CryptidInspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a face-down permanent you control enters and whenever Cryptid Inspector or another permanent you control is turned face up,
        // put a +1/+1 counter on Cryptid Inspector.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false,
                "Whenever a face-down permanent you control enters and "
                        + "whenever {this} or another permanent you control is turned face up, ",
                new EntersBattlefieldControlledTriggeredAbility(null, filter1),
                new TurnedFaceUpAllTriggeredAbility(null, filter2)
        ));
    }

    private CryptidInspector(final CryptidInspector card) {
        super(card);
    }

    @Override
    public CryptidInspector copy() {
        return new CryptidInspector(this);
    }
}
