package mage.cards.g;

import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoBlank extends CardImpl {

    public GoBlank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player discards two cards. Then exile all cards from that player's graveyard.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addEffect(new ExileGraveyardAllTargetPlayerEffect()
                .setText("Then exile that player's graveyard"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private GoBlank(final GoBlank card) {
        super(card);
    }

    @Override
    public GoBlank copy() {
        return new GoBlank(this);
    }
}
