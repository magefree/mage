package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWorldSpell extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("non-Saga permanent card");

    static {
        filter.add(Predicates.not(SubType.SAGA.getPredicate()));
    }

    public TheWorldSpell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{G}{G}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I, II -- Look at the top seven cards of your library. You may reveal a non-Saga permanent card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new LookLibraryAndPickControllerEffect(
                        7, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
                )
        );

        // III -- Put up to two non-Saga permanent cards from your hand onto the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheWorldSpellEffect());
        this.addAbility(sagaAbility);
    }

    private TheWorldSpell(final TheWorldSpell card) {
        super(card);
    }

    @Override
    public TheWorldSpell copy() {
        return new TheWorldSpell(this);
    }
}

class TheWorldSpellEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard("non-Saga permanent cards");

    static {
        filter.add(Predicates.not(SubType.SAGA.getPredicate()));
    }

    TheWorldSpellEffect() {
        super(Outcome.Benefit);
        staticText = "put up to two non-Saga permanent cards from your hand onto the battlefield";
    }

    private TheWorldSpellEffect(final TheWorldSpellEffect effect) {
        super(effect);
    }

    @Override
    public TheWorldSpellEffect copy() {
        return new TheWorldSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, 2, filter);
        player.choose(outcome, player.getHand(), target, game);
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
    }
}
