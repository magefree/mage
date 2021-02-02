package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BlindingPowder extends CardImpl {

    public BlindingPowder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "Unattach Blinding Powder: Prevent all combat damage that would be dealt to this creature this turn."
        this.addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"Unattach {this}: Prevent all combat damage " +
                        "that would be dealt to this creature this turn.\"",
                new PreventCombatDamageToSourceEffect(Duration.EndOfTurn)
                        .setText("Prevent all combat damage that would be dealt to this creature this turn"),
                null, new UnattachCost()
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.PreventDamage, new GenericManaCost(2)));
    }

    private BlindingPowder(final BlindingPowder card) {
        super(card);
    }

    @Override
    public BlindingPowder copy() {
        return new BlindingPowder(this);
    }
}
