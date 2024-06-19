package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuntersBow extends CardImpl {

    public HuntersBow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        // When Hunter's Bow enters the battlefield, attach it to target creature you control. That creature deals damage equal to its power to up to one target creature you don't control.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("that creature"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // Equipped creature has reach and ward {2}.
        ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(2)), AttachmentType.EQUIPMENT
        ).setText("and ward {2}"));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private HuntersBow(final HuntersBow card) {
        super(card);
    }

    @Override
    public HuntersBow copy() {
        return new HuntersBow(this);
    }
}
