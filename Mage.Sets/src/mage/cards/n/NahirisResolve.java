package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NahirisResolve extends CardImpl {

    public NahirisResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{W}");

        // Creatures you control get +1/+0 and have haste.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and have haste"));
        this.addAbility(ability);

        // At the beginning of your end step, exile any number of nontoken artifacts and/or creatures you control. Return those cards to the battlefield under their owner's control at the beginning of your next upkeep.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new NahirisResolveExileEffect(), TargetController.YOU, false
        ));
    }

    private NahirisResolve(final NahirisResolve card) {
        super(card);
    }

    @Override
    public NahirisResolve copy() {
        return new NahirisResolve(this);
    }
}

class NahirisResolveExileEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("nontoken artifacts and/or creatures you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(TokenPredicate.FALSE);
    }

    NahirisResolveExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of nontoken artifacts and/or creatures you control. Return those cards " +
                "to the battlefield under their owner's control at the beginning of your next upkeep";
    }

    private NahirisResolveExileEffect(final NahirisResolveExileEffect effect) {
        super(effect);
    }

    @Override
    public NahirisResolveExileEffect copy() {
        return new NahirisResolveExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                new NahirisResolveReturnEffect().setTargetPointer(new FixedTargets(cards, game))
        ), source);
        return true;
    }
}

class NahirisResolveReturnEffect extends OneShotEffect {

    NahirisResolveReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return those cards to the battlefield under their owner's control";
    }

    private NahirisResolveReturnEffect(final NahirisResolveReturnEffect effect) {
        super(effect);
    }

    @Override
    public NahirisResolveReturnEffect copy() {
        return new NahirisResolveReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        return player != null && !cards.isEmpty() && player.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game, false,
                false, true, null
        );
    }
}
