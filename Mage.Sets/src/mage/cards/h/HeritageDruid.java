
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class HeritageDruid extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Elves you control");

    static {
        filter.add(Predicates.not(TappedPredicate.instance));
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public HeritageDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tap three untapped Elves you control: Add {G}{G}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(3), new TapTargetCost(new TargetControlledPermanent(3, 3, filter, true))));
    }

    public HeritageDruid(final HeritageDruid card) {
        super(card);
    }

    @Override
    public HeritageDruid copy() {
        return new HeritageDruid(this);
    }
}
