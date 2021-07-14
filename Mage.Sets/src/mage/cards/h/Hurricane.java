
package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Quercitron
 */
public final class Hurricane extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }
    
    public Hurricane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G}");


        // Hurricane deals X damage to each creature with flying and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(ManacostVariableValue.REGULAR, filter));
    }

    private Hurricane(final Hurricane card) {
        super(card);
    }

    @Override
    public Hurricane copy() {
        return new Hurricane(this);
    }
}
