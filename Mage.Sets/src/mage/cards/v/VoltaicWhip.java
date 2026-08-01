package mage.cards.v;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class VoltaicWhip extends CardImpl {

    public VoltaicWhip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has "Whenever this creature attacks alone, you draw a card and lose 1 life."
        Ability subAbility = new AttacksAloneSourceTriggeredAbility(new DrawCardSourceControllerEffect(1, true));
        subAbility.addEffect(new LoseLifeSourceControllerEffect(1, false).concatBy("and"));

        Ability boostAbility = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        boostAbility.addEffect(new GainAbilityAttachedEffect(subAbility, AttachmentType.EQUIPMENT)
            .setText("and has \""+subAbility.getRule("this creature")+"\"").concatBy(""));
        this.addAbility(boostAbility);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private VoltaicWhip(final VoltaicWhip card) {
        super(card);
    }

    @Override
    public VoltaicWhip copy() {
        return new VoltaicWhip(this);
    }
}
