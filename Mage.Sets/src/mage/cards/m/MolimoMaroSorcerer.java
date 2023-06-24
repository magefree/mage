
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Loki
 */
public final class MolimoMaroSorcerer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("lands you control");

    public MolimoMaroSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Molimo, Maro-Sorcerer's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private MolimoMaroSorcerer(final MolimoMaroSorcerer card) {
        super(card);
    }

    @Override
    public MolimoMaroSorcerer copy() {
        return new MolimoMaroSorcerer(this);
    }
}
