package mage.cards.m;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuseVortex extends CardImpl {

    public MuseVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Exile the top X cards of your library. You may cast an instant or sorcery spell with mana value X or less from among them without paying its mana cost. Then put the exiled instant and sorcery cards that weren't cast this way into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new MuseVortexEffect());
    }

    private MuseVortex(final MuseVortex card) {
        super(card);
    }

    @Override
    public MuseVortex copy() {
        return new MuseVortex(this);
    }
}

class MuseVortexEffect extends OneShotEffect {

    MuseVortexEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top X cards of your library. You may cast an instant or sorcery spell "
                + "with mana value X or less from among them without paying its mana cost. "
                + "Then put the exiled instant and sorcery cards that weren't cast this way into your hand "
                + "and the rest on the bottom of your library in a random order";
    }

    private MuseVortexEffect(final MuseVortexEffect effect) {
        super(effect);
    }

    @Override
    public MuseVortexEffect copy() {
        return new MuseVortexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        FilterCard filter = new FilterInstantOrSorceryCard("an instant or sorcery card with mana value " + xValue + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        TargetCardInExile target = new TargetCardInExile(filter);
        target.setNotTarget(true);
        if (controller.choose(Outcome.Benefit, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                cards.remove(card);
                if (cardWasCast) {
                    cards.remove(card);
                } else {
                    game.informPlayer(controller, "You're not able to cast "
                            + card.getIdName() + " or you canceled the casting.");
                }
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
                return true;
            }
        }
        return false;
    }
}
