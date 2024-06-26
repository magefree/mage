
package mage.cards.m;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Molder extends CardImpl {

    public Molder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Destroy target artifact or enchantment with converted mana cost X. It can't be regenerated. You gain X life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy target artifact or enchantment with mana value X. It can't be regenerated", true));
        this.getSpellAbility().addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());
    }

    private Molder(final Molder card) {
        super(card);
    }

    @Override
    public Molder copy() {
        return new Molder(this);
    }
}
