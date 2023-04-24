package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnsealTheNecropolis extends CardImpl {

    public UnsealTheNecropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Each player mills three cards. Then you return up to two creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsEachPlayerEffect(3, TargetController.EACH_PLAYER));
        this.getSpellAbility().addEffect(new UnsealTheNecropolisEffect());
    }

    private UnsealTheNecropolis(final UnsealTheNecropolis card) {
        super(card);
    }

    @Override
    public UnsealTheNecropolis copy() {
        return new UnsealTheNecropolis(this);
    }
}

class UnsealTheNecropolisEffect extends OneShotEffect {

    UnsealTheNecropolisEffect() {
        super(Outcome.Benefit);
        staticText = "Then you return up to two creature cards from your graveyard to your hand";
    }

    private UnsealTheNecropolisEffect(final UnsealTheNecropolisEffect effect) {
        super(effect);
    }

    @Override
    public UnsealTheNecropolisEffect copy() {
        return new UnsealTheNecropolisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD, true
        );
        player.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        return !cards.isEmpty() && player.moveCards(cards, Zone.HAND, source, game);
    }
}
