package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDragonKamiReborn extends CardImpl {

    public TheDragonKamiReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.d.DragonKamisEgg.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — You gain 2 life. Look at the top three cards of your library. Exile one of them face down with a hatching counter on it, then put the rest on the bottom of your library in any order.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(new GainLifeEffect(2), new TheDragonKamiRebornEffect())
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private TheDragonKamiReborn(final TheDragonKamiReborn card) {
        super(card);
    }

    @Override
    public TheDragonKamiReborn copy() {
        return new TheDragonKamiReborn(this);
    }
}

class TheDragonKamiRebornEffect extends OneShotEffect {

    TheDragonKamiRebornEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top three cards of your library. Exile one of them face down " +
                "with a hatching counter on it, then put the rest on the bottom of your library in any order";
    }

    private TheDragonKamiRebornEffect(final TheDragonKamiRebornEffect effect) {
        super(effect);
    }

    @Override
    public TheDragonKamiRebornEffect copy() {
        return new TheDragonKamiRebornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary();
        player.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
            card.setFaceDown(true, game);
            card.addCounters(CounterType.HATCHLING.createInstance(), source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
