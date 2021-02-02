
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class NyleasEmissary extends CardImpl {

    public NyleasEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bestow {5}{G}
        this.addAbility(new BestowAbility(this, "{5}{G}"));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Enchanted creature gets +3/+3 and has trample.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3,3, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield,"and has trample"));
        this.addAbility(ability);
        
    }

    private NyleasEmissary(final NyleasEmissary card) {
        super(card);
    }

    @Override
    public NyleasEmissary copy() {
        return new NyleasEmissary(this);
    }
}
