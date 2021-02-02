
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class SupremeVerdict extends CardImpl {

    public SupremeVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{W}{U}");

        // Supreme Verdict can't be countered.
        Ability ability = new CantBeCounteredSourceAbility();
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Destroy all creatures. 
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private SupremeVerdict(final SupremeVerdict card) {
        super(card);
    }

    @Override
    public SupremeVerdict copy() {
        return new SupremeVerdict(this);
    }
}
