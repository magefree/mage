
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
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
public final class FlitterstepEidolon extends CardImpl {

    public FlitterstepEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {5}{U}
        this.addAbility(new BestowAbility(this, "{5}{U}"));
        // Flitterstep Eidolon can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
        // Enchanted creature gets +1/+1 and can't be blocked.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1,1, Duration.WhileOnBattlefield));
        ability.addEffect(new CantBeBlockedAttachedEffect(AttachmentType.AURA).setText("and can't be blocked"));
        this.addAbility(ability);
    }

    private FlitterstepEidolon(final FlitterstepEidolon card) {
        super(card);
    }

    @Override
    public FlitterstepEidolon copy() {
        return new FlitterstepEidolon(this);
    }
}
