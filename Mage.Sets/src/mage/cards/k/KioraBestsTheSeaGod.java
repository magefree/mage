package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.KrakenHexproofToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KioraBestsTheSeaGod extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public KioraBestsTheSeaGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}{U}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Create an 8/8 blue Kraken creature token with hexproof.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new KrakenHexproofToken())
        );

        // II — Tap all nonland permanents target opponent controls. They don't untap during their controller's next untap step.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new Effects(new TapAllTargetPlayerControlsEffect(
                        StaticFilters.FILTER_PERMANENTS_NON_LAND
                ), new KioraBestsTheSeaGodEffect()),
                new TargetOpponent()
        );

        // III — Gain control of target permanent an opponent controls. Untap it.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new Effects(
                        new GainControlTargetEffect(Duration.Custom),
                        new UntapTargetEffect().setText("Untap it.")
                ), new TargetPermanent(filter)
        );
        this.addAbility(sagaAbility);
    }

    private KioraBestsTheSeaGod(final KioraBestsTheSeaGod card) {
        super(card);
    }

    @Override
    public KioraBestsTheSeaGod copy() {
        return new KioraBestsTheSeaGod(this);
    }
}

class KioraBestsTheSeaGodEffect extends OneShotEffect {

    KioraBestsTheSeaGodEffect() {
        super(Outcome.Benefit);
        staticText = "They don't untap during their controller's next untap step";
    }

    private KioraBestsTheSeaGodEffect(final KioraBestsTheSeaGodEffect effect) {
        super(effect);
    }

    @Override
    public KioraBestsTheSeaGodEffect copy() {
        return new KioraBestsTheSeaGodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setTargetPointer(new FixedTargets(game.getBattlefield().getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENTS_NON_LAND, source.getFirstTarget(), game
                ), game)), source
        );
        return true;
    }
}
