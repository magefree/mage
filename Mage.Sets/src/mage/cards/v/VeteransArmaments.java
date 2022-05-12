package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VeteransArmaments extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.SOLDIER, "a Soldier creature");
    private static final DynamicValue xValue = new AttackingCreatureCount();

    public VeteransArmaments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "Whenever this creature attacks or blocks, it gets +1/+1 until end of turn for each attacking creature."
        Ability gainedAbility = new AttacksOrBlocksTriggeredAbility(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true), false);
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"Whenever this creature attacks or blocks, it gets +1/+1 until end of turn for each attacking creature.\"");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);

        // Whenever a Soldier creature enters the battlefield, you may attach Veteran's Armaments to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {this} to it"),
                filter, true, SetTargetPointer.PERMANENT, null));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private VeteransArmaments(final VeteransArmaments card) {
        super(card);
    }

    @Override
    public VeteransArmaments copy() {
        return new VeteransArmaments(this);
    }
}
