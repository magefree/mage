package mage.cards.c;

import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class Cryoclasm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Plains or Island");

    static {
        filter.add(Predicates.or(SubType.PLAINS.getPredicate(), SubType.ISLAND.getPredicate()));
    }

    public Cryoclasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Destroy target Plains or Island. Cryoclasm deals 3 damage to that land's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageTargetControllerEffect(3, "land"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    private Cryoclasm(final Cryoclasm card) {
        super(card);
    }

    @Override
    public Cryoclasm copy() {
        return new Cryoclasm(this);
    }
}
