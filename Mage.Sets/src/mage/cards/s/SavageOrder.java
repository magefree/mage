package mage.cards.s;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;
import mage.util.SubTypes;

/**
 *
 * @author notgreat
 */
public final class SavageOrder extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }
    public SavageOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // As an additional cost to cast this spell, sacrifice a creature with power 4 or greater.
        this.getSpellAbility().addCost(new SacrificeTargetCost(filter));
        // Search your library for a Dinosaur creature card, put it onto the battlefield, then shuffle. It gains indestructible until your next turn.
        this.getSpellAbility().addEffect(new SavageOrderEffect());
    }

    private SavageOrder(final SavageOrder card) {
        super(card);
    }

    @Override
    public SavageOrder copy() {
        return new SavageOrder(this);
    }
}
//Based on Nahiri, the Harbinger
class SavageOrderEffect extends SearchEffect {
    private static final FilterCreatureCard filterCard = new FilterCreatureCard("Dinosaur creature card");

    static {
        filterCard.add(SubType.DINOSAUR.getPredicate());
    }

    SavageOrderEffect() {
        super(new TargetCardInLibrary(0, 1, filterCard), Outcome.PutCardInPlay);
        this.staticText = "Search your library for a Dinosaur creature card, put it onto the battlefield, then shuffle. It gains indestructible until your next turn";
    }

    private SavageOrderEffect(final SavageOrderEffect effect) {
        super(effect);
    }

    @Override
    public SavageOrderEffect copy() {
        return new SavageOrderEffect(this);
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
                            ContinuousEffect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn);
                            effect.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                            game.addEffect(effect, source);
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
