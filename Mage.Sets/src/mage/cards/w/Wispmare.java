
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class Wispmare extends CardImpl {

    public Wispmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Wispmare enters the battlefield, destroy target enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
        // Evoke {W}
        this.addAbility(new EvokeAbility("{W}"));
    }

    private Wispmare(final Wispmare card) {
        super(card);
    }

    @Override
    public Wispmare copy() {
        return new Wispmare(this);
    }
}
