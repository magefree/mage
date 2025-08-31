package mage.cards.l;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWorldToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostInTheSpiritWorld extends CardImpl {

    public LostInTheSpiritWorld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Return up to one target creature to its owner's hand. Create a 1/1 colorless Spirit creature token with "This token can't block or be blocked by non-Spirit creatures."
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWorldToken()));
    }

    private LostInTheSpiritWorld(final LostInTheSpiritWorld card) {
        super(card);
    }

    @Override
    public LostInTheSpiritWorld copy() {
        return new LostInTheSpiritWorld(this);
    }
}
