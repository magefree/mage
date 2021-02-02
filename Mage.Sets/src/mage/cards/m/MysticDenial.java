
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox
 */
public final class MysticDenial extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or sorcery spell");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public MysticDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Counter target creature or sorcery spell.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private MysticDenial(final MysticDenial card) {
        super(card);
    }

    @Override
    public MysticDenial copy() {
        return new MysticDenial(this);
    }
}
