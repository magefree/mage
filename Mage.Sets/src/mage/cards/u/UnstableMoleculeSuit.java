package mage.cards.u;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class UnstableMoleculeSuit extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("commander");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public UnstableMoleculeSuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has indestructible.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
            IndestructibleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has indestructible"));
        this.addAbility(ability);

        // Equip commander {2}
        this.addAbility(new EquipAbility(
            Outcome.BoostCreature, new GenericManaCost(2), new TargetPermanent(filter), false
        ));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private UnstableMoleculeSuit(final UnstableMoleculeSuit card) {
        super(card);
    }

    @Override
    public UnstableMoleculeSuit copy() {
        return new UnstableMoleculeSuit(this);
    }
}
