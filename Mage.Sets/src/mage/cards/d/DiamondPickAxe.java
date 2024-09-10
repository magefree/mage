package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiamondPickAxe extends CardImpl {

    public DiamondPickAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Equipped creature gets +1/+1 and has "Whenever this creature attacks, create a Treasure token."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken()))
                        .setTriggerPhrase("Whenever this creature attacks, "),
                AttachmentType.EQUIPMENT
        ).setText("and has \"Whenever this creature attacks, create a Treasure token.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private DiamondPickAxe(final DiamondPickAxe card) {
        super(card);
    }

    @Override
    public DiamondPickAxe copy() {
        return new DiamondPickAxe(this);
    }
}
