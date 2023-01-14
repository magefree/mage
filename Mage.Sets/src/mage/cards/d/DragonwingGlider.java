package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.AttachmentType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class DragonwingGlider extends CardImpl {

    public DragonwingGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Equipped creature gets +2/+2 and has flying and haste.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and haste"));
        this.addAbility(ability);

        // Equip {3}{R}{R}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{3}{R}{R}")));
    }

    private DragonwingGlider(final DragonwingGlider card) {
        super(card);
    }

    @Override
    public DragonwingGlider copy() {
        return new DragonwingGlider(this);
    }
}
