package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class IconicShield extends CardImpl {

    static final FilterAttackingCreature filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public IconicShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+2 and has "Whenever this creature attacks, another target attacking creature gains indestructible until end of turn."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 2));
        AttacksTriggeredAbility attacksAbility = new AttacksTriggeredAbility(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), false);
        attacksAbility.addTarget(new TargetPermanent(filter));
        ability.addEffect(new GainAbilityAttachedEffect(attacksAbility, AttachmentType.EQUIPMENT)
            .setText("and has \"Whenever this creature attacks, another target attacking creature gains indestructible until end of turn.\""));

        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private IconicShield(final IconicShield card) {
        super(card);
    }

    @Override
    public IconicShield copy() {
        return new IconicShield(this);
    }
}
