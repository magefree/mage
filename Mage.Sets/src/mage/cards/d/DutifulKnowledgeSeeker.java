package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DutifulKnowledgeSeeker extends CardImpl {

    public DutifulKnowledgeSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more cards are put into a library from anywhere, put a +1/+1 counter on this creature.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // {3}: Put target card from a graveyard on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(false), new GenericManaCost(3));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private DutifulKnowledgeSeeker(final DutifulKnowledgeSeeker card) {
        super(card);
    }

    @Override
    public DutifulKnowledgeSeeker copy() {
        return new DutifulKnowledgeSeeker(this);
    }
}
