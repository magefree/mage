package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SOLDIERMilitaryProgram extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SOLDIER, "Soldiers you control");

    public SOLDIERMilitaryProgram(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // At the beginning of combat on your turn, choose one. If you control a commander, you may choose both instead.
        // * Create a 1/1 white Soldier creature token.
        Ability ability = new BeginningOfCombatTriggeredAbility(new CreateTokenEffect(new SoldierToken()));
        ability.getModes().setChooseText("choose one. If you control a commander, you may choose both instead.");
        ability.getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // * Put a +1/+1 counter on each of up to two Soldiers you control.
        ability.addMode(new Mode(new SOLDIERMilitaryProgramEffect()));
        this.addAbility(ability);
    }

    private SOLDIERMilitaryProgram(final SOLDIERMilitaryProgram card) {
        super(card);
    }

    @Override
    public SOLDIERMilitaryProgram copy() {
        return new SOLDIERMilitaryProgram(this);
    }
}

class SOLDIERMilitaryProgramEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SOLDIER, "Soldiers you control");

    SOLDIERMilitaryProgramEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on each of up to two Soldiers you control";
    }

    private SOLDIERMilitaryProgramEffect(final SOLDIERMilitaryProgramEffect effect) {
        super(effect);
    }

    @Override
    public SOLDIERMilitaryProgramEffect copy() {
        return new SOLDIERMilitaryProgramEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 2, filter, true);
        player.choose(Outcome.BoostCreature, target, source, game);
        for (UUID targetId : target.getTargets()) {
            Optional.ofNullable(targetId)
                    .map(game::getPermanent)
                    .map(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game));
        }
        return true;
    }
}
