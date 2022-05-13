package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AzraOddsmaker extends CardImpl {

    public AzraOddsmaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, you may discard a card. If you do, choose a creature. Whenever that creature deals combat damage to a player this turn, you draw two cards.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(
                        new AzraOddsmakerEffect(),
                        new DiscardCardCost()
                ), TargetController.YOU, false
        ));
    }

    private AzraOddsmaker(final AzraOddsmaker card) {
        super(card);
    }

    @Override
    public AzraOddsmaker copy() {
        return new AzraOddsmaker(this);
    }
}

class AzraOddsmakerEffect extends OneShotEffect {

    public AzraOddsmakerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a creature. Whenever that creature deals combat damage to a player this turn, you draw two cards";
    }

    public AzraOddsmakerEffect(final AzraOddsmakerEffect effect) {
        super(effect);
    }

    @Override
    public AzraOddsmakerEffect copy() {
        return new AzraOddsmakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = null;
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setNotTarget(true);
        if (player.choose(Outcome.DrawCard, target, source, game)) {
            permanent = game.getPermanent(target.getFirstTarget());
        }
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new AzraOddsmakerDelayedTriggeredAbility(
                new MageObjectReference(permanent, game),
                permanent.getName()
        ), source);
        return true;
    }
}

class AzraOddsmakerDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;
    private final String creatureName;

    public AzraOddsmakerDelayedTriggeredAbility(MageObjectReference mor, String creatureName) {
        super(new DrawCardSourceControllerEffect(2), Duration.EndOfTurn, false, false);
        this.mor = mor;
        this.creatureName = creatureName;
    }

    public AzraOddsmakerDelayedTriggeredAbility(final AzraOddsmakerDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
        this.creatureName = ability.creatureName;
    }

    @Override
    public AzraOddsmakerDelayedTriggeredAbility copy() {
        return new AzraOddsmakerDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            return mor.refersTo(permanent, game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + creatureName + " deals combat damage to a player this turn, you draw two cards";
    }
}
