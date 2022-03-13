
package mage.cards.s;

import java.util.UUID;
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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class SwiftWarkite extends CardImpl {

    public SwiftWarkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Swift Warkite enters the battlefield, you may put a creature card with converted mana cost 3 or less from your hand or graveyard onto the battlefield. That creature gains haste. Return it to your hand at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SwiftWarkiteEffect(), true));

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

    private static final FilterCard filter = new FilterCard("creature card with mana value 3 or less from your hand or graveyard");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    SwiftWarkiteEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put a creature card with mana value 3 or less from your hand or graveyard onto the battlefield. That creature gains haste. Return it to your hand at the beginning of the next end step";
    }

    SwiftWarkiteEffect(final SwiftWarkiteEffect effect) {
        super(effect);
    }

    @Override
    public SwiftWarkiteEffect copy() {
        return new SwiftWarkiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.PutCardInPlay, "Put a creature card from your hand? (No = from your graveyard)", source, game)) {
                Target target = new TargetCardInHand(0, 1, filter);
                controller.choose(outcome, target, source, game);
                Card card = controller.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                        Permanent creature = game.getPermanent(card.getId());
                        if (creature != null) {
                            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                            effect.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game)));
                            game.addEffect(effect, source);
                            Effect effect2 = new ReturnToHandTargetEffect();
                            effect2.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game)));
                            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect2);
                            game.addDelayedTriggeredAbility(delayedAbility, source);
                        }
                    }
                }
            } else {
                Target target = new TargetCardInYourGraveyard(0, 1, filter);
                target.choose(Outcome.PutCardInPlay, source.getControllerId(), source.getSourceId(), source, game);
                Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    Permanent creature = game.getPermanent(card.getId());
                    if (creature != null) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                        effect.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game)));
                        game.addEffect(effect, source);
                        Effect effect2 = new ReturnToHandTargetEffect();
                        effect2.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game)));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect2);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
