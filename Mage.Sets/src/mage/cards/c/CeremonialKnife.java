package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CeremonialKnife extends CardImpl {

    public CeremonialKnife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has "Whenever this creature deals combat damage, create a Blood token."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsCombatDamageTriggeredAbility(
                        new CreateTokenEffect(new BloodToken()), false
                ).setTriggerPhrase("Whenever this creature deals combat damage, "), AttachmentType.AURA
        ).setText("and has \"Whenever this creature deals combat damage, create a Blood token.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private CeremonialKnife(final CeremonialKnife card) {
        super(card);
    }

    @Override
    public CeremonialKnife copy() {
        return new CeremonialKnife(this);
    }
}
