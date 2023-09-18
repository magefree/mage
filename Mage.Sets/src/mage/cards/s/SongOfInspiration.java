package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SongOfInspiration extends CardImpl {

    public SongOfInspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");

        // Choose up to two target permanent cards in your graveyard. Roll a d20 and add the total mana value of those cards.
        // 1-14 | Return those cards to your hand.
        // 15+ | Return those cards to your hand. You gain life equal to their total mana value.
        this.getSpellAbility().addEffect(new SongOfInspirationEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_PERMANENT
        ));
    }

    private SongOfInspiration(final SongOfInspiration card) {
        super(card);
    }

    @Override
    public SongOfInspiration copy() {
        return new SongOfInspiration(this);
    }
}

class SongOfInspirationEffect extends OneShotEffect {

    SongOfInspirationEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to two target permanent cards in your graveyard. Roll a d20 " +
                "and add the total mana value of those cards.<br>1-14 | Return those cards to your hand." +
                "<br>15+ | Return those cards to your hand. You gain life equal to their total mana value";
    }

    private SongOfInspirationEffect(final SongOfInspirationEffect effect) {
        super(effect);
    }

    @Override
    public SongOfInspirationEffect copy() {
        return new SongOfInspirationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        int totalMv = cards.getCards(game).stream().mapToInt(MageObject::getManaValue).sum();
        int result = player.rollDice(outcome, source, game, 20);
        player.moveCards(cards, Zone.HAND, source, game);
        if (result + totalMv >= 15) {
            player.gainLife(totalMv, game, source);
        }
        return true;
    }
}
