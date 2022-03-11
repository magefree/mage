package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AidTheFallen extends CardImpl {

    private static final FilterCard filter = new FilterPlaneswalkerCard("planeswalker card from your graveyard");

    public AidTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose one or both—
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ).withChooseHint("returns a creature card to your hand"));

        // • Return target planeswalker card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter)
                .withChooseHint("returns a planeswalker card to your hand"));
        this.getSpellAbility().addMode(mode);
    }

    private AidTheFallen(final AidTheFallen card) {
        super(card);
    }

    @Override
    public AidTheFallen copy() {
        return new AidTheFallen(this);
    }
}
