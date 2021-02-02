
package mage.cards.p;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Wehk
 */
public final class PureSimple extends SplitCard {

    private static final FilterPermanent filterDestroy = new FilterPermanent("Auras and Equipment");
    private static final FilterPermanent filterMulticolor = new FilterPermanent("multicolored permanent");

    static {
        filterDestroy.add(Predicates.or(SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()));
        filterMulticolor.add(MulticoloredPredicate.instance);
    }

    public PureSimple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{G}", "{1}{G}{W}", SpellAbilityType.SPLIT_FUSED);

        // Pure
        // Destroy target multicolored permanent.
        getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filterMulticolor));

        // Simple
        // Destroy all Auras and Equipment.
        getRightHalfCard().getSpellAbility().addEffect(new DestroyAllEffect(filterDestroy));
    }

    private PureSimple(final PureSimple card) {
        super(card);
    }

    @Override
    public PureSimple copy() {
        return new PureSimple(this);
    }
}
