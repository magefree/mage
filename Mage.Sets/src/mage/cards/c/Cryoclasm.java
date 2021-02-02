
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author dustinconrad
 */
public final class Cryoclasm extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Plains or Island");

    static {
        filter.add(Predicates.or(SubType.PLAINS.getPredicate(), SubType.ISLAND.getPredicate()));
    }

    public Cryoclasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Destroy target Plains or Island. Cryoclasm deals 3 damage to that land's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Effect effect = new DamageTargetControllerEffect(3);
        effect.setText("{this} deals 3 damage to that land's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetLandPermanent(filter));

    }

    private Cryoclasm(final Cryoclasm card) {
        super(card);
    }

    @Override
    public Cryoclasm copy() {
        return new Cryoclasm(this);
    }
}
