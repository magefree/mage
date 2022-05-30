
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public final class Flusterstorm extends CardImpl {

    public Flusterstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Counter target instant or sorcery spell unless its controller pays {1}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new ManaCostsImpl<>("{1}")));
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        // Storm
        this.addAbility(new StormAbility());
    }

    private Flusterstorm(final Flusterstorm card) {
        super(card);
    }

    @Override
    public Flusterstorm copy() {
        return new Flusterstorm(this);
    }
}
