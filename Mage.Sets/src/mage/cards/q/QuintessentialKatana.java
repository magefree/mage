package mage.cards.q;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class QuintessentialKatana extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.NINJA, "a Ninja");

    public QuintessentialKatana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has "Whenever this creature deals combat damage, untap it and you gain 2 life."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        Ability gainedAbility = new DealsCombatDamageTriggeredAbility(
            new UntapAttachedEffect().setText("untap it "), false
        ).setTriggerPhrase("Whenever this creature deals combat damage, ");
        gainedAbility.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addEffect(new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT
        ).setText("and has \"Whenever this creature deals combat damage, untap it and you gain 2 life.\""));
        this.addAbility(ability);

        // Whenever a Ninja you control enters, you may attach this Equipment to it.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature, "attach this Equipment to it"),
            filter, true, SetTargetPointer.PERMANENT
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private QuintessentialKatana(final QuintessentialKatana card) {
        super(card);
    }

    @Override
    public QuintessentialKatana copy() {
        return new QuintessentialKatana(this);
    }
}
