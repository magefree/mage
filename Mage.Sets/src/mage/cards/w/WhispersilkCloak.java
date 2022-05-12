package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class WhispersilkCloak extends CardImpl {

    public WhispersilkCloak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature can't be blocked and has shroud.
        Ability ability = new SimpleStaticAbility(new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT));
        ability.addEffect(new GainAbilityAttachedEffect(
                ShroudAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has shroud"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    protected WhispersilkCloak(WhispersilkCloak me) {
        super(me);
    }

    @Override
    public WhispersilkCloak copy() {
        return new WhispersilkCloak(this);
    }
}
