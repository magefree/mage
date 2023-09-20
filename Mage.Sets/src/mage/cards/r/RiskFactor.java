package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiskFactor extends CardImpl {

    public RiskFactor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Target opponent may have Risk Factor deal 4 damage to them. If that player doesn't, you draw three cards.
        this.getSpellAbility().addEffect(new RiskFactorEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));

    }

    private RiskFactor(final RiskFactor card) {
        super(card);
    }

    @Override
    public RiskFactor copy() {
        return new RiskFactor(this);
    }
}

class RiskFactorEffect extends OneShotEffect {

    public RiskFactorEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent may have {this} deal 4 damage to them. "
                + "If that player doesn't, you draw three cards.";
    }

    private RiskFactorEffect(final RiskFactorEffect effect) {
        super(effect);
    }

    @Override
    public RiskFactorEffect copy() {
        return new RiskFactorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        if (opponent.chooseUse(outcome, "Do you choose to take the damage?", source, game)) {
            opponent.damage(4, source.getSourceId(), source, game);
        } else {
            controller.drawCards(3, source, game);
        }
        return true;
    }
}
