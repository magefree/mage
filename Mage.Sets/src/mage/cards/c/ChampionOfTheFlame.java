
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class ChampionOfTheFlame extends CardImpl {

    public ChampionOfTheFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Champion of the Flame gets +2/+2 for each Aura and Equipment attached to it.
        DynamicValue auraAmount = new AuraAttachedCount(2);
        DynamicValue equipAmount = new EquipmentAttachedCount(2);
        DynamicValue totalAmount = new AdditiveDynamicValue(auraAmount, equipAmount);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(totalAmount, totalAmount, Duration.WhileOnBattlefield)
                        .setText("{this} gets +2/+2 for each Aura and Equipment attached to it")));
    }

    private ChampionOfTheFlame(final ChampionOfTheFlame card) {
        super(card);
    }

    @Override
    public ChampionOfTheFlame copy() {
        return new ChampionOfTheFlame(this);
    }
}
