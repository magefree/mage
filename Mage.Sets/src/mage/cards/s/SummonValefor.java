package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.*;

/**
 * @author TheElk801
 */
public final class SummonValefor extends CardImpl {

    public SummonValefor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I - Sonic Wings -- Each opponent chooses a creature with the greatest mana value among creatures they control. Return those creatures to their owners' hands.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new SummonValeforEffect());
            ability.withFlavorWord("Sonic Wings");
        });

        // II, III, IV - Tap up to one target creature and put a stun counter on it.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_IV,
                new Effects(
                        new TapTargetEffect(),
                        new AddCountersTargetEffect(CounterType.STUN.createInstance())
                                .setText("and put a stun counter on it")
                ), new TargetCreaturePermanent(0, 1)
        );
        this.addAbility(sagaAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SummonValefor(final SummonValefor card) {
        super(card);
    }

    @Override
    public SummonValefor copy() {
        return new SummonValefor(this);
    }
}

class SummonValeforEffect extends OneShotEffect {

    enum SummonValeforPredicate implements ObjectSourcePlayerPredicate<Permanent> {
        instance;

        @Override
        public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
            return input
                    .getObject()
                    .getManaValue()
                    >= game
                    .getBattlefield()
                    .getActivePermanents(
                            StaticFilters.FILTER_CONTROLLED_CREATURE,
                            input.getPlayerId(), input.getSource(), game
                    )
                    .stream()
                    .mapToInt(MageObject::getManaValue)
                    .max()
                    .orElse(0);
        }
    }

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with the greatest mana value");

    static {
        filter.add(SummonValeforPredicate.instance);
    }

    SummonValeforEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent chooses a creature with the greatest mana value " +
                "among creatures they control. Return those creatures to their owners' hands";
    }

    private SummonValeforEffect(final SummonValeforEffect effect) {
        super(effect);
    }

    @Override
    public SummonValeforEffect copy() {
        return new SummonValeforEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = new HashSet<>();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null || !game.getBattlefield().contains(
                    StaticFilters.FILTER_CONTROLLED_CREATURE, opponentId, source, game, 1
            )) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            player.choose(Outcome.ReturnToHand, target, source, game);
            permanents.add(game.getPermanent(target.getFirstTarget()));
        }
        permanents.removeIf(Objects::isNull);
        if (permanents.isEmpty()) {
            return false;
        }
        Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.moveCards(permanents, Zone.HAND, source, game));
        return true;
    }
}
