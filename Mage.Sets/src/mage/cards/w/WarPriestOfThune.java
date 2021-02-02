

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class WarPriestOfThune extends CardImpl {

    public WarPriestOfThune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When War Priest of Thune enters the battlefield, you may destroy target enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private WarPriestOfThune(final WarPriestOfThune card) {
        super(card);
    }

    @Override
    public WarPriestOfThune copy() {
        return new WarPriestOfThune(this);
    }

}
