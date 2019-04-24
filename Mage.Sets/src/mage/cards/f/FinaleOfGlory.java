package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.AngelVigilanceToken;
import mage.game.permanent.token.SoldierVigilanceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinaleOfGlory extends CardImpl {

    public FinaleOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}");

        // Create X 2/2 white Soldier creature tokens with vigilance. If X is 10 or more, also create X 4/4 white Angel creature tokens with flying and vigilance.
        this.getSpellAbility().addEffect(new FinaleOfGloryEffect());
    }

    private FinaleOfGlory(final FinaleOfGlory card) {
        super(card);
    }

    @Override
    public FinaleOfGlory copy() {
        return new FinaleOfGlory(this);
    }
}

class FinaleOfGloryEffect extends OneShotEffect {

    FinaleOfGloryEffect() {
        super(Outcome.Benefit);
        staticText = "Create X 2/2 white Soldier creature tokens with vigilance. " +
                "If X is 10 or more, also create X 4/4 white Angel creature tokens with flying and vigilance.";
    }

    private FinaleOfGloryEffect(final FinaleOfGloryEffect effect) {
        super(effect);
    }

    @Override
    public FinaleOfGloryEffect copy() {
        return new FinaleOfGloryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        if (xValue == 0) {
            return false;
        }
        new CreateTokenEffect(new SoldierVigilanceToken(), xValue).apply(game, source);
        if (xValue >= 10) {
            new CreateTokenEffect(new AngelVigilanceToken(), xValue).apply(game, source);
        }
        return true;
    }
}