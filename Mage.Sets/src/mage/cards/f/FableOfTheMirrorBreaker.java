package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FableOfTheMirrorBreakerToken;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FableOfTheMirrorBreaker extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nonlegendary creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public FableOfTheMirrorBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{2}{R}",
                "Reflection of Kiki-Jiki",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.GOBLIN, SubType.SHAMAN}, "R"
        );

        // Fable of the Mirror-Breaker
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Create a 2/2 red Goblin Shaman creature token with "Whenever this creature attacks, create a Treasure token."
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_I, new CreateTokenEffect(new FableOfTheMirrorBreakerToken()));

        // II — You may discard up to two cards. If you do, draw that many cards.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_II, new DiscardAndDrawThatManyEffect(2)
                .setText("you may discard up to two cards. If you do, draw that many cards"));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Reflection of Kiki-Jiki
        this.getRightHalfCard().setPT(2, 2);

        // {1}, {T}: Create a token that's a copy of another target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new ReflectionOfKikiJikiEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private FableOfTheMirrorBreaker(final FableOfTheMirrorBreaker card) {
        super(card);
    }

    @Override
    public FableOfTheMirrorBreaker copy() {
        return new FableOfTheMirrorBreaker(this);
    }
}

class ReflectionOfKikiJikiEffect extends OneShotEffect {

    ReflectionOfKikiJikiEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of another target nonlegendary creature you control, " +
                "except it has haste. Sacrifice it at the beginning of the next end step";
    }

    private ReflectionOfKikiJikiEffect(final ReflectionOfKikiJikiEffect effect) {
        super(effect);
    }

    @Override
    public ReflectionOfKikiJikiEffect copy() {
        return new ReflectionOfKikiJikiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }

        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, true);
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
