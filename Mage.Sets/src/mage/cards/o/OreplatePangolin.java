package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OreplatePangolin extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public OreplatePangolin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.PANGOLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another artifact you control enters, you may pay {1}. If you do, put a +1/+1 counter on this creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        new GenericManaCost(1)
                ), filter)
        );
    }

    private OreplatePangolin(final OreplatePangolin card) {
        super(card);
    }

    @Override
    public OreplatePangolin copy() {
        return new OreplatePangolin(this);
    }
}
