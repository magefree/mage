
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class SylvanReclamation extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and/or enchantments");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    public SylvanReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{W}");

        // Exile up to two target artifacts and/or enchantments.
        Effect effect = new ExileTargetEffect();
        effect.setText("Exile up to two target artifacts and/or enchantments");
        Target target = new TargetPermanent(0, 2, filter, false);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(effect);

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SylvanReclamation(final SylvanReclamation card) {
        super(card);
    }

    @Override
    public SylvanReclamation copy() {
        return new SylvanReclamation(this);
    }
}
