package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToBattlefieldAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class NextOfKin extends CardImpl {

    public NextOfKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.addSubType(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // When enchanted creature dies, you may put a creature card you own with lesser mana value from your hand or from the command zone onto the battlefield.
        // If you do, return Next of Kin to the battlefield attached to that creature at the beginning of the next end step.
        this.addAbility(new DiesAttachedTriggeredAbility(new NextOfKinDiesEffect(), "enchanted creature", true));
    }

    private NextOfKin(final NextOfKin card) {
        super(card);
    }

    @Override
    public NextOfKin copy() {
        return new NextOfKin(this);
    }
}

class NextOfKinDiesEffect extends OneShotEffect {

    NextOfKinDiesEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put a creature card you own with lesser mana value from your hand or from the command zone onto the battlefield. " +
                "If you do, return {this} to the battlefield attached to that creature at the beginning of the next end step.";
    }

    private NextOfKinDiesEffect(final NextOfKinDiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card nextOfKinCard = (Card) source.getSourceObjectIfItStillExists(game);
        Object object = getValue("attachedTo");
        if (controller == null || nextOfKinCard == null || !(object instanceof Permanent)) {
            return false;
        }
        int manaValue = ((Permanent) object).getManaValue();

        FilterCreatureCard filter = new FilterCreatureCard("a creature card you own with lesser mana value");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, manaValue));
        Cards cards = new CardsImpl();
        cards.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
        for (Card possibleCard : game.getCommanderCardsFromCommandZone(controller, CommanderCardType.ANY)) {
            if (filter.match(possibleCard, source.getControllerId(), source, game)) {
                cards.add(possibleCard);
            }
        }
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
        target.withNotTarget(true);
        controller.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            Effect returnToBattlefieldAttachedEffect = new ReturnToBattlefieldAttachedEffect();
            returnToBattlefieldAttachedEffect.setTargetPointer(new FixedTarget(card, game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnToBattlefieldAttachedEffect), source);
        }
        return true;
    }

    @Override
    public NextOfKinDiesEffect copy() {
        return new NextOfKinDiesEffect(this);
    }
}
