package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ThornbiteStaff extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.SHAMAN, "a Shaman creature");

    public ThornbiteStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{2}, {T}: This creature deals 1 damage to any target" and "Whenever a creature dies, untap this creature."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(2));
        gainedAbility.addCost(new TapSourceCost());
        gainedAbility.addTarget(new TargetAnyTarget());
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"{2}, {T}: This creature deals 1 damage to any target\"");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(new DiesCreatureTriggeredAbility(new UntapSourceEffect(),false), AttachmentType.EQUIPMENT);
        effect.setText("and \"Whenever a creature dies, untap this creature.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Whenever a Shaman creature enters the battlefield, you may attach Thornbite Staff to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {this} to it"),
                filter, true, SetTargetPointer.PERMANENT, null));
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), new TargetControlledCreaturePermanent(), false));
    }

    private ThornbiteStaff(final ThornbiteStaff card) {
        super(card);
    }

    @Override
    public ThornbiteStaff copy() {
        return new ThornbiteStaff(this);
    }
}
