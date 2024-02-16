package mage.cards.w;

import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WritOfReturn extends CardImpl {

    public WritOfReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Return target creature card from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private WritOfReturn(final WritOfReturn card) {
        super(card);
    }

    @Override
    public WritOfReturn copy() {
        return new WritOfReturn(this);
    }
}
