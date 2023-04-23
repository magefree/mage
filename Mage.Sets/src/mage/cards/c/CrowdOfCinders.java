
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class CrowdOfCinders extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("black permanents you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public CrowdOfCinders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(FearAbility.getInstance());
        // Crowd of Cinders's power and toughness are each equal to the number of black permanents you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private CrowdOfCinders(final CrowdOfCinders card) {
        super(card);
    }

    @Override
    public CrowdOfCinders copy() {
        return new CrowdOfCinders(this);
    }
}
