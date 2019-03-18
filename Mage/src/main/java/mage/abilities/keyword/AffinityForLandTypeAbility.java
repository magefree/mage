
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.effects.common.AffinityEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class AffinityForLandTypeAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    private final FilterControlledPermanent filter;

    String text;
    SubType landType;

    public AffinityForLandTypeAbility(SubType landType, String text) {
        super(Zone.OUTSIDE, new AffinityEffect(getFilter(landType)));
        this.filter = getFilter(landType);
        setRuleAtTheTop(true);
        this.text = text;
        this.landType = landType;
    }

   private static FilterControlledPermanent getFilter(SubType landType) {
        FilterControlledPermanent affinityfilter = new FilterControlledPermanent();
        affinityfilter.add(new SubtypePredicate(landType));
        return affinityfilter;

    }

    public AffinityForLandTypeAbility(final AffinityForLandTypeAbility ability) {
        super(ability);
        this.text = ability.text;
        this.filter = ability.filter.copy();
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AffinityForLandTypeAbility(this);
    }

    @Override
    public String getRule() {
        return "Affinity for " + text + " <i>(This spell costs 1 less to cast for each " + landType + " you control.)</i>";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int count = game.getBattlefield().getAllActivePermanents(filter, ability.getControllerId(), game).size();
            if (count > 0) {
                CardUtil.adjustCost((SpellAbility)ability, count);
            }
        }
    }
}
