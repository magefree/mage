
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author North
 */
public final class RollingTemblor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public RollingTemblor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");


        // Rolling Temblor deals 2 damage to each creature without flying.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
        // Flashback {4}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{R}{R}")));
    }

    private RollingTemblor(final RollingTemblor card) {
        super(card);
    }

    @Override
    public RollingTemblor copy() {
        return new RollingTemblor(this);
    }
}
