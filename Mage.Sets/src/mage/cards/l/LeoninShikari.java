
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.ActivateAbilitiesAnyTimeYouCouldCastInstantEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class LeoninShikari extends CardImpl {

    public LeoninShikari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may activate equip abilities any time you could cast an instant.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ActivateAbilitiesAnyTimeYouCouldCastInstantEffect(EquipAbility.class, "equip abilities")));
    }

    private LeoninShikari(final LeoninShikari card) {
        super(card);
    }

    @Override
    public LeoninShikari copy() {
        return new LeoninShikari(this);
    }
}
