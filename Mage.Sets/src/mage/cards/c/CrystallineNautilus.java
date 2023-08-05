
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
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
public final class CrystallineNautilus extends CardImpl {

    public CrystallineNautilus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.NAUTILUS);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Bestow 3UU (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        this.addAbility(new BestowAbility(this, "{3}{U}{U}"));
        
        // When Crystalline Nautilus becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
        
        // Enchanted creature gets +4/+4 and has "When this creature becomes the target of a spell or ability, sacrifice it."        
        Effect effect = new BoostEnchantedEffect(4,4,Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +4/+4");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect()), AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("and has \"When this creature becomes the target of a spell or ability, sacrifice it.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CrystallineNautilus(final CrystallineNautilus card) {
        super(card);
    }

    @Override
    public CrystallineNautilus copy() {
        return new CrystallineNautilus(this);
    }
}
