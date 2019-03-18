
package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class DemolitionStomper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less");
    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public DemolitionStomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");
        this.subtype.add( SubType.VEHICLE);
        this.power = new MageInt(10);
        this.toughness = new MageInt(7);

        // Demolition Stomper can't be blocked by creatures with power 2 or less.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Crew 5
        this.addAbility(new CrewAbility(5));
    }

    public DemolitionStomper(final DemolitionStomper card) {
        super(card);
    }

    @Override
    public DemolitionStomper copy() {
        return new DemolitionStomper(this);
    }
}
