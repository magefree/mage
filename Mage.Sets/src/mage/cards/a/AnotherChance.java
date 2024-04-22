package mage.cards.a;

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
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AnotherChance extends CardImpl {

    public AnotherChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // You may mill two cards. Then return up to two creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new AnotherChanceEffect());
    }

    private AnotherChance(final AnotherChance card) {
        super(card);
    }

    @Override
    public AnotherChance copy() {
        return new AnotherChance(this);
    }
}

/**
 * Inspired by {@link mage.cards.u.UnsealTheNecropolis}
 */
class AnotherChanceEffect extends OneShotEffect {

    AnotherChanceEffect() {
        super(Outcome.Benefit);
        staticText = "You may mill two cards. Then return up to two creature cards from your graveyard to your hand";
    }

    private AnotherChanceEffect(final AnotherChanceEffect effect) {
        super(effect);
    }

    @Override
    public AnotherChanceEffect copy() {
        return new AnotherChanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.chooseUse(outcome, "Mill two cards?", source, game)) {
            player.millCards(2, source, game);
        }

        // Make sure the mill has been processed.
        game.getState().processAction(game);

        TargetCard target = new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD, true
        );
        player.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        if (!cards.isEmpty()) {
            player.moveCards(cards, Zone.HAND, source, game);
        }
        return true;
    }
}
