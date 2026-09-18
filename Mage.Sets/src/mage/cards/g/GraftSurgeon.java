package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.effects.common.PutSourceCountersOnTargetEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GraftSurgeon extends CardImpl {

    public GraftSurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature enters with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(1)));

        // When this creature dies, put its counters on up to one target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new PutSourceCountersOnTargetEffect()).setTriggerPhrase("When this creature dies, ");
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private GraftSurgeon(final GraftSurgeon card) {
        super(card);
    }

    @Override
    public GraftSurgeon copy() {
        return new GraftSurgeon(this);
    }
}
