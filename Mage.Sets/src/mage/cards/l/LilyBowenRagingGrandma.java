package mage.cards.l;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Cguy7777
 */
public final class LilyBowenRagingGrandma extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 16));
    }

    public LilyBowenRagingGrandma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lily Bowen, Raging Grandma enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(2)),
                "with two +1/+1 counters on it"));

        // At the beginning of your upkeep, double the number of +1/+1 counters on Lily Bowen if its power is 16 or less.
        // Otherwise, remove all but one +1/+1 counter from it, then you gain 1 life for each +1/+1 counter removed this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new DoubleCountersSourceEffect(CounterType.P1P1),
                        new LilyBowenRagingGrandmaEffect(),
                        new SourceMatchesFilterCondition(filter),
                        "double the number of +1/+1 counters on {this} if its power is 16 or less. " +
                                "Otherwise, remove all but one +1/+1 counter from it, " +
                                "then you gain 1 life for each +1/+1 counter removed this way"),
                TargetController.YOU,
                false));
    }

    private LilyBowenRagingGrandma(final LilyBowenRagingGrandma card) {
        super(card);
    }

    @Override
    public LilyBowenRagingGrandma copy() {
        return new LilyBowenRagingGrandma(this);
    }
}

class LilyBowenRagingGrandmaEffect extends OneShotEffect {

    LilyBowenRagingGrandmaEffect() {
        super(Outcome.Benefit);
    }

    private LilyBowenRagingGrandmaEffect(final LilyBowenRagingGrandmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }

        // Remove all but one +1/+1 counter from it, then you gain 1 life for each +1/+1 counter removed this way.
        int count = permanent.getCounters(game).getCount(CounterType.P1P1);
        if (count <= 1) {
            return true;
        }

        int countToRemove = count - 1;
        permanent.removeCounters(CounterType.P1P1.createInstance(countToRemove), source, game);
        new GainLifeEffect(countToRemove).apply(game, source);
        return true;
    }

    @Override
    public LilyBowenRagingGrandmaEffect copy() {
        return new LilyBowenRagingGrandmaEffect(this);
    }
}
