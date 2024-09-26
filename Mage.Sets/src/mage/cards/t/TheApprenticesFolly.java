package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.SacrificeAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.common.TargetControlledCreaturePermanent;

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

    private static final FilterPermanent filterReflection =
            new FilterPermanent(SubType.REFLECTION, "Reflections");

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
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new SacrificeAllControllerEffect(filterReflection));

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
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        input.getPlayerId(), input.getSource(), game
                )
                .stream()
                .filter(PermanentToken.class::isInstance)
                .noneMatch(permanent -> permanent.sharesName(input.getObject(), game));
    }
}
