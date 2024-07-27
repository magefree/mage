package mage.cards.b;

import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.predicate.other.AbilitySourceAttachedPredicate;
import mage.filter.predicate.other.NotManaAbilityPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattlemagesBracers extends CardImpl {

    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("an ability of equipped creature");

    static {
        filter.add(NotManaAbilityPredicate.instance);
        filter.add(AbilitySourceAttachedPredicate.instance);
    }

    public BattlemagesBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield
        )));

        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, you may pay {1}. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new ActivateAbilityTriggeredAbility(new DoIfCostPaid(new CopyStackObjectEffect(), new GenericManaCost(1)), filter, SetTargetPointer.SPELL)
                .setTriggerPhrase("Whenever an ability of equipped creature is activated, if it isn't a mana ability, "));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private BattlemagesBracers(final BattlemagesBracers card) {
        super(card);
    }

    @Override
    public BattlemagesBracers copy() {
        return new BattlemagesBracers(this);
    }
}
