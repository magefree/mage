package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.ZombieMutantToken;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class Vault12TheNecropolis extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("creature you control that's a Zombie or Mutant");

    static {
        filter.add(Predicates.or(SubType.ZOMBIE.getPredicate(), SubType.MUTANT.getPredicate()));
    }


    public Vault12TheNecropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Each player gets three rad counters.
        sagaAbility.addChapterEffect(this,
                SagaChapter.CHAPTER_I,
                new AddCountersPlayersEffect(CounterType.RAD.createInstance(3), TargetController.EACH_PLAYER)
        );

        // II -- Create X 2/2 black Zombie Mutant creature tokens, where X is the total number of rad counters among players.
        sagaAbility.addChapterEffect(this,
                SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new ZombieMutantToken(), Vault12TheNecropolisValue.instance)
                        .setText("Create X 2/2 black Zombie Mutant creature tokens, "
                                + "where X is the total number of rad counters among players")
        );
        sagaAbility.addHint(Vault12TheNecropolisValue.hint);

        // III -- Put two +1/+1 counters on each creature you control that's a Zombie or Mutant.
        sagaAbility.addChapterEffect(this,
                SagaChapter.CHAPTER_III,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(2), filter)
        );

        this.addAbility(sagaAbility);
    }

    private Vault12TheNecropolis(final Vault12TheNecropolis card) {
        super(card);
    }

    @Override
    public Vault12TheNecropolis copy() {
        return new Vault12TheNecropolis(this);
    }
}

enum Vault12TheNecropolisValue implements DynamicValue {
    instance;

    public static final Hint hint = new ValueHint("total Rad counters", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return game
                .getState()
                .getPlayersInRange(sourceAbility.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(p -> p.getCountersCount(CounterType.RAD))
                .sum();
    }

    @Override
    public Vault12TheNecropolisValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}