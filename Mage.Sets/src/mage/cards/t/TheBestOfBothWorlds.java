package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AssimilateTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.AssimilatedBySourceHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author JayDi85
 */
public final class TheBestOfBothWorlds extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature an opponent controls");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public TheBestOfBothWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Assimilate target nonartifact creature an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new AssimilateTargetEffect(),
                new TargetPermanent(filter)
        );

        // II — Artifact creatures you control get +2/+2 until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new BoostControlledEffect(2, 2, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE)
        );

        // III — Exile the Borg assimilated with this Saga, then return it to the battlefield under its owner's control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheBestOfBothWorldsEffect());

        sagaAbility.addHint(AssimilatedBySourceHint.instance);
        this.addAbility(sagaAbility);
    }

    private TheBestOfBothWorlds(final TheBestOfBothWorlds card) {
        super(card);
    }

    @Override
    public TheBestOfBothWorlds copy() {
        return new TheBestOfBothWorlds(this);
    }
}

class TheBestOfBothWorldsEffect extends OneShotEffect {

    TheBestOfBothWorldsEffect() {
        super(Outcome.Neutral);
        staticText = "exile the Borg assimilated with this Saga, then return it to the battlefield under its owner's control";
    }

    private TheBestOfBothWorldsEffect(final TheBestOfBothWorldsEffect effect) {
        super(effect);
    }

    @Override
    public TheBestOfBothWorldsEffect copy() {
        return new TheBestOfBothWorldsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Permanent> toFlicker = AssimilateTargetEffect.getAssimilatedPermanents(game, source);
        if (toFlicker.isEmpty()) {
            return false;
        }
        controller.moveCards(toFlicker, Zone.EXILED, source, game);
        game.processAction();
        Cards cardsToReturn = new CardsImpl();
        for (Card card : CardUtil.getAllCardsFromPermanentsLeftBattlefield(toFlicker, game)) {
            cardsToReturn.add(card.getMainCard());
        }
        cardsToReturn.retainZone(Zone.EXILED, game);
        if (!cardsToReturn.isEmpty()) {
            controller.moveCards(cardsToReturn.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
        }
        return true;
    }
}
