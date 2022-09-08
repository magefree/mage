package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneAttachedTriggeredAbility;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author awjackson
 */
public final class ReapersTalisman extends CardImpl {

    public ReapersTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, it gains deathtouch until end of turn.
        this.addAbility(new AttacksAttachedTriggeredAbility(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(),
                AttachmentType.EQUIPMENT,
                Duration.EndOfTurn,
                "it gains deathtouch until end of turn"
        )));

        // Whenever equipped creature attacks alone, defending player loses 2 life and you gain 2 life.
        Ability ability = new AttacksAloneAttachedTriggeredAbility(
                new LoseLifeTargetEffect(2).setText("defending player loses 2 life"),
                AttachmentType.EQUIPMENT, false, SetTargetPointer.PLAYER
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ReapersTalisman(final ReapersTalisman card) {
        super(card);
    }

    @Override
    public ReapersTalisman copy() {
        return new ReapersTalisman(this);
    }
}
