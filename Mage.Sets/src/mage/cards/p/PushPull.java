package mage.cards.p;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author DominionSpy
 */
public final class PushPull extends SplitCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public PushPull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W/B}", "{4}{B/R}{B/R}", SpellAbilityType.SPLIT);

        // Push
        // Destroy target tapped creature.
        getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Pull
        // Put up to two target creature cards from a single graveyard onto the battlefield under your control. They gain haste until end of turn. Sacrifice them at the beginning of the next end step.
        getRightHalfCard().getSpellAbility().addEffect(new PullEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 2,
                new FilterCreatureCard("up to two creature cards")));

    }

    private PushPull(final PushPull card) {
        super(card);
    }

    @Override
    public PushPull copy() {
        return new PushPull(this);
    }
}

class PullEffect extends OneShotEffect {

    PullEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Put up to two target creature cards from a single graveyard onto the battlefield under your control. " +
                "They gain haste until end of turn. Sacrifice them at the beginning of the next end step.";
    }

    private PullEffect(final PullEffect effect) {
        super(effect);
    }

    @Override
    public PullEffect copy() {
        return new PullEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> cards = getTargetPointer().getTargets(game, source)
            .stream()
            .map(game::getCard)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        if (controller == null || cards.isEmpty()
            || !controller.moveCards(cards, Zone.BATTLEFIELD, source, game)) {
            return false;
        }

        Set<Permanent> permanents = cards.stream()
                .map(Card::getId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (permanents.isEmpty()) {
            return false;
        }

        permanents.forEach(permanent -> {
            FixedTarget blueprintTarget = new FixedTarget(permanent, game);

            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance());
            effect.setTargetPointer(blueprintTarget.copy());
            game.addEffect(effect, source);

            Effect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + permanent.getLogName(), controller.getId());
            sacrificeEffect.setTargetPointer(blueprintTarget.copy());
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
        });

        return true;
    }
}
