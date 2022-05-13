
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

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
        this.getSpellAbility().setTargetAdjuster(MolderAdjuster.instance);
    }

    private Molder(final Molder card) {
        super(card);
    }

    @Override
    public Molder copy() {
        return new Molder(this);
    }
}

enum MolderAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetPermanent(filter));
    }
}