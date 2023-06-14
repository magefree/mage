package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScrollOfIsildur extends CardImpl {

    public ScrollOfIsildur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Gain control of up to one target artifact for as long as you control Scroll of Isildur. The Ring tempts you.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new Effects(
                        new GainControlTargetEffect(Duration.WhileControlled), new TheRingTemptsYouEffect()
                ), new TargetArtifactPermanent(0, 1)
        );

        // II -- Tap up to two target creatures. Put a stun counter on each of them.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new Effects(
                        new TapTargetEffect(), new AddCountersTargetEffect(CounterType.STUN.createInstance())
                        .setText("Put a stun counter on each of them")
                ), new TargetCreaturePermanent(0, 2)
        );

        // III - Draw a card for each tapped creature target opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new ScrollOfIsildurEffect(), new TargetOpponent()
        );
        this.addAbility(sagaAbility);
    }

    private ScrollOfIsildur(final ScrollOfIsildur card) {
        super(card);
    }

    @Override
    public ScrollOfIsildur copy() {
        return new ScrollOfIsildur(this);
    }
}

class ScrollOfIsildurEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    ScrollOfIsildurEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card for each tapped creature target opponent controls";
    }

    private ScrollOfIsildurEffect(final ScrollOfIsildurEffect effect) {
        super(effect);
    }

    @Override
    public ScrollOfIsildurEffect copy() {
        return new ScrollOfIsildurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || opponent == null) {
            return false;
        }
        int count = game.getBattlefield().count(filter, opponent.getId(), source, game);
        return count > 0 && player.drawCards(count, source, game) > 0;
    }
}
