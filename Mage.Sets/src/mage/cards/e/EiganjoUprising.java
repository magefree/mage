package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SamuraiToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EiganjoUprising extends CardImpl {

    public EiganjoUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{W}");

        // Create X 2/2 white Samurai creature tokens with vigilance. They gain menace and haste until end of turn.
        // Each opponent creates X minus one 2/2 white Samurai creature tokens with vigilance.
        this.getSpellAbility().addEffect(new EiganjoUprisingEffect());
    }

    private EiganjoUprising(final EiganjoUprising card) {
        super(card);
    }

    @Override
    public EiganjoUprising copy() {
        return new EiganjoUprising(this);
    }
}

class EiganjoUprisingEffect extends OneShotEffect {

    EiganjoUprisingEffect() {
        super(Outcome.Benefit);
        staticText = "create X 2/2 white Samurai creature tokens with vigilance. " +
                "They gain menace and haste until end of turn.<br>Each opponent " +
                "creates X minus one 2/2 white Samurai creature tokens with vigilance";
    }

    private EiganjoUprisingEffect(final EiganjoUprisingEffect effect) {
        super(effect);
    }

    @Override
    public EiganjoUprisingEffect copy() {
        return new EiganjoUprisingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        if (amount < 1) {
            return false;
        }
        Token token = new SamuraiToken();
        token.putOntoBattlefield(amount, game, source);
        game.addEffect(new GainAbilityTargetEffect(new MenaceAbility(false))
                .setTargetPointer(new FixedTargets(token, game)), source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(token, game)), source);
        if (amount < 2) {
            return true;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            token.putOntoBattlefield(amount - 1, game, source, opponentId);
        }
        return true;
    }
}
