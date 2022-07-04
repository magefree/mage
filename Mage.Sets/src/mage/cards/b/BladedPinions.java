package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.Outcome;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Loki
 */
public final class BladedPinions extends CardImpl {

    public BladedPinions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has flying and first strike.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and first strike"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    public BladedPinions(final BladedPinions card) {
        super(card);
    }

    @Override
    public BladedPinions copy() {
        return new BladedPinions(this);
    }

}
