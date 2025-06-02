package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.command.emblems.KaitoBaneOfNightmaresEmblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author jackd149
 */
public final class KaitoBaneOfNightmares extends CardImpl {

    public KaitoBaneOfNightmares(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAITO);
        this.setStartingLoyalty(4);

        // Ninjutsu {1}{U}{B}
        this.addAbility(new NinjutsuAbility("{1}{U}{B}"));

        // During your turn, as long as Kaito has one or more loyalty counters on him, he's a 3/4 Ninja creature and has hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BecomesCreatureSourceEffect(
                        new CreatureToken(3, 4, "3/4 Ninja creature")
                                .withSubType(SubType.NINJA)
                                .withAbility(HexproofAbility.getInstance()), null, Duration.WhileOnBattlefield
                ), KaitoBaneOfNightmaresCondition.instance, "During your turn, as long as {this} has one or more loyalty counters on him, " +
                "he's a 3/4 Ninja creature and has hexproof."
        )).addHint(MyTurnHint.instance));

        // +1: You get an emblem with "Ninjas you control get +1/+1."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KaitoBaneOfNightmaresEmblem()), 1));

        // 0: Surveil 2. Then draw a card for each opponent who lost life this turn.
        Ability ability = new LoyaltyAbility(new SurveilEffect(2), 0);
        ability.addEffect(new DrawCardSourceControllerEffect(KaitoBaneOfNightmaresCount.instance).concatBy("Then"));
        this.addAbility(ability);

        // -2: Tap target creature. Put two stun counters on it.
        Ability minusTwoAbility = new LoyaltyAbility(new TapTargetEffect(), -2);
        minusTwoAbility.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(2))
                .setText("Put two stun counters on it"));
        minusTwoAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(minusTwoAbility);
    }

    private KaitoBaneOfNightmares(final KaitoBaneOfNightmares card) {
        super(card);
    }

    @Override
    public KaitoBaneOfNightmares copy() {
        return new KaitoBaneOfNightmares(this);
    }
}

enum KaitoBaneOfNightmaresCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!MyTurnCondition.instance.apply(game, source)) {
            return false;
        }

        Permanent permanent = game.getPermanent(source.getSourceId());

        if (permanent == null) {
            return false;
        }

        int loyaltyCount = permanent.getCounters(game).getCount(CounterType.LOYALTY);
        return loyaltyCount > 0;

    }
}

enum KaitoBaneOfNightmaresCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null) {
            return watcher.getNumberOfOpponentsWhoLostLife(sourceAbility.getControllerId(), game);
        }
        return 0;
    }

    @Override
    public KaitoBaneOfNightmaresCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "opponent who lost life this turn.";
    }
}
