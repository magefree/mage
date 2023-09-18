
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class HordeOfBoggarts extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("red permanents you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public HordeOfBoggarts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Horde of Boggarts's power and toughness are each equal to the number of red permanents you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
        
        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());
    }

    private HordeOfBoggarts(final HordeOfBoggarts card) {
        super(card);
    }

    @Override
    public HordeOfBoggarts copy() {
        return new HordeOfBoggarts(this);
    }
}
