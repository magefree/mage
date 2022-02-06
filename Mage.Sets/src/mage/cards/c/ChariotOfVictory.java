
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ChariotOfVictory extends CardImpl {

    public ChariotOfVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike, trample, and haste.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT));
        Effect effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", trample,");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and haste");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), false));
    }

    private ChariotOfVictory(final ChariotOfVictory card) {
        super(card);
    }

    @Override
    public ChariotOfVictory copy() {
        return new ChariotOfVictory(this);
    }
}
