package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class CosmicRebirth extends CardImpl {

    public CosmicRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{W}");

        // Choose target permanent card in your graveyard. If it has mana value 3 or less, you may put it onto the battlefield. If you don't put it onto the battlefield, put it into your hand.
        this.getSpellAbility().addEffect(new CosmicRebirthEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_PERMANENT));

        // You gain 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(3).concatBy("<br>"));
    }

    private CosmicRebirth(final CosmicRebirth card) {
        super(card);
    }

    @Override
    public CosmicRebirth copy() {
        return new CosmicRebirth(this);
    }
}

class CosmicRebirthEffect extends OneShotEffect {

    CosmicRebirthEffect() {
        super(Outcome.Benefit);
        staticText = "choose target permanent card in your graveyard. If it has mana value 3 or less, " +
                "you may put it onto the battlefield. If you don't put it onto the battlefield, put it into your hand";
    }

    private CosmicRebirthEffect(final CosmicRebirthEffect effect) {
        super(effect);
    }

    @Override
    public CosmicRebirthEffect copy() {
        return new CosmicRebirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return player != null && card != null && player.moveCards(
                card, card.getManaValue() <= 3 && player.chooseUse(
                        Outcome.PutCardInPlay, "Put it onto the battlefield or your hand?",
                        null, "Battlefield", "Hand", source, game
                ) ? Zone.BATTLEFIELD : Zone.HAND, source, game
        );
    }
}
