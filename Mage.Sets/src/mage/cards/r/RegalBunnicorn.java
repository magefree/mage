package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class RegalBunnicorn extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland permanents you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final DynamicValue starValue = new PermanentsOnBattlefieldCount(filter);

    public RegalBunnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Regal Bunnicorn's power and toughness are each equal to the number of nonland permanents you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(starValue)));
    }

    private RegalBunnicorn(final RegalBunnicorn card) {
        super(card);
    }

    @Override
    public RegalBunnicorn copy() {
        return new RegalBunnicorn(this);
    }
}
