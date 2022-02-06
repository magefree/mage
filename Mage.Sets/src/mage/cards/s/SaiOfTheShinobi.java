
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SaiOfTheShinobi extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SaiOfTheShinobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));
        // Whenever a creature enters the battlefield under your control, you may attach Sai of the Shinobi to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {this} to it"),
                filter, true, SetTargetPointer.PERMANENT, null, true));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private SaiOfTheShinobi(final SaiOfTheShinobi card) {
        super(card);
    }

    @Override
    public SaiOfTheShinobi copy() {
        return new SaiOfTheShinobi(this);
    }
}
