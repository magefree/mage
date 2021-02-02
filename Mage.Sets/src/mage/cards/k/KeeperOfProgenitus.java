    
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Plopman
 */
public final class KeeperOfProgenitus extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a player taps a Mountain, Forest, or Plains");

    static {
        filter.add(Predicates.or(
            SubType.MOUNTAIN.getPredicate(),
            SubType.FOREST.getPredicate(),
            SubType.PLAINS.getPredicate()
            ));
    }

    public KeeperOfProgenitus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a player taps a Mountain, Forest, or Plains for mana, that player adds one mana of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(),
                filter, SetTargetPointer.PERMANENT));
    }

    private KeeperOfProgenitus(final KeeperOfProgenitus card) {
        super(card);
    }

    @Override
    public KeeperOfProgenitus copy() {
        return new KeeperOfProgenitus(this);
    }
}
