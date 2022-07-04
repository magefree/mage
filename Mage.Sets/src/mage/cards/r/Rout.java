
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author LoneFox
 *
 */
public final class Rout extends CardImpl {

    public Rout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        Effect effect = new DestroyAllEffect(FILTER_PERMANENT_CREATURES, true);
        // You may cast Rout as though it had flash if you pay {2} more to cast it.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl<>("{2}"));
        ability.addEffect(effect);
        this.addAbility(ability);
        // Destroy all creatures. They can't be regenerated.
        this.getSpellAbility().addEffect(effect);
    }

    private Rout(final Rout card) {
        super(card);
    }

    @Override
    public Rout copy() {
        return new Rout(this);
    }
}
