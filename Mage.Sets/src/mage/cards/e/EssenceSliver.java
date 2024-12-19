package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAnyTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class EssenceSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SLIVER, "a Sliver");

    public EssenceSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals damage, its controller gains that much life.
        this.addAbility(new DealsDamageToAnyTriggeredAbility(Zone.BATTLEFIELD,
                new GainLifeTargetEffect(SavedDamageValue.MUCH).setText("its controller gains that much life"),
                filter, SetTargetPointer.PLAYER, false, false
        ));

    }

    private EssenceSliver(final EssenceSliver card) {
        super(card);
    }

    @Override
    public EssenceSliver copy() {
        return new EssenceSliver(this);
    }
}
