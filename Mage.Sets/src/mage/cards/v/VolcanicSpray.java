
package mage.cards.v;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
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
 * @author cbt33
 */
public final class VolcanicSpray extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public VolcanicSpray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Volcanic Spray deals 1 damage to each creature without flying and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(1, filter));
        // Flashback {1}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{1}{R}")));
    }

    private VolcanicSpray(final VolcanicSpray card) {
        super(card);
    }

    @Override
    public VolcanicSpray copy() {
        return new VolcanicSpray(this);
    }
}
