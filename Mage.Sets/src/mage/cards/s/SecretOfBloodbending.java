package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.common.WaterbendedCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.abilities.keyword.WaterbendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.turn.CombatPhase;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class SecretOfBloodbending extends CardImpl {

    public SecretOfBloodbending(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}{U}{U}");

        this.subtype.add(SubType.LESSON);

        // As an additional cost to cast this spell, you may waterbend {10}.
        this.addAbility(new WaterbendAbility(10));

        // You control target opponent during their next combat phase.
        // If this spell’s additional cost was paid, you control that player during their next turn instead.
        this.getSpellAbility().addEffect(new SecretOfBloodbendingEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Exile Secret of Bloodbending.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private SecretOfBloodbending(final SecretOfBloodbending card) {
        super(card);
    }

    @Override
    public SecretOfBloodbending copy() {
        return new SecretOfBloodbending(this);
    }
}

class SecretOfBloodbendingEffect extends OneShotEffect {

    SecretOfBloodbendingEffect() {
        super(Outcome.GainControl);
        staticText = "You control target opponent during their next combat phase. " +
                "If this spell's additional cost was paid, you control that player during their next turn instead";
    }

    private SecretOfBloodbendingEffect(final SecretOfBloodbendingEffect effect) {
        super(effect);
    }

    @Override
    public SecretOfBloodbendingEffect copy() {
        return new SecretOfBloodbendingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || controller == null) {
            return false;
        }
        if (WaterbendedCondition.instance.apply(game, source)) {
            game.informPlayers(controller.getLogName() + " will take control of "
                    + targetPlayer.getLogName() + "'s next turn");
            Effect controlPlayerEffect = new ControlTargetPlayerNextTurnEffect();
            controlPlayerEffect.apply(game, source);
        } else {
            game.informPlayers(controller.getLogName() + " will take control of "
                    + targetPlayer.getLogName() + "'s next combat");
            Effect controlCombatEffect = new ControlPlayerCombatEffect();
            controlCombatEffect.apply(game, source);
        }


        return true;
    }
}

class ControlPlayerCombatEffect extends OneShotEffect {

    public ControlPlayerCombatEffect() {
        super(Outcome.Benefit);
        staticText = "You control target opponent during their next combat phase";
    }

    protected ControlPlayerCombatEffect(final ControlPlayerCombatEffect effect) {
        super(effect);
    }

    @Override
    public ControlPlayerCombatEffect copy() {
        return new ControlPlayerCombatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || controller == null) {
            return false;
        }
        // Possible generalization for any phase
        TurnMod combat = new TurnMod(targetPlayer.getId())
                .withPhaseController(new CombatPhase(), controller.getId());
        game.getState().getTurnMods().add(combat);
        return true;
    }
}


