package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HumanMonkToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JuganDefendsTheTemple extends TransformingDoubleFacedCard {

    private static final FilterPermanent filterModified = new FilterControlledCreaturePermanent();

    static {
        filterModified.add(ModifiedPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filterModified, ComparisonType.MORE_THAN, 4);
    private static final Hint hint = new ValueHint("Modified creatures you control", new PermanentsOnBattlefieldCount(filterModified));

    public JuganDefendsTheTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{2}{G}",
                "Remnant of the Rising Star",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.DRAGON, SubType.SPIRIT}, "G");

        // Jugan Defends the Temple

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Create a 1/1 green Human Monk creature token with "{T}: Add {G}."
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_I, new CreateTokenEffect(new HumanMonkToken()));

        // II — Put a +1/+1 counter on each of up to two target creatures.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TargetPermanent(0, 2, StaticFilters.FILTER_PERMANENT_CREATURES)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Remnant of the Rising Star
        this.getRightHalfCard().setPT(2, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever another creature you control enters, you may pay {X}. When you do, put X +1/+1 counters on that creature.
        this.getRightHalfCard().addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new RemnantOfTheRisingStarEffect(), StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));

        // As long as you control five or more modified creatures, Remnant of the Rising Star gets +5/+5 and has trample.
        Ability staticAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(5, 5, Duration.WhileOnBattlefield),
                condition, "as long as you control five or more modified creatures, {this} gets +5/+5"
        ));
        staticAbility.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()), condition, "and has trample"
        ));
        this.getRightHalfCard().addAbility(staticAbility.addHint(hint));
    }

    private JuganDefendsTheTemple(final JuganDefendsTheTemple card) {
        super(card);
    }

    @Override
    public JuganDefendsTheTemple copy() {
        return new JuganDefendsTheTemple(this);
    }
}

class RemnantOfTheRisingStarEffect extends OneShotEffect {

    RemnantOfTheRisingStarEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. When you do, put X +1/+1 counters on that creature";
    }

    private RemnantOfTheRisingStarEffect(final RemnantOfTheRisingStarEffect effect) {
        super(effect);
    }

    @Override
    public RemnantOfTheRisingStarEffect copy() {
        return new RemnantOfTheRisingStarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
        if (player == null || !player.chooseUse(
                Outcome.BoostCreature, "Pay " + cost.getText() + "?", source, game
        )) {
            return false;
        }
        int xValue = player.announceX(0, Integer.MAX_VALUE, "Announce the value for {X} (pay to add counters)", game, source, true);
        cost.add(new GenericManaCost(xValue));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(xValue))
                        .setTargetPointer(new FixedTarget(permanent, game)), false
        ), source);
        return true;
    }
}
