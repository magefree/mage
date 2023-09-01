package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.RitualOfTheReturnedZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class RitualOfTheReturned extends CardImpl {

    public RitualOfTheReturned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile target creature card from your graveyard. Create a black Zombie creature token with power equal to the exiled card's power and toughness equal to the exiled card's toughness.
        this.getSpellAbility().addEffect(new RitualOfTheReturnedExileEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
    }

    private RitualOfTheReturned(final RitualOfTheReturned card) {
        super(card);
    }

    @Override
    public RitualOfTheReturned copy() {
        return new RitualOfTheReturned(this);
    }
}

class RitualOfTheReturnedExileEffect extends OneShotEffect {

    public RitualOfTheReturnedExileEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile target creature card from your graveyard. Create a black Zombie creature token. Its power is equal to that card's power and its toughness is equal to that card's toughness.";
    }

    private RitualOfTheReturnedExileEffect(final RitualOfTheReturnedExileEffect effect) {
        super(effect);
    }

    @Override
    public RitualOfTheReturnedExileEffect copy() {
        return new RitualOfTheReturnedExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                controller.moveCardToExileWithInfo(card, null, null, source, game, Zone.GRAVEYARD, true);
                return new CreateTokenEffect(
                        new RitualOfTheReturnedZombieToken(card.getPower().getValue(), card.getToughness().getValue())).apply(game, source);
            }
        }
        return false;
    }
}
