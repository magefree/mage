package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author padfoothelix
 */
public final class TheSecondDoctor extends CardImpl {

    public TheSecondDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Players have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET, TargetController.ANY
        )));

	// How Civil of You -- At the beginning of your end step, each player may draw a card. Each opponent who does can't attack you or permanents you control during their next turn.
	this.addAbility(new BeginningOfEndStepTriggeredAbility(
		new TheSecondDoctorEffect()).withFlavorWord("How Civil of You"));	
    }

    private TheSecondDoctor(final TheSecondDoctor card) {
        super(card);
    }

    @Override
    public TheSecondDoctor copy() {
        return new TheSecondDoctor(this);
    }
}

class TheSecondDoctorEffect extends OneShotEffect {
    TheSecondDoctorEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player may draw a card. Each opponent who does can't attack you or permanents you control during their next turn.";
    }

    private TheSecondDoctorEffect(final TheSecondDoctorEffect effect) {
        super(effect);
    }

    @Override
    public TheSecondDoctorEffect copy() {
        return new TheSecondDoctorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
	    return false;
	}
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null
	            && player.chooseUse(Outcome.DrawCard, "Draw a card ?", source, game)
		    && player.drawCards(1, source, game) > 0
		    && game.getOpponents(controller.getId()).contains(playerId)) {
	        RestrictionEffect effect = new TheSecondDoctorCantAttackEffect(player.getId());
		game.addEffect(effect, source);
	    }
        }
        return true;
    }
}

class TheSecondDoctorCantAttackEffect extends RestrictionEffect {

    private final UUID opponentId;

    public TheSecondDoctorCantAttackEffect(UUID opponentId) {
        super(Duration.UntilEndOfYourNextTurn);
	this.opponentId = opponentId;
	staticText = "";
    }

    private TheSecondDoctorCantAttackEffect(final TheSecondDoctorCantAttackEffect effect) {
        super(effect);
	this.opponentId = effect.opponentId;
    }

    @Override
    public TheSecondDoctorCantAttackEffect copy() {
        return new TheSecondDoctorCantAttackEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
	if (opponentId != null) {
        setStartingControllerAndTurnNum(game, opponentId, game.getActivePlayerId());
        } else {
            discard();
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return game.isActivePlayer(opponentId);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        UUID controllerId = source.getControllerId();
	// defender is null
	if (defenderId == null) {
	     return true;
	}
	// defender is a permanent
	Permanent defender = game.getPermanent(defenderId);
	if (defender != null) {
	     return !defender.isControlledBy(controllerId);
	}
	// defender is a player
        return !defenderId.equals(controllerId);
    }
}
