package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledLandPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaiLiIndoctrination extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("a nonland permanent card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public DaiLiIndoctrination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        this.subtype.add(SubType.LESSON);

        // Choose one --
        // * Target opponent reveals their hand. You choose a nonland permanent card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // * Earthbend 2.
        this.getSpellAbility().addMode(new Mode(new EarthbendTargetEffect(2))
                .addTarget(new TargetControlledLandPermanent()));
    }

    private DaiLiIndoctrination(final DaiLiIndoctrination card) {
        super(card);
    }

    @Override
    public DaiLiIndoctrination copy() {
        return new DaiLiIndoctrination(this);
    }
}
