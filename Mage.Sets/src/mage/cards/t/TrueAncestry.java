package mage.cards.t;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrueAncestry extends CardImpl {

    public TrueAncestry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        this.subtype.add(SubType.LESSON);

        // Return up to one target permanent card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_PERMANENT));

        // Create a Clue token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ClueArtifactToken()).concatBy("<br>"));
    }

    private TrueAncestry(final TrueAncestry card) {
        super(card);
    }

    @Override
    public TrueAncestry copy() {
        return new TrueAncestry(this);
    }
}
