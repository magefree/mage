
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.BlocksIfAbleAttachedEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleSourceEffect;
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
public final class Spirespine extends CardImpl {

    public Spirespine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Bestow 4G (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        this.addAbility(new BestowAbility(this, "{4}{G}"));
        // Spirespine blocks each turn if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield)));
        // Enchanted creature gets +4/+1 and blocks each combat if able.
        Effect effect = new BoostEnchantedEffect(4, 1, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +4/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new BlocksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText("and blocks each combat if able");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Spirespine(final Spirespine card) {
        super(card);
    }

    @Override
    public Spirespine copy() {
        return new Spirespine(this);
    }
}
