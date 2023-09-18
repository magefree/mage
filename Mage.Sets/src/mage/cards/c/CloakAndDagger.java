package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CloakAndDagger extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.ROGUE, "a Rogue creature");

    public CloakAndDagger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has shroud.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(ShroudAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has shroud"));
        this.addAbility(ability);
        // Whenever a Rogue creature enters the battlefield, you may attach Cloak and Dagger to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {this} to it"),
                filter, true, SetTargetPointer.PERMANENT, null));
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));
    }

    private CloakAndDagger(final CloakAndDagger card) {
        super(card);
    }

    @Override
    public CloakAndDagger copy() {
        return new CloakAndDagger(this);
    }
}
