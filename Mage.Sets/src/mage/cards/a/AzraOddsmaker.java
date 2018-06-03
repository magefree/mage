
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
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
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AzraOddsmakerEffect(), TargetController.YOU, true));
    }

    public AzraOddsmaker(final AzraOddsmaker card) {
        super(card);
    }

    @Override
    public AzraOddsmaker copy() {
        return new AzraOddsmaker(this);
    }
}

class AzraOddsmakerEffect extends OneShotEffect {

    AzraOddsmakerEffect() {
        super(Outcome.Benefit);
        this.staticText = "discard a card. If you do, choose a creature. Whenever that creature deals combat damage to a player this turn, you draw two cards";
    }

    AzraOddsmakerEffect(final AzraOddsmakerEffect effect) {
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
        Cost cost = new DiscardCardCost();
        Permanent permanent = null;
        if (cost.canPay(source, source.getSourceId(), source.getControllerId(), game)
                && cost.pay(source, game, source.getSourceId(), source.getControllerId(), true)) {
            TargetCreaturePermanent target = new TargetCreaturePermanent();
            target.setNotTarget(true);
            if (player.choose(Outcome.DrawCard, target, source.getSourceId(), game)) {
                permanent = game.getPermanent(target.getFirstTarget());
            }
        }
        if (permanent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new MageObjectReferencePredicate(new MageObjectReference(permanent, game)));
        game.addDelayedTriggeredAbility(new AzraOddsmakerDelayedTriggeredAbility(filter, permanent.getName()), source);
        return true;
    }
}

class AzraOddsmakerDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final FilterCreaturePermanent filter;
    private final String creatureName;

    AzraOddsmakerDelayedTriggeredAbility(FilterCreaturePermanent filter, String creatureName) {
        super(new DrawCardSourceControllerEffect(2), Duration.EndOfTurn, false);
        this.filter = filter;
        this.creatureName = creatureName;
    }

    AzraOddsmakerDelayedTriggeredAbility(final AzraOddsmakerDelayedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
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
        if (event.getFlag()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && filter.match(permanent, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + creatureName + " deals damage to a player this turn, you draw two cards";
    }
}
