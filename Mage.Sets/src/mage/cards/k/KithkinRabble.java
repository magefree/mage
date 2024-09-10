
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
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
public final class KithkinRabble extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("white permanents you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public KithkinRabble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.KITHKIN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(VigilanceAbility.getInstance());
        // Kithkin Rabble's power and toughness are each equal to the number of white permanents you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private KithkinRabble(final KithkinRabble card) {
        super(card);
    }

    @Override
    public KithkinRabble copy() {
        return new KithkinRabble(this);
    }
}
