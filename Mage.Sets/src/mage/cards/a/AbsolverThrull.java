
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetEnchantmentPermanent;

/**
*
* @author LevelX2
*/
public final class AbsolverThrull extends CardImpl {

    public AbsolverThrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.THRULL);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Haunt (When this creature dies, exile it haunting target creature.)
        // When Absolver Thrull enters the battlefield or the creature it haunts dies, destroy target enchantment.
        Ability ability = new HauntAbility(this, new DestroyTargetEffect());
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private AbsolverThrull(final AbsolverThrull card) {
        super(card);
    }

    @Override
    public AbsolverThrull copy() {
        return new AbsolverThrull(this);
    }
}
