package mage.cards.b;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BuyYourSilence extends CardImpl {

    public BuyYourSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Exile target nonland permanent. Its controller creates a Treasure token.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new TreasureToken()));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private BuyYourSilence(final BuyYourSilence card) {
        super(card);
    }

    @Override
    public BuyYourSilence copy() {
        return new BuyYourSilence(this);
    }
}