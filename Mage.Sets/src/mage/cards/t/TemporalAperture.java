package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class TemporalAperture extends CardImpl {

    public TemporalAperture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {5}, {tap}: Shuffle your library, then reveal the top card. Until end of turn, for as long as that card remains on top of your library, play with the top card of your library revealed and you may play that card without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TemporalApertureEffect(), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private TemporalAperture(final TemporalAperture card) {
        super(card);
    }

    @Override
    public TemporalAperture copy() {
        return new TemporalAperture(this);
    }
}

class TemporalApertureEffect extends OneShotEffect {

    public TemporalApertureEffect() {
        super(Outcome.Neutral);
        staticText = "Shuffle your library, then reveal the top card. "
                + "Until end of turn, for as long as that card remains on "
                + "top of your library, play with the top card of your "
                + "library revealed and you may play that card without "
                + "paying its mana cost";
    }

    public TemporalApertureEffect(final TemporalApertureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.shuffleLibrary(source, game);
            Card topCard = controller.getLibrary().getFromTop(game);
            if (topCard != null) {
                Cards cards = new CardsImpl(topCard);
                controller.revealCards("Top card of " + controller.getName() + "'s library revealed", cards, game);
                ContinuousEffect effect = new TemporalApertureTopCardCastEffect(topCard);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public TemporalApertureEffect copy() {
        return new TemporalApertureEffect(this);
    }
}

class TemporalApertureTopCardCastEffect extends AsThoughEffectImpl {

    private final Card card;

    public TemporalApertureTopCardCastEffect(Card card) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.card = card;
        staticText = "Until end of turn, for as long as that card is on top "
                + "of your library, you may cast it without paying its mana costs";
    }

    public TemporalApertureTopCardCastEffect(final TemporalApertureTopCardCastEffect effect) {
        super(effect);
        this.card = effect.card;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TemporalApertureTopCardCastEffect copy() {
        return new TemporalApertureTopCardCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card objectCard = game.getCard(objectId);
            if (objectCard != null) {
                Player controller = game.getPlayer(affectedControllerId);
                if (controller != null
                        && game.getState().getZone(objectId) == Zone.LIBRARY) {
                    if (controller.getLibrary().getFromTop(game).equals(card)) {
                        if (objectCard == card && (objectCard.getSpellAbility() != null || objectCard.isLand(game))) { // only if castable or land
                            allowCardToPlayWithoutMana(objectId, source, affectedControllerId, game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
