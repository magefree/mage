package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author emerald000
 */
public final class CreamOfTheCrop extends CardImpl {

    public CreamOfTheCrop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a creature enters the battlefield under your control, 
        // you may look at the top X cards of your library, where X is that 
        // creature's power. If you do, put one of those cards on top of your 
        // library and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new CreamOfTheCropEffect(),
                StaticFilters.FILTER_PERMANENT_CREATURE, true, SetTargetPointer.PERMANENT,
                "Whenever a creature enters the battlefield under your control, "
                + "you may look at the top X cards of your library, where X "
                + "is that creature's power. If you do, put one of those cards "
                + "on top of your library and the rest on the bottom of "
                + "your library in any order."));
    }

    private CreamOfTheCrop(final CreamOfTheCrop card) {
        super(card);
    }

    @Override
    public CreamOfTheCrop copy() {
        return new CreamOfTheCrop(this);
    }
}

class CreamOfTheCropEffect extends OneShotEffect {

    CreamOfTheCropEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top X cards of your library, "
                + "where X is that creature's power. If you do, put "
                + "one of those cards on top of your library and the "
                + "rest on the bottom of your library in any order";
    }

    CreamOfTheCropEffect(final CreamOfTheCropEffect effect) {
        super(effect);
    }

    @Override
    public CreamOfTheCropEffect copy() {
        return new CreamOfTheCropEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && permanent != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, permanent.getPower().getValue()));
            if (!cards.isEmpty()) {
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put on top of your library"));
                target.setNotTarget(true);
                controller.chooseTarget(Outcome.Benefit, cards, target, source, game);
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, true);
                }
                controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
