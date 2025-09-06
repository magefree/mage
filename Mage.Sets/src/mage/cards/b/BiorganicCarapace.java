package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class BiorganicCarapace extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("each modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public BiorganicCarapace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+2 and has "Whenever this creature deals combat damage to a player, draw a card for each modified creature you control."
        Ability gainedAbility = new DealsCombatDamageTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)), false);
        Ability equipAbility = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        equipAbility.addEffect(new GainAbilityAttachedEffect(gainedAbility, null)
                .concatBy("and"));
        this.addAbility(equipAbility);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private BiorganicCarapace(final BiorganicCarapace card) {
        super(card);
    }

    @Override
    public BiorganicCarapace copy() {
        return new BiorganicCarapace(this);
    }
}
