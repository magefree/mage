
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class ScentOfJasmine extends CardImpl {

    public ScentOfJasmine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Reveal any number of white cards in your hand. You gain 2 life for each card revealed this way.
        Effect effect = new ScentOfJasmineEffect();
        effect.setText("Reveal any number of white cards in your hand. You gain 2 life for each card revealed this way.");
        this.getSpellAbility().addEffect(effect);
    }

    public ScentOfJasmine(final ScentOfJasmine card) {
        super(card);
    }

    @Override
    public ScentOfJasmine copy() {
        return new ScentOfJasmine(this);
    }
}

class ScentOfJasmineEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("white cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ScentOfJasmineEffect() {
        super(Outcome.GainLife);
    }

    public ScentOfJasmineEffect(final ScentOfJasmineEffect effect) {
        super(effect);
    }

    @Override
    public ScentOfJasmineEffect copy() {
        return new ScentOfJasmineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        if (player.getHand().count(filter, game) > 0) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
            if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                for (UUID uuid : target.getTargets()) {
                    cards.add(player.getHand().get(uuid, game));
                }
                player.revealCards("cards", cards, game);
                player.gainLife(cards.getCards(game).size() * 2, game, source);
            }
        }
        return true;
    }
}
