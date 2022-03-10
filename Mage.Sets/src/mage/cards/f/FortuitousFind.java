package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FortuitousFind extends CardImpl {

    public FortuitousFind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose one or both &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Return target artifact card from your graveyard to your hand.;
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD).withChooseHint("return to hand"));

        // or Return target creature card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD).withChooseHint("return to hand"));
        this.getSpellAbility().addMode(mode);
    }

    private FortuitousFind(final FortuitousFind card) {
        super(card);
    }

    @Override
    public FortuitousFind copy() {
        return new FortuitousFind(this);
    }
}
