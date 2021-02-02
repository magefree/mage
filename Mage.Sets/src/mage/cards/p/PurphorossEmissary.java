
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class PurphorossEmissary extends CardImpl {

    public PurphorossEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.OX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bestow {6}{R}  (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        this.addAbility(new BestowAbility(this, "{6}{R}"));
        
        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());
        
        // Enchanted creature gets +3/+3 and and has menace.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3,3));
        Effect effect = new GainAbilityAttachedEffect(new MenaceAbility(), AttachmentType.AURA);
        effect.setText("and has menace");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private PurphorossEmissary(final PurphorossEmissary card) {
        super(card);
    }

    @Override
    public PurphorossEmissary copy() {
        return new PurphorossEmissary(this);
    }
}
