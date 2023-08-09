package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.HumanSoldierToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class FaramirPrinceOfIthilien extends CardImpl {

    public FaramirPrinceOfIthilien(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, choose an opponent.
        // At the beginning of that player's next end step,
        // you draw a card if they didn't attack you that turn.
        // Otherwise, create three 1/1 white Human Soldier creature tokens.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new FaramirPrinceOfIthilienEffect(), false));
    }

    private FaramirPrinceOfIthilien(final FaramirPrinceOfIthilien card) {
        super(card);
    }

    @Override
    public FaramirPrinceOfIthilien copy() {
        return new FaramirPrinceOfIthilien(this);
    }

}

class FaramirPrinceOfIthilienEffect extends OneShotEffect {

    FaramirPrinceOfIthilienEffect() {
        super(Outcome.Neutral);
        staticText = "choose an opponent. " +
            "At the beginning of that player's next end step, " +
            "you draw a card if they didn't attack you that turn. " +
            "Otherwise, create three 1/1 white Human Soldier creature tokens.";
    }

    FaramirPrinceOfIthilienEffect(final FaramirPrinceOfIthilienEffect effect) {
        super(effect);
    }

    @Override
    public FaramirPrinceOfIthilienEffect copy() {
        return new FaramirPrinceOfIthilienEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Target target = new TargetOpponent(true);
        target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return false;
        }

        Effect effect = new FaramirPrinceOfIthilienDelayedEffect();
        effect.setTargetPointer(new FixedTarget(opponent.getId(), game));
        game.addDelayedTriggeredAbility(
            new AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility(
                effect,
                opponent.getId()
            ), source);

        return true;
    }
}


class FaramirPrinceOfIthilienDelayedEffect extends OneShotEffect {

    FaramirPrinceOfIthilienDelayedEffect() {
        super(Outcome.Benefit);
        staticText = "you draw a card if the chosen player didn't attack you that turn. " +
            "Otherwise, create three 1/1 white Human Soldier creature tokens.";
    }

    FaramirPrinceOfIthilienDelayedEffect(final FaramirPrinceOfIthilienDelayedEffect effect) {
        super(effect);
    }

    @Override
    public FaramirPrinceOfIthilienDelayedEffect copy() {
        return new FaramirPrinceOfIthilienDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayersAttackedThisTurnWatcher watcher = game.getState().getWatcher(PlayersAttackedThisTurnWatcher.class);
        if(watcher == null){
            return false;
        }

        UUID controllerId = source.getControllerId();
        UUID targetId = getTargetPointer().getFirst(game, source);

        Player controller = game.getPlayer(controllerId);
        if(controller == null){
            return false;
        }

        if(watcher.hasPlayerAttackedPlayer(targetId, controllerId)){
            return new CreateTokenEffect(new HumanSoldierToken(), 3).apply(game, source);
        } else {
            return new DrawCardSourceControllerEffect(1).apply(game, source);
        }
    }
}