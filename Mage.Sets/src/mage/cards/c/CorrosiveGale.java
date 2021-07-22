
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author North
 */
public final class CorrosiveGale extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public CorrosiveGale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G/P}");


        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, filter));
    }

    private CorrosiveGale(final CorrosiveGale card) {
        super(card);
    }

    @Override
    public CorrosiveGale copy() {
        return new CorrosiveGale(this);
    }
}
