
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class FeastOfFlesh extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Feast of Flesh");

    static {
        filter.add(new NamePredicate("Feast of Flesh"));
    }

    public FeastOfFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Feast of Flesh deals X damage to target creature and you gain X life, where X is 1 plus the number of cards named Feast of Flesh in all graveyards.
        IntPlusDynamicValue value = new IntPlusDynamicValue(1, new CardsInAllGraveyardsCount(filter));
        Effect effect1 = new DamageTargetEffect(value);
        effect1.setText("Feast of Flesh deals X damage to target creature");
        Effect effect2 = new GainLifeEffect(value);
        effect2.setText("and you gain X life, where X is 1 plus the number of cards named {this} in all graveyards");
        this.getSpellAbility().addEffect(effect1);
        this.getSpellAbility().addEffect(effect2);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FeastOfFlesh(final FeastOfFlesh card) {
        super(card);
    }

    @Override
    public FeastOfFlesh copy() {
        return new FeastOfFlesh(this);
    }
}
