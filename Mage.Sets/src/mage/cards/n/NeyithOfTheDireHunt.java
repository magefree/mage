package mage.cards.n;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeyithOfTheDireHunt extends CardImpl {

    public NeyithOfTheDireHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more creatures you control fight or become blocked, draw a card.
        this.addAbility(new NeyithOfTheDireHuntTriggeredAbility());

        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature's power until end of turn. That creature must be blocked this combat if able.
        Ability ability = new BeginningOfCombatTriggeredAbility(new DoIfCostPaid(
                new NeyithOfTheDireHuntEffect(), new ManaCostsImpl<>("{2}{R/G}")
        ), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NeyithOfTheDireHunt(final NeyithOfTheDireHunt card) {
        super(card);
    }

    @Override
    public NeyithOfTheDireHunt copy() {
        return new NeyithOfTheDireHunt(this);
    }
}

// TODO: this needs to work with cards like Choking Vines
class NeyithOfTheDireHuntTriggeredAbility extends TriggeredAbilityImpl {

    NeyithOfTheDireHuntTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    private NeyithOfTheDireHuntTriggeredAbility(final NeyithOfTheDireHuntTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BATCH_FIGHT
                || event.getType() == GameEvent.EventType.DECLARE_BLOCKERS_STEP
                || event.getType() == GameEvent.EventType.BATCH_BLOCK_NONCOMBAT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Object value;
        Set<MageObjectReference> permanents;
        switch (event.getType()) {
            case BATCH_FIGHT:
                value = game.getState().getValue("batchFight_" + event.getData());
                if (!(value instanceof Set)) {
                    return false;
                }
                permanents = (Set<MageObjectReference>) value;
                return permanents
                        .stream()
                        .map(mor -> mor.getPermanentOrLKIBattlefield(game))
                        .filter(Objects::nonNull)
                        .map(Controllable::getControllerId)
                        .anyMatch(this.getControllerId()::equals);
            case BATCH_BLOCK_NONCOMBAT:
                value = game.getState().getValue("becameBlocked_" + event.getData());
                if (!(value instanceof Set)) {
                    return false;
                }
                permanents = (Set<MageObjectReference>) value;
                return permanents
                        .stream()
                        .map(mor -> mor.getPermanentOrLKIBattlefield(game))
                        .filter(Objects::nonNull)
                        .map(Controllable::getControllerId)
                        .anyMatch(this.getControllerId()::equals);
            case DECLARE_BLOCKERS_STEP:
                return game.getCombat()
                        .getGroups()
                        .stream()
                        .filter(CombatGroup::getBlocked)
                        .map(CombatGroup::getAttackers)
                        .flatMap(Collection::stream)
                        .map(game::getControllerId)
                        .anyMatch(this.getControllerId()::equals);
            default:
                return false;
        }
    }

    @Override
    public NeyithOfTheDireHuntTriggeredAbility copy() {
        return new NeyithOfTheDireHuntTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control fight or become blocked, draw a card.";
    }
}

class NeyithOfTheDireHuntEffect extends OneShotEffect {

    NeyithOfTheDireHuntEffect() {
        super(Outcome.Benefit);
        staticText = "double target creature's power until end of turn. " +
                "That creature must be blocked this combat if able";
    }

    private NeyithOfTheDireHuntEffect(final NeyithOfTheDireHuntEffect effect) {
        super(effect);
    }

    @Override
    public NeyithOfTheDireHuntEffect copy() {
        return new NeyithOfTheDireHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        game.addEffect(new BoostTargetEffect(power, 0, Duration.EndOfTurn), source);
        game.addEffect(new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfCombat), source);
        return true;
    }
}
