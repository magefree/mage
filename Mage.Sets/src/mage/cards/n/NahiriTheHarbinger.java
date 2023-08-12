package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class NahiriTheHarbinger extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchantment, tapped artifact, or tapped creature");

    static {
        filter.add(Predicates.or(CardType.ENCHANTMENT.getPredicate(),
                (Predicates.and(CardType.ARTIFACT.getPredicate(),
                        TappedPredicate.TAPPED)),
                (Predicates.and(CardType.CREATURE.getPredicate(),
                        TappedPredicate.TAPPED))));
    }

    public NahiriTheHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAHIRI);

        this.setStartingLoyalty(4);

        // +2: You may discard a card. If you do, draw a card.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), 2));

        // -2: Exile target enchantment, tapped artifact, or tapped creature.
        LoyaltyAbility ability = new LoyaltyAbility(new ExileTargetEffect(), -2);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -8: Search your library for an artifact or creature card, put it onto the battlefield, then shuffle your library. It gains haste.
        // Return it to your hand at the beginning of the next end step.
        this.addAbility(new LoyaltyAbility(new NahiriTheHarbingerEffect(), -8));
    }

    private NahiriTheHarbinger(final NahiriTheHarbinger card) {
        super(card);
    }

    @Override
    public NahiriTheHarbinger copy() {
        return new NahiriTheHarbinger(this);
    }
}

class NahiriTheHarbingerEffect extends SearchEffect {

    private static final FilterCard filterCard = new FilterCard("artifact or creature card");

    static {
        filterCard.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    NahiriTheHarbingerEffect() {
        super(new TargetCardInLibrary(0, 1, filterCard), Outcome.PutCardInPlay);
        this.staticText = "Search your library for an artifact or creature card, put it onto the battlefield, then shuffle. It gains haste. Return it to your hand at the beginning of the next end step";
    }

    NahiriTheHarbingerEffect(final NahiriTheHarbingerEffect effect) {
        super(effect);
    }

    @Override
    public NahiriTheHarbingerEffect copy() {
        return new NahiriTheHarbingerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        Permanent permanent = game.getPermanent(card.getId());
                        if (permanent != null) {
                            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                            effect.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                            game.addEffect(effect, source);
                            Effect effect2 = new ReturnToHandTargetEffect();
                            effect2.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect2);
                            game.addDelayedTriggeredAbility(delayedAbility, source);
                        }
                    }
                }
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
