
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class RollingEarthquake extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without horsemanship");

    static {
        filter.add(Predicates.not(new AbilityPredicate(HorsemanshipAbility.class)));
    }

    public RollingEarthquake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");


        // Rolling Earthquake deals X damage to each creature without horsemanship and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(ManacostVariableValue.REGULAR, filter));
    }

    private RollingEarthquake(final RollingEarthquake card) {
        super(card);
    }

    @Override
    public RollingEarthquake copy() {
        return new RollingEarthquake(this);
    }
}
