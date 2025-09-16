package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttachedToAttachedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThranPowerSuit extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Aura and Equipment attached to it");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
        filter.add(AttachedToAttachedPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);

    public ThranPowerSuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each Aura and Equipment attached to it and has ward {2}.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue));
        ability.addEffect(new GainAbilityAttachedEffect(new WardAbility(
                new GenericManaCost(2), false
        ), AttachmentType.EQUIPMENT).setText("and has ward {2}. <i>(Whenever equipped creature becomes the target of a spell or ability an opponent controls, counter it unless that player pays {2}.)</i>"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ThranPowerSuit(final ThranPowerSuit card) {
        super(card);
    }

    @Override
    public ThranPowerSuit copy() {
        return new ThranPowerSuit(this);
    }
}
