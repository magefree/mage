
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Loki
 */
public final class Nightmare extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Swamps you control");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public Nightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORSE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        
        // Flying        
        this.addAbility(FlyingAbility.getInstance());
        
        // Nightmare's power and toughness are each equal to the number of Swamps you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    public Nightmare(final Nightmare card) {
        super(card);
    }

    @Override
    public Nightmare copy() {
        return new Nightmare(this);
    }
}
