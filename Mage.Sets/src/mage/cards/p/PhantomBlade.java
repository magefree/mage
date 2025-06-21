package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class PhantomBlade extends CardImpl {

    public PhantomBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Phantom Blade enters the battlefield, attach it to up to one target creature you control. Destroy up to one other target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(Outcome.BoostCreature).setText("attach it to up to one target creature you control"));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1).setTargetTag(1));

        ability.addEffect(new DestroyTargetEffect().setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
        this.addAbility(ability);

        // Equipped creature gets +1/+1 and has menace.
        Ability boostAbility = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        boostAbility.addEffect(new GainAbilityAttachedEffect(new MenaceAbility(), AttachmentType.EQUIPMENT).setText("and has menace"));
        this.addAbility(boostAbility);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));

    }

    private PhantomBlade(final PhantomBlade card) {
        super(card);
    }

    @Override
    public PhantomBlade copy() {
        return new PhantomBlade(this);
    }
}
