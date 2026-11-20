package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author muz
 */
public final class StrayFromHistory extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and/or Spacecraft cards");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            SubType.SPACECRAFT.getPredicate()
        ));
    }

    public StrayFromHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose one --
        // * Rewrite the Past -- Return one or two target creature and/or Spacecraft cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(1, 2, filter));
        this.getSpellAbility().withFirstModeFlavorWord("Rewrite the Past");

        // * Repair the Timeline -- Target opponent reveals their hand. You choose a nonland card from it. Exile that card.
        Mode mode = new Mode(new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_NON_LAND)
            .setText("target opponent reveals their hand. You choose a nonland card from it. Exile that card"));
        mode.addTarget(new TargetOpponent());
        mode.withFlavorWord("Repair the Timeline");
        this.getSpellAbility().addMode(mode);
    }

    private StrayFromHistory(final StrayFromHistory card) {
        super(card);
    }

    @Override
    public StrayFromHistory copy() {
        return new StrayFromHistory(this);
    }
}
