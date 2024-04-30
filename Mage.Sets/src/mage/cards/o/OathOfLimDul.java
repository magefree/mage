package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class OathOfLimDul extends CardImpl {

    public OathOfLimDul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever you lose life, for each 1 life you lost, sacrifice a permanent other than Oath of Lim-Dul unless you discard a card.
        this.addAbility(new LoseLifeTriggeredAbility(new OathOfLimDulEffect(), TargetController.YOU));

        // {B}{B}: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{B}{B}")));
    }

    private OathOfLimDul(final OathOfLimDul card) {
        super(card);
    }

    @Override
    public OathOfLimDul copy() {
        return new OathOfLimDul(this);
    }
}

class OathOfLimDulEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("controlled permanent other than Oath of Lim-Dul to sacrifice");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public OathOfLimDulEffect() {
        super(Outcome.Detriment);
        staticText = "for each 1 life you lost, sacrifice a permanent other than {this} unless you discard a card";
    }

    private OathOfLimDulEffect(final OathOfLimDulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amountDamage = SavedLifeLossValue.MANY.calculate(game, source, this);
        Player controller = game.getPlayer(source.getControllerId());
        if (amountDamage <= 0 || controller == null) {
            return false;
        }
        boolean sacrificeDone = false;
        int numberSacrificed = 0;
        int numberToDiscard = 0;
        int numberOfControlledPermanents = 0;
        TargetControlledPermanent target = new TargetControlledPermanent(0, numberOfControlledPermanents, filter, true);
        target.withNotTarget(true);
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
        return sacrificeDone;
    }

    @Override
    public OathOfLimDulEffect copy() {
        return new OathOfLimDulEffect(this);
    }

}
