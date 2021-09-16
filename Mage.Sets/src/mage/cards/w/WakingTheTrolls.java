package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.token.TrollWarriorToken;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetLandPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WakingTheTrolls extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("land card from a graveyard");

    public WakingTheTrolls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{G}");

        this.subtype.add(SubType.SAGA);

        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I — Destroy target land.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new DestroyTargetEffect(), new TargetLandPermanent()
        );

        // II — Put target land card from a graveyard onto the battlefield under your control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new ReturnFromGraveyardToBattlefieldTargetEffect().setText(
                        "put target land card from a graveyard onto the battlefield under your control"
                ), new TargetCardInGraveyard(filter)
        );

        // III — Choose target opponent. If they control fewer lands than you, create a number of 4/4 green Troll Warrior creature tokens with trample equal to the difference.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new WakingTheTrollsEffect(), new TargetOpponent()
        );

        this.addAbility(sagaAbility);
    }

    private WakingTheTrolls(final WakingTheTrolls card) {
        super(card);
    }

    @Override
    public WakingTheTrolls copy() {
        return new WakingTheTrolls(this);
    }
}

class WakingTheTrollsEffect extends OneShotEffect {

    WakingTheTrollsEffect() {
        super(Outcome.Benefit);
        staticText = "choose target opponent. If they control fewer lands than you, "
                + "create a number of 4/4 green Troll Warrior creature tokens with trample equal to the difference";
    }

    private WakingTheTrollsEffect(final WakingTheTrollsEffect effect) {
        super(effect);
    }

    @Override
    public WakingTheTrollsEffect copy() {
        return new WakingTheTrollsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int myLands = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getSourceId(), source.getControllerId(), game
        );
        int theirLands = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getSourceId(), source.getFirstTarget(), game
        );
        if (myLands <= theirLands) {
            return false;
        }
        return new TrollWarriorToken().putOntoBattlefield(
                myLands - theirLands, game,
                source, source.getControllerId()
        );
    }
}
