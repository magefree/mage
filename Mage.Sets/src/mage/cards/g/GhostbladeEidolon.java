
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
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
public final class GhostbladeEidolon extends CardImpl {

    public GhostbladeEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {5}{W}
        this.addAbility(new BestowAbility(this, "{5}{W}"));
        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Enchanted creature gets +1/+1 and has double strike.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1,1));
        Effect effect = new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("and has double strike");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GhostbladeEidolon(final GhostbladeEidolon card) {
        super(card);
    }

    @Override
    public GhostbladeEidolon copy() {
        return new GhostbladeEidolon(this);
    }
}
