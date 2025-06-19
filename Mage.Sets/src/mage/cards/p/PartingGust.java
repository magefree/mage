package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author xenohedron
 */
public final class PartingGust extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public PartingGust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{W}");

        // Gift a tapped Fish
        this.addAbility(new GiftAbility(this, GiftType.TAPPED_FISH));

        // Exile target nontoken creature. If the gift wasn't promised, return that creature to the battlefield under its owner's control with a +1/+1 counter on it at the beginning of the next end step.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ExileTargetEffect(),
                new PartingGustExileReturnEffect(),
                GiftWasPromisedCondition.TRUE,
                "Exile target nontoken creature. If the gift wasn't promised, return that card to the " +
                        "battlefield under its owner's control with a +1/+1 counter on it at the beginning of the next end step."
        ));
    }

    private PartingGust(final PartingGust card) {
        super(card);
    }

    @Override
    public PartingGust copy() {
        return new PartingGust(this);
    }
}

class PartingGustExileReturnEffect extends OneShotEffect {

    PartingGustExileReturnEffect() {
        super(Outcome.Neutral);
    }

    private PartingGustExileReturnEffect(final PartingGustExileReturnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> toExile = getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (toExile.isEmpty()) {
            return false;
        }
        controller.moveCardsToExile(toExile, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        Effect effect = new ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect(CounterType.P1P1.createInstance(), false, false);
        effect.setTargetPointer(new FixedTargets(toExile
                .stream()
                .map(Card::getMainCard)
                .collect(Collectors.toSet()), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }

    @Override
    public PartingGustExileReturnEffect copy() {
        return new PartingGustExileReturnEffect(this);
    }

}
