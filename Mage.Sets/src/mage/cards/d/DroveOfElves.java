
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.HexproofAbility;
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
public final class DroveOfElves extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("green permanents you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public DroveOfElves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HexproofAbility.getInstance());
        // Drove of Elves's power and toughness are each equal to the number of green permanents you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    private DroveOfElves(final DroveOfElves card) {
        super(card);
    }

    @Override
    public DroveOfElves copy() {
        return new DroveOfElves(this);
    }
}
