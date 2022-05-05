package mage.cards.i;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class InventorsGoggles extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ARTIFICER, "an Artificer");

    public InventorsGoggles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 2, Duration.WhileOnBattlefield)));

        // Whenever an Artificer enters the battlefield under your control, you may attach Inventor's Goggles to it.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature, "attach {this} to it"),
                filter, true, SetTargetPointer.PERMANENT, null
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private InventorsGoggles(final InventorsGoggles card) {
        super(card);
    }

    @Override
    public InventorsGoggles copy() {
        return new InventorsGoggles(this);
    }
}
