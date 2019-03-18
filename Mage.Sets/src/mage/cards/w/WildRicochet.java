
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetStackObject;

/**
 *
 * @author jeffwadsworth
 */
public final class WildRicochet extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public WildRicochet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");

        // You may choose new targets for target instant or sorcery spell. Then copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect());
        Effect effect = new CopyTargetSpellEffect();
        effect.setText("Then copy that spell. You may choose new targets for the copy");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetStackObject(filter));
    }

    public WildRicochet(final WildRicochet card) {
        super(card);
    }

    @Override
    public WildRicochet copy() {
        return new WildRicochet(this);
    }
}
