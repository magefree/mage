package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterPlayerOrPlaneswalker;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TahngarthFirstMate extends CardImpl {

    public TahngarthFirstMate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Tahngarth, First Mate can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()));

        // Whenever an opponent attacks with one or more creatures, if Tahngarth is tapped, you may have that opponent gain control of Tahngarth until end of combat. If you do, choose a player or planeswalker that opponent is attacking. Tahngarth is attacking that player or planeswalker.
        this.addAbility(new TahngarthFirstMateTriggeredAbility());
    }

    private TahngarthFirstMate(final TahngarthFirstMate card) {
        super(card);
    }

    @Override
    public TahngarthFirstMate copy() {
        return new TahngarthFirstMate(this);
    }
}

class TahngarthFirstMateTriggeredAbility extends TriggeredAbilityImpl {

    TahngarthFirstMateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TahngarthFirstMateEffect(), true);
    }

    private TahngarthFirstMateTriggeredAbility(final TahngarthFirstMateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TahngarthFirstMateTriggeredAbility copy() {
        return new TahngarthFirstMateTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(game.getActivePlayerId())
                && !game.getCombat().getAttackers().isEmpty();
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.isTapped();
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks with one or more creatures, " +
                "if {this} is tapped, you may have that opponent gain control of {this} until end of combat. " +
                "If you do, choose a player or planeswalker that opponent is attacking. " +
                "{this} is attacking that player or planeswalker.";
    }
}

class TahngarthFirstMateEffect extends OneShotEffect {

    private static final FilterPlayerOrPlaneswalker filter
            = new FilterPlayerOrPlaneswalker("player or planeswalker active player is attacking");

    static {
        filter.getPlayerFilter().add(TahngarthFirstMatePlayerPredicate.instance);
        filter.getPermanentFilter().add(TahngarthFirstMatePermanentPredicate.instance);
    }

    TahngarthFirstMateEffect() {
        super(Outcome.Benefit);
    }

    private TahngarthFirstMateEffect(final TahngarthFirstMateEffect effect) {
        super(effect);
    }

    @Override
    public TahngarthFirstMateEffect copy() {
        return new TahngarthFirstMateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller == null || player == null || permanent == null) {
            return false;
        }
        TargetPlayerOrPlaneswalker target = new TargetPlayerOrPlaneswalker(filter);
        target.setNotTarget(true);
        if (!controller.choose(outcome, target, source, game)) {
            return false;
        }
        ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfCombat, player.getId());
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        game.getState().processAction(game);
        return game.getCombat().addAttackerToCombat(permanent.getId(), target.getFirstTarget(), game);
    }
}

enum TahngarthFirstMatePlayerPredicate implements Predicate<Player> {
    instance;

    @Override
    public boolean apply(Player input, Game game) {
        return game.getCombat().getDefenders().contains(input.getId());
    }
}

enum TahngarthFirstMatePermanentPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return game.getCombat().getDefenders().contains(input.getId());
    }
}