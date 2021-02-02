
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllAttachedEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
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
public final class NobleQuarry extends CardImpl {

    public NobleQuarry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.UNICORN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {5}{G}
        this.addAbility(new BestowAbility(this, "{5}{G}"));
        // All creatures able to block Noble Quarry or enchanted creature do so.
        Effect effect = new MustBeBlockedByAllSourceEffect(Duration.WhileOnBattlefield);
        effect.setText("All creatures able to block Noble Quarry");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new MustBeBlockedByAllAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText("or enchanted creature do so");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1,1, Duration.WhileOnBattlefield)));
    }

    private NobleQuarry(final NobleQuarry card) {
        super(card);
    }

    @Override
    public NobleQuarry copy() {
        return new NobleQuarry(this);
    }
}
