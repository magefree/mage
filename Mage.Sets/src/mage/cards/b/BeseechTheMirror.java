package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BeseechTheMirror extends CardImpl {

    public BeseechTheMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}{B}");

        // Bargain
        this.addAbility(new BargainAbility());

        // Search your library for a card, exile it face down, then shuffle. If this spell was bargained, you may cast the exiled card without paying its mana cost if that spell's mana value is 4 or less. Put the exiled card into your hand if it wasn't cast this way.
        this.getSpellAbility().addEffect(new BeseechTheMirrorEffect());
    }

    private BeseechTheMirror(final BeseechTheMirror card) {
        super(card);
    }

    @Override
    public BeseechTheMirror copy() {
        return new BeseechTheMirror(this);
    }
}

class BeseechTheMirrorEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    BeseechTheMirrorEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a card, exile it face down, then shuffle. "
                + "If this spell was bargained, you may cast the exiled card without paying "
                + "its mana cost if that spell's mana value is 4 or less. Put the exiled card "
                + "into your hand if it wasn't cast this way.";
    }

    private BeseechTheMirrorEffect(final BeseechTheMirrorEffect effect) {
        super(effect);
    }

    @Override
    public BeseechTheMirrorEffect copy() {
        return new BeseechTheMirrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // Search your library for a card
        TargetCardInLibrary target = new TargetCardInLibrary();
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                // exile it face down
                card.setFaceDown(true, game);
                controller.moveCards(card, Zone.EXILED, source, game, false, true, false, null);
                card.setFaceDown(true, game);

                // then shuffle
                controller.shuffleLibrary(source, game);

                // If this spell was bargained,
                if (BargainedCondition.instance.apply(game, source)) {
                    // you may cast the exiled card without paying its mana cost if that spell's mana value is 4 or less.
                    CardUtil.castSpellWithAttributesForFree(controller, source, game, card, filter);
                }
                game.getState().processAction(game);

                if (game.getState().getZone(card.getId()).equals(Zone.EXILED)) {
                    // Put the exiled card into your hand if it wasn't cast this way.
                    controller.moveCards(card, Zone.HAND, source, game, false, true, false, null);
                    card.setFaceDown(false, game);
                }

                return true;
            }
        }
        return false;
    }
}