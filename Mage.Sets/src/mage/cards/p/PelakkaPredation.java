package mage.cards.p;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PelakkaPredation extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card from it with converted mana cost 3 or greater");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, 2));
    }

    public PelakkaPredation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.p.PelakkaCaverns.class;

        // Target opponent reveals their hand. You may choose a card from it with converted mana cost 3 or greater. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private PelakkaPredation(final PelakkaPredation card) {
        super(card);
    }

    @Override
    public PelakkaPredation copy() {
        return new PelakkaPredation(this);
    }
}
