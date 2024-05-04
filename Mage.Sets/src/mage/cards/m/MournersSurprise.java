package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MercenaryToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MournersSurprise extends CardImpl {

    public MournersSurprise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Return up to one target creature card from your graveyard to your hand. Create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MercenaryToken()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private MournersSurprise(final MournersSurprise card) {
        super(card);
    }

    @Override
    public MournersSurprise copy() {
        return new MournersSurprise(this);
    }
}
