package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CeremonialGroundbreaker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.CITIZEN, "Citizen");

    public CeremonialGroundbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Equip Citizen {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetPermanent(filter)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private CeremonialGroundbreaker(final CeremonialGroundbreaker card) {
        super(card);
    }

    @Override
    public CeremonialGroundbreaker copy() {
        return new CeremonialGroundbreaker(this);
    }
}
