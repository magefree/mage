package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheArgentEtchings extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("other permanents except for artifacts, lands, and Phyrexians");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(Predicates.not(SubType.PHYREXIAN.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    public TheArgentEtchings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.SAGA);
        this.color.setWhite(true);
        this.nightCard = true;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Incubate 2 five times, then transform all Incubator tokens you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new TheArgentEtchingsEffect());

        // II -- Creatures you control get +1/+1 and gain double strike until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+1"),
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("and gain double strike until end of turn")
        );

        // III -- Destroy all other permanents except for artifacts, lands, and Phyrexians. Exile The Argent Etchings, then return it to the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new DestroyAllEffect(filter),
                new ExileSourceAndReturnFaceUpEffect()
        );
        this.addAbility(sagaAbility);
    }

    private TheArgentEtchings(final TheArgentEtchings card) {
        super(card);
    }

    @Override
    public TheArgentEtchings copy() {
        return new TheArgentEtchings(this);
    }
}

class TheArgentEtchingsEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.INCUBATOR);

    static {
        filter.add(TokenPredicate.TRUE);
    }

    TheArgentEtchingsEffect() {
        super(Outcome.Benefit);
        staticText = "incubate 2 five times, then transform all Incubator tokens you control";
    }

    private TheArgentEtchingsEffect(final TheArgentEtchingsEffect effect) {
        super(effect);
    }

    @Override
    public TheArgentEtchingsEffect copy() {
        return new TheArgentEtchingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (int i = 0; i < 5; i++) {
            IncubateEffect.doIncubate(2, game, source);
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            permanent.transform(source, game);
        }
        return true;
    }
}
