package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatchedPlaything extends CardImpl {

    public PatchedPlaything(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.TOY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Patched Plaything enters with two -1/-1 counters on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)),
                CastFromHandSourcePermanentCondition.instance, null,
                "with two -1/-1 counters on it if you cast it from your hand"
        ), new CastFromHandWatcher());
    }

    private PatchedPlaything(final PatchedPlaything card) {
        super(card);
    }

    @Override
    public PatchedPlaything copy() {
        return new PatchedPlaything(this);
    }
}
