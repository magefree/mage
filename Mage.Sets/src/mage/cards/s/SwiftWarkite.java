package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class SwiftWarkite extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature card with mana value 3 or less");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SwiftWarkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Swift Warkite enters the battlefield, you may put a creature card with converted mana cost 3 or less from your hand or graveyard onto the battlefield. That creature gains haste. Return it to your hand at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SwiftWarkiteEffect()));
    }

    private SwiftWarkite(final SwiftWarkite card) {
        super(card);
    }

    @Override
    public SwiftWarkite copy() {
        return new SwiftWarkite(this);
    }
}

class SwiftWarkiteEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature card with mana value 3 or less");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    SwiftWarkiteEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put a creature card with mana value 3 or less from your hand or graveyard onto the battlefield. " +
                "That creature gains haste. Return it to your hand at the beginning of the next end step";
    }

    private SwiftWarkiteEffect(final SwiftWarkiteEffect effect) {
        super(effect);
    }

    @Override
    public SwiftWarkiteEffect copy() {
        return new SwiftWarkiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
        cards.addAllCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
        target.withNotTarget(true);
        controller.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);

            ContinuousEffect gainHasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            gainHasteEffect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(gainHasteEffect, source);

            Effect returnToHandEffect = new ReturnToHandTargetEffect();
            returnToHandEffect.setTargetPointer(new FixedTarget(card, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnToHandEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }

}
