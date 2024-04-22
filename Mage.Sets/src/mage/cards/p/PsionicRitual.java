package mage.cards.p;

import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PsionicRitual extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.HORROR, "an untapped Horror you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public PsionicRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Replicate—Tap an untapped Horror you control.
        this.addAbility(new ReplicateAbility(new TapTargetCost(new TargetControlledPermanent(filter))));

        // Exile target instant or sorcery card from a graveyard and copy it. You may cast the copy without paying its mana cost.
        this.getSpellAbility()
                .addEffect(new ExileTargetCardCopyAndCastEffect(true)
                        .setText("exile target instant or sorcery card from a graveyard " +
                                "and copy it. You may cast the copy without paying its mana cost"));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));

        // Exile Psionic Ritual.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private PsionicRitual(final PsionicRitual card) {
        super(card);
    }

    @Override
    public PsionicRitual copy() {
        return new PsionicRitual(this);
    }
}
