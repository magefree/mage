
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LoneFox
 */
public final class DaruSanctifier extends CardImpl {

    public DaruSanctifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Morph {1}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{1}{W}")));
        // When Daru Sanctifier is turned face up, destroy target enchantment.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private DaruSanctifier(final DaruSanctifier card) {
        super(card);
    }

    @Override
    public DaruSanctifier copy() {
        return new DaruSanctifier(this);
    }
}
