
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.IntimidateAbility;
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
public final class CavernLampad extends CardImpl {

    public CavernLampad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.NYMPH);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bestow {5}{B} (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        this.addAbility(new BestowAbility(this, "{5}{B}"));
        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
        // Enchanted creature gets +2/+2 and has intimidate.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2,2));
        Effect effect = new GainAbilityAttachedEffect(IntimidateAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has intimidate");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CavernLampad(final CavernLampad card) {
        super(card);
    }

    @Override
    public CavernLampad copy() {
        return new CavernLampad(this);
    }
}
