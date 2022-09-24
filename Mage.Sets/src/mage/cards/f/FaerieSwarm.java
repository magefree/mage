
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class FaerieSwarm extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("blue permanents you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public FaerieSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.FAERIE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(FlyingAbility.getInstance());
        // Faerie Swarm's power and toughness are each equal to the number of blue permanents you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    private FaerieSwarm(final FaerieSwarm card) {
        super(card);
    }

    @Override
    public FaerieSwarm copy() {
        return new FaerieSwarm(this);
    }
}
