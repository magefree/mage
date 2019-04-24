
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FolkMedicine extends CardImpl {

    public FolkMedicine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // You gain 1 life for each creature you control.
        DynamicValue amount = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(amount));
        // Flashback {1}{W}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{1}{W}"), TimingRule.INSTANT));
    }

    public FolkMedicine(final FolkMedicine card) {
        super(card);
    }

    @Override
    public FolkMedicine copy() {
        return new FolkMedicine(this);
    }
}
