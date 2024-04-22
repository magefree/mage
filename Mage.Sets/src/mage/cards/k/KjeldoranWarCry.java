
package mage.cards.k;

import java.util.UUID;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author L_J
 */
public final class KjeldoranWarCry extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Kjeldoran War Cry");

    static {
        filter.add(new NamePredicate("Kjeldoran War Cry"));
    }

    public KjeldoranWarCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Creatures you control get +X/+X until end of turn, where X is 1 plus the number of cards named Kjeldoran War Cry in all graveyards.
        IntPlusDynamicValue value = new IntPlusDynamicValue(1, new CardsInAllGraveyardsCount(filter));
        Effect effect = new BoostControlledEffect(value, value, Duration.EndOfTurn, new FilterCreaturePermanent("creatures"), false);
        effect.setText("Creatures you control get +X/+X until end of turn, where X is 1 plus the number of cards named {this} in all graveyards");
        this.getSpellAbility().addEffect(effect);
    }

    private KjeldoranWarCry(final KjeldoranWarCry card) {
        super(card);
    }

    @Override
    public KjeldoranWarCry copy() {
        return new KjeldoranWarCry(this);
    }
}
