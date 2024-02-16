package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author fireshoes
 */
public final class BladeOfSelves extends CardImpl {

    private static final String rule = "Equipped creature has myriad. " +
            "<i>(Whenever it attacks, for each opponent other than defending player, " +
            "you may create a token that's a copy of that creature that's tapped and " +
            "attacking that player or a planeswalker they control. Exile the tokens at end of combat.)</i>";

    public BladeOfSelves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has myriad.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(new MyriadAbility(), AttachmentType.EQUIPMENT)
                .setText(rule)));
        
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), false));
    }

    private BladeOfSelves(final BladeOfSelves card) {
        super(card);
    }

    @Override
    public BladeOfSelves copy() {
        return new BladeOfSelves(this);
    }
}
