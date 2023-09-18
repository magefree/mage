package mage.cards.r;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class RoninWarclub extends CardImpl {

    public RoninWarclub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Whenever a creature enters the battlefield under your control, attach Ronin Warclub to that creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature, "attach {this} to that creature"),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, false, SetTargetPointer.PERMANENT, null));

        // Equip {5} ({5}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(5)));
    }

    private RoninWarclub(final RoninWarclub card) {
        super(card);
    }

    @Override
    public RoninWarclub copy() {
        return new RoninWarclub(this);
    }
}
