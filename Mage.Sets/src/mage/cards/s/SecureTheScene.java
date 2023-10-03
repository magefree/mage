package mage.cards.s;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecureTheScene extends CardImpl {

    public SecureTheScene(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Exile target nonland permanent. Its controller creates a 1/1 white Soldier creature token.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new SoldierToken()));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private SecureTheScene(final SecureTheScene card) {
        super(card);
    }

    @Override
    public SecureTheScene copy() {
        return new SecureTheScene(this);
    }
}