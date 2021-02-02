
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class StructuralDistortion extends CardImpl {

    private static final FilterPermanent FILTER = new FilterPermanent("artifact or land");

    static {
        FILTER.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.LAND.getPredicate()));
    }

    public StructuralDistortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Exile target artifact or land. Structural Distortion deals 2 damage to that permanent's controller.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        Effect effect = new DamageTargetControllerEffect(2);
        effect.setText("{this} deals 2 damage to that permanent's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER));
    }

    private StructuralDistortion(final StructuralDistortion card) {
        super(card);
    }

    @Override
    public StructuralDistortion copy() {
        return new StructuralDistortion(this);
    }
}
