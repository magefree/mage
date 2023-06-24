package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllAttachedToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 *
 */
public final class StripBare extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Auras and Equipment");

    static {
        filter.add(Predicates.or(SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()));
    }

    public StripBare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Destroy all Auras and Equipment attached to target creature.
        this.getSpellAbility().addEffect(new DestroyAllAttachedToTargetEffect(filter, "target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StripBare(final StripBare card) {
        super(card);
    }

    @Override
    public StripBare copy() {
        return new StripBare(this);
    }
}
