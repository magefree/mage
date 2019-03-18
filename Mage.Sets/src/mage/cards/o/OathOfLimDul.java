package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class OathOfLimDul extends CardImpl {

    public OathOfLimDul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever you lose life, for each 1 life you lost, sacrifice a permanent other than Oath of Lim-Dul unless you discard a card.
        this.addAbility(new OathOfLimDulTriggeredAbility());

        // {B}{B}: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{B}{B}")));

    }

    private OathOfLimDul(final OathOfLimDul card) {
        super(card);
    }

    @Override
    public OathOfLimDul copy() {
        return new OathOfLimDul(this);
    }
}

class OathOfLimDulTriggeredAbility extends TriggeredAbilityImpl {

    public OathOfLimDulTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OathOfLimDulEffect());
    }

    public OathOfLimDulTriggeredAbility(final OathOfLimDulTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OathOfLimDulTriggeredAbility copy() {
        return new OathOfLimDulTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            game.getState().setValue(sourceId.toString() + "oathOfLimDul", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you lose life, for each 1 life you lost, sacrifice a permanent other than {this} unless you discard a card.";
    }
}

class OathOfLimDulEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("controlled permanent other than Oath of Lim-Dul");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public OathOfLimDulEffect() {
        super(Outcome.Neutral);
    }

    public OathOfLimDulEffect(final OathOfLimDulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int amount = (int) game.getState().getValue(source.getSourceId().toString() + "oathOfLimDul");
        if (amount > 0
                && controller != null) {
            for (int i = 0; i < amount; i++) {
                TargetControlledPermanent target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(controller.getId(), game)
                        && controller.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                    Cost cost = new DiscardTargetCost(new TargetCardInHand());
                    if (cost.canPay(source, source.getSourceId(), controller.getId(), game)
                            && controller.chooseUse(Outcome.Benefit, 
                                    "Do you wish to discard a card rather than sacrifice the target permanent?", source, game)) {
                        cost.pay(source, game, source.getSourceId(), controller.getId(), true);
                    } else {
                        Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                        if (targetPermanent != null) {
                            targetPermanent.sacrifice(source.getSourceId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public OathOfLimDulEffect copy() {
        return new OathOfLimDulEffect(this);
    }

}
