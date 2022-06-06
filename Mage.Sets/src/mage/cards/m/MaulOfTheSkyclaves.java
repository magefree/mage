package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaulOfTheSkyclaves extends CardImpl {

    public MaulOfTheSkyclaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Maul of the Skyclaves enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+2 and has flying and first strike.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and first strike"));
        this.addAbility(ability);

        // Equip {2}{W}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{W}{W}"), false));
    }

    private MaulOfTheSkyclaves(final MaulOfTheSkyclaves card) {
        super(card);
    }

    @Override
    public MaulOfTheSkyclaves copy() {
        return new MaulOfTheSkyclaves(this);
    }
}
