package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

/**
 * @author jeffwadsworth
 */
public final class PutAway extends CardImpl {

    public PutAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target spell. You may shuffle up to one target card from your graveyard into your library.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new ShuffleIntoLibraryTargetEffect(true)
                .setTargetPointer(new SecondTargetPointer())
                .setText("you may shuffle up to one target card from your graveyard into your library"));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD));

    }

    private PutAway(final PutAway card) {
        super(card);
    }

    @Override
    public PutAway copy() {
        return new PutAway(this);
    }
}
