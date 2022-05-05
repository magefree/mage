package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CulturalExchange extends CardImpl {

    public CulturalExchange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Choose any number of creatures target player controls. Choose the same number of creatures another target player controls. Those players exchange control of those creatures.
        this.getSpellAbility().addEffect(new CulturalExchangeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(2));
    }

    private CulturalExchange(final CulturalExchange card) {
        super(card);
    }

    @Override
    public CulturalExchange copy() {
        return new CulturalExchange(this);
    }
}

class CulturalExchangeEffect extends OneShotEffect {

    CulturalExchangeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose any number of creatures target player controls. "
                + "Choose the same number of creatures another target player controls. "
                + "Those players exchange control of those creatures.";
    }

    CulturalExchangeEffect(final CulturalExchangeEffect effect) {
        super(effect);
    }

    @Override
    public CulturalExchangeEffect copy() {
        return new CulturalExchangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player1 = game.getPlayer(targetPointer.getTargets(game, source).get(0));
        Player player2 = game.getPlayer(targetPointer.getTargets(game, source).get(1));
        Player controller = game.getPlayer(source.getControllerId());
        if (player1 == null || player2 == null || controller == null) {
            return false;
        }
        FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creatures " + player1.getLogName() + " controls");
        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures " + player2.getLogName() + " controls");
        filter1.add(new ControllerIdPredicate(player1.getId()));
        filter2.add(new ControllerIdPredicate(player2.getId()));
        int creatureCount1 = game.getBattlefield().count(filter1, source.getControllerId(), source, game);
        int creatureCount2 = game.getBattlefield().count(filter2, source.getControllerId(), source, game);
        int creaturesToSwitch = Math.min(creatureCount1, creatureCount2);
        if (creaturesToSwitch == 0) {
            return true;
        }
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(0, creaturesToSwitch, filter1, true);
        if (target1.choose(Outcome.Benefit, controller.getId(), source.getSourceId(), source, game)) {
            int otherToSwitch = target1.getTargets().size();
            TargetCreaturePermanent target2 = new TargetCreaturePermanent(otherToSwitch, otherToSwitch, filter2, true);
            if (target2.choose(Outcome.Benefit, controller.getId(), source.getSourceId(), source, game)) {
                for (UUID creatureId : target1.getTargets()) {
                    Permanent creature = game.getPermanent(creatureId);
                    if (creature != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, player2.getId());
                        game.informPlayers(player2.getLogName() + " gains control of " + creature.getLogName());
                        effect.setTargetPointer(new FixedTarget(creature, game));
                        game.addEffect(effect, source);
                    }
                }
                for (UUID creatureId : target2.getTargets()) {
                    Permanent creature = game.getPermanent(creatureId);
                    if (creature != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, player1.getId());
                        game.informPlayers(player1.getLogName() + " gains control of " + creature.getLogName());
                        effect.setTargetPointer(new FixedTarget(creature, game));
                        game.addEffect(effect, source);
                    }
                }
            }
        }
        return true;
    }
}
