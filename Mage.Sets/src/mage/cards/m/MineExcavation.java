
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class MineExcavation extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or enchantment card in a graveyard");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public MineExcavation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Return target artifact or enchantment card from a graveyard to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));
    }

    private MineExcavation(final MineExcavation card) {
        super(card);
    }

    @Override
    public MineExcavation copy() {
        return new MineExcavation(this);
    }
}
