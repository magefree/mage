
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author North
 */
public final class Lashwrithe extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp you control");

    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public Lashwrithe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon (When this Equipment enters the battlefield, create a 0/0 black Phyrexian Germ creature token, then attach this to it.)
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +1/+1 for each Swamp you control.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(value, value)));

        // Equip {B/P}{B/P} (Phyrexian Black can be paid with either Black or 2 life.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{B/P}{B/P}"), false));
    }

    private Lashwrithe(final Lashwrithe card) {
        super(card);
    }

    @Override
    public Lashwrithe copy() {
        return new Lashwrithe(this);
    }
}
