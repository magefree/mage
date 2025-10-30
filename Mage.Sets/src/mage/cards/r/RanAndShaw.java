package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RanAndShaw extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                SubType.DRAGON.getPredicate(),
                SubType.LESSON.getPredicate()
        ));
    }

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.DRAGON, "Dragons");
    private static final Condition condition = new CompoundCondition(
            "you cast them and there are three or more Dragon and/or Lesson cards in your graveyard",
            CastFromEverywhereSourceCondition.instance, new CardsInControllerGraveyardCondition(3, filter)
    );
    private static final Hint hint = new ValueHint(
            "Dragon and Lesson cards in your graveyard", new CardsInControllerGraveyardCount(filter)
    );

    public RanAndShaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Firebending 2
        this.addAbility(new FirebendingAbility(2));

        // When Ran and Shaw enter, if you cast them and there are three or more Dragon and/or Lesson cards in your graveyard, create a token that's a copy of Ran and Shaw, except it's not legendary.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RanAndShawEffect())
                .withInterveningIf(condition)
                .setTriggerPhrase("When {this} enter, ")
                .addHint(hint));

        // {3}{R}: Dragons you control get +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostControlledEffect(
                2, 0, Duration.EndOfTurn, filter2
        ), new ManaCostsImpl<>("{3}{R}")));
    }

    private RanAndShaw(final RanAndShaw card) {
        super(card);
    }

    @Override
    public RanAndShaw copy() {
        return new RanAndShaw(this);
    }
}

class RanAndShawEffect extends OneShotEffect {

    RanAndShawEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of {this}, except it's not legendary";
    }

    private RanAndShawEffect(final RanAndShawEffect effect) {
        super(effect);
    }

    @Override
    public RanAndShawEffect copy() {
        return new RanAndShawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null
                && new CreateTokenCopyTargetEffect()
                .setIsntLegendary(true)
                .setSavedPermanent(permanent)
                .apply(game, source);
    }
}
