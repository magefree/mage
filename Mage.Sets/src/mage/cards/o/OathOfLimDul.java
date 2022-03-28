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
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
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
        return event.getType() == GameEvent.EventType.LOST_LIFE;
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

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("controlled permanent other than Oath of Lim-Dul to sacrifice");

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
        boolean sacrificeDone = false;
        int numberSacrificed = 0;
        int numberToDiscard = 0;
        int numberOfControlledPermanents = 0;
        Player controller = game.getPlayer(source.getControllerId());
        int amountDamage = (int) game.getState().getValue(source.getSourceId().toString() + "oathOfLimDul");
        if (amountDamage > 0
                && controller != null) {
            TargetControlledPermanent target = new TargetControlledPermanent(0, numberOfControlledPermanents, filter, true);
            target.setNotTarget(true);
            if (controller.choose(Outcome.Detriment, target, source, game)) {
                for (UUID targetPermanentId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetPermanentId);
                    if (permanent != null
                            && permanent.sacrifice(source, game)) {
                        numberSacrificed += 1;
                        sacrificeDone = true;
                    }
                }
            }
            numberToDiscard = amountDamage - numberSacrificed;
            Cost cost = new DiscardTargetCost(new TargetCardInHand(numberToDiscard, new FilterCard("card(s) in your hand to discard")));
            if (numberToDiscard > 0
                    && cost.canPay(source, source, controller.getId(), game)) {
                return cost.pay(source, game, source, controller.getId(), true);  // discard cost paid simultaneously
            }
        }
        return sacrificeDone;
    }

    @Override
    public OathOfLimDulEffect copy() {
        return new OathOfLimDulEffect(this);
    }

}
