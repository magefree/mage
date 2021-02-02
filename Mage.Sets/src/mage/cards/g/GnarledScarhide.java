
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class GnarledScarhide extends CardImpl {

    public GnarledScarhide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{B}");
        this.subtype.add(SubType.MINOTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bestow {3}{B}
        this.addAbility(new BestowAbility(this, "{3}{B}"));
        // Gnarled Scarhide can't block.
        this.addAbility(new CantBlockAbility());
        // Enchanted creature gets +2/+1 and can't block.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2,1, Duration.WhileOnBattlefield));
        Effect effect = new CantBlockAttachedEffect(AttachmentType.AURA);
        effect.setText("and can't block");
        ability.addEffect(effect);
        this.addAbility(ability);
        
    }

    private GnarledScarhide(final GnarledScarhide card) {
        super(card);
    }

    @Override
    public GnarledScarhide copy() {
        return new GnarledScarhide(this);
    }
}
