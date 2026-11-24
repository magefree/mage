package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.SourceAttachedTargetPointer;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SwordOfFireAndIce extends CardImpl {

    public SwordOfFireAndIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from red and from blue.
        this.addAbility(new SimpleStaticAbility(new BoostGainAbilityGenericEffect(
                2, 2, Duration.WhileOnBattlefield,
                ProtectionAbility.from(ObjectColor.RED, ObjectColor.BLUE)
        ).setTargetPointer(new SourceAttachedTargetPointer("equipped creature"))));

        // Whenever equipped creature deals combat damage to a player, Sword of Fire 
        // and Ice deals 2 damage to any target and you draw a card.
        Ability ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new DamageTargetEffect(2), "equipped creature", false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1, true).concatBy("and"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(2), false));
    }

    private SwordOfFireAndIce(final SwordOfFireAndIce card) {
        super(card);
    }

    @Override
    public SwordOfFireAndIce copy() {
        return new SwordOfFireAndIce(this);
    }

}
