
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class PsychicSpear extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spirit or Arcane card");

    static {
        filter.add(Predicates.or(SubType.SPIRIT.getPredicate(),SubType.ARCANE.getPredicate()));
    }

    public PsychicSpear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target player reveals their hand. You choose a Spirit or Arcane card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
    }

    private PsychicSpear(final PsychicSpear card) {
        super(card);
    }

    @Override
    public PsychicSpear copy() {
        return new PsychicSpear(this);
    }
}
