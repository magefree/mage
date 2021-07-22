

package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Earthquake extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public Earthquake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");

        // Hurricane deals X damage to each creature with flying and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(ManacostVariableValue.REGULAR, filter));
    }

    private Earthquake(final Earthquake card) {
        super(card);
    }

    @Override
    public Earthquake copy() {
        return new Earthquake(this);
    }
}
