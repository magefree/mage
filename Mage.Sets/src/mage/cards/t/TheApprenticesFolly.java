package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheApprenticesFolly extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("nontoken creature you control that doesn't have the same name as a token you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TheApprenticesFollyPredicate.instance);
    }

    public TheApprenticesFolly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Choose target nontoken creature you control that doesn't have the same name as a token you control. Create a token that's a copy of it, except it isn't legendary, is a Reflection in addition to its other types, and has haste.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new CreateTokenCopyTargetEffect(null, null, true, 1)
                        .setIsntLegendary(true)
                        .withAdditionalSubType(SubType.REFLECTION)
                        .setText("choose target nontoken creature you control that doesn't have the same name as a "
                                + "token you control. Create a token that's a copy of it, except it isn't legendary, "
                                + "is a Reflection in addition to its other types, and has haste"),
                new TargetControlledCreaturePermanent(filter)
        );

        // III -- Sacrifice all Reflections you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new TheApprenticesFollySacrificeAllEffect()
        );

        this.addAbility(sagaAbility);
    }

    private TheApprenticesFolly(final TheApprenticesFolly card) {
        super(card);
    }

    @Override
    public TheApprenticesFolly copy() {
        return new TheApprenticesFolly(this);
    }
}

enum TheApprenticesFollyPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        String name = input.getObject().getName();
        if (name == null || name.isEmpty()) {
            return true;
        }

        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(new NamePredicate(name));
        filter.add(TokenPredicate.TRUE);
        // This works due to the non-token clause on the target. There should be no controlled token with that name.
        return game.getBattlefield().count(filter, input.getPlayerId(), input.getSource(), game) == 0;
    }
}

class TheApprenticesFollySacrificeAllEffect extends OneShotEffect {

    private static final FilterControlledPermanent filterReflection =
            new FilterControlledPermanent(SubType.REFLECTION, "Reflections you control");

    TheApprenticesFollySacrificeAllEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice all Reflections you control";
    }

    private TheApprenticesFollySacrificeAllEffect(final TheApprenticesFollySacrificeAllEffect effect) {
        super(effect);
    }

    @Override
    public TheApprenticesFollySacrificeAllEffect copy() {
        return new TheApprenticesFollySacrificeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield()
                .getAllActivePermanents(filterReflection, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source, game);
        }
        return true;
    }

}
