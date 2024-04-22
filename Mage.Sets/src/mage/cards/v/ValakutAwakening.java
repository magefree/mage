package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValakutAwakening extends ModalDoubleFacedCard {

    public ValakutAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{2}{R}",
                "Valakut Stoneforge", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Valakut Awakening
        // Instant

        // Put any number of cards from your hand on the bottom of your library, then draw that many cards plus one.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ValakutAwakeningEffect());

        // 2.
        // Valakut Stoneforge
        // Land

        // Valakut Stoneforge enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private ValakutAwakening(final ValakutAwakening card) {
        super(card);
    }

    @Override
    public ValakutAwakening copy() {
        return new ValakutAwakening(this);
    }
}

class ValakutAwakeningEffect extends OneShotEffect {

    ValakutAwakeningEffect() {
        super(Outcome.Benefit);
        staticText = "put any number of cards from your hand on the bottom of your library, " +
                "then draw that many cards plus one";
    }

    private ValakutAwakeningEffect(final ValakutAwakeningEffect effect) {
        super(effect);
    }

    @Override
    public ValakutAwakeningEffect copy() {
        return new ValakutAwakeningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard targetCard = new TargetCardInHand(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CARDS
        );
        player.choose(outcome, player.getHand(), targetCard, source, game);
        Cards cards = new CardsImpl(targetCard.getTargets());
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        player.drawCards(cards.size() + 1, source, game);
        return true;
    }
}
