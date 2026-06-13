package mage.cards.h;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class HulkbusterArmor extends CardImpl {

    private static final FilterPermanent filter
        = new FilterControlledCreaturePermanent(SubType.HERO, "Hero");

    public HulkbusterArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has base power and toughness 9/9 and has flying.
        Ability ability = new SimpleStaticAbility(
            new SetBasePowerToughnessAttachedEffect(9, 9, AttachmentType.EQUIPMENT)
        );
        ability.addEffect(
            new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and has flying")
        );
        this.addAbility(ability);

        // Equip Hero {3}
        this.addAbility(new EquipAbility(
            Outcome.AddAbility,
            new GenericManaCost(3),
            new TargetPermanent(filter)
        ));

        // Equip {6}
        this.addAbility(new EquipAbility(6));
    }

    private HulkbusterArmor(final HulkbusterArmor card) {
        super(card);
    }

    @Override
    public HulkbusterArmor copy() {
        return new HulkbusterArmor(this);
    }
}
