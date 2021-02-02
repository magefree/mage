
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LevelX2
 */
public final class BalefulEidolon extends CardImpl {

    public BalefulEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {4}{B}
        this.addAbility(new BestowAbility(this, "{4}{B}"));
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Enchanted creature gets +1/+1 and has deathtouch.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1,1, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield,"and has deathtouch"));
        this.addAbility(ability);
    }

    private BalefulEidolon(final BalefulEidolon card) {
        super(card);
    }

    @Override
    public BalefulEidolon copy() {
        return new BalefulEidolon(this);
    }
}
