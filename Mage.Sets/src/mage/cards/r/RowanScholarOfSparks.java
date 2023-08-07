package mage.cards.r;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.command.emblems.RowanScholarOfSparksEmblem;
import mage.game.permanent.token.Elemental44Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class RowanScholarOfSparks extends ModalDoubleFacedCard {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and sorcery spells");

    public RowanScholarOfSparks(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.ROWAN}, "{2}{R}",
                "Will, Scholar of Frost",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.WILL}, "{4}{U}"
        );

        // 1.
        // Rowan, Scholar of Sparks
        // Legendary Planeswalker - Rowan
        this.getLeftHalfCard().setStartingLoyalty(2);

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // +1: Rowan, Scholar of Sparks deals 1 damage to each opponent. If you've drawn three or more cards this turn, she deals 3 damage to each opponent instead.
        Ability ability = new LoyaltyAbility(new ConditionalOneShotEffect(
                new DamagePlayersEffect(3, TargetController.OPPONENT),
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                RowanScholarOfSparksCondition.instance, "{this} deals 1 damage to each opponent. " +
                "If you've drawn three or more cards this turn, she deals 3 damage to each opponent instead"
        ), 1);
        ability.addWatcher(new CardsAmountDrawnThisTurnWatcher());
        this.getLeftHalfCard().addAbility(ability.addHint(RowanScholarOfSparksHint.instance));

        // −4: You get an emblem with "Whenever you cast an instant or sorcery spell, you may pay {2}. If you do, copy that spell. You may choose new targets for the copy."
        this.getLeftHalfCard().addAbility(new LoyaltyAbility(
                new GetEmblemEffect(new RowanScholarOfSparksEmblem()), -4
        ));

        // 2.
        // Will, Scholar of Frost
        // Legendary Planeswalker - Will
        this.getRightHalfCard().setStartingLoyalty(4);

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filter, 1)
        ));

        // +1: Up to one target creature has base power and toughness 0/2 until your next turn.
        ability = new LoyaltyAbility(new SetBasePowerToughnessTargetEffect(
                0, 2, Duration.UntilYourNextTurn
        ), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.getRightHalfCard().addAbility(ability);

        // −3: Draw two cards.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(2), -3));

        // −7: Exile up to five target permanents. For each permanent exiled this way, its controller creates a 4/4 blue and red Elemental Creature token.
        ability = new LoyaltyAbility(new WillScholarOfFrostEffect(), -7);
        ability.addTarget(new TargetPermanent(0, 5, StaticFilters.FILTER_PERMANENTS));
        this.getRightHalfCard().addAbility(ability);
    }

    private RowanScholarOfSparks(final RowanScholarOfSparks card) {
        super(card);
    }

    @Override
    public RowanScholarOfSparks copy() {
        return new RowanScholarOfSparks(this);
    }
}

enum RowanScholarOfSparksCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getAmountCardsDrawn(source.getControllerId()) >= 3;
    }
}

enum RowanScholarOfSparksHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        int drawn = watcher != null ? watcher.getAmountCardsDrawn(ability.getControllerId()) : 0;
        return "Cards drawn this turn: " + drawn;
    }

    @Override
    public RowanScholarOfSparksHint copy() {
        return instance;
    }
}

class WillScholarOfFrostEffect extends OneShotEffect {

    WillScholarOfFrostEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to five target permanents. For each permanent exiled this way, " +
                "its controller creates a 4/4 blue and red Elemental creature token";
    }

    private WillScholarOfFrostEffect(final WillScholarOfFrostEffect effect) {
        super(effect);
    }

    @Override
    public WillScholarOfFrostEffect copy() {
        return new WillScholarOfFrostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(targetPointer.getTargets(game, source));
        Map<UUID, Integer> playerMap = cards
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .map(game::getControllerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Function.identity(),
                        x -> 1, Integer::sum
                ));
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Map.Entry<UUID, Integer> entry : playerMap.entrySet()) {
            new Elemental44Token().putOntoBattlefield(entry.getValue(), game, source, entry.getKey());
        }
        return true;
    }
}
