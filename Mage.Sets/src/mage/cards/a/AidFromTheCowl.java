
package mage.cards.a;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.RevoltWatcher;

/**
 *
 * @author fireshoes
 */
public final class AidFromTheCowl extends CardImpl {

    private static final String ruleText = "<i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, "
            + "reveal the top card of your library. If it's a permanent card, you may put it onto the battlefield. Otherwise, you may put it on the bottom of your library.";

    public AidFromTheCowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // <i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn,
        // reveal the top card of your library. If it is a permanent card, you may put it onto the battlefield. Otherwise, put it on the bottom of your library.
        TriggeredAbility ability = new BeginningOfYourEndStepTriggeredAbility(new AidFromTheCowlEffect(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, RevoltCondition.instance, ruleText), new RevoltWatcher());
    }

    private AidFromTheCowl(final AidFromTheCowl card) {
        super(card);
    }

    @Override
    public AidFromTheCowl copy() {
        return new AidFromTheCowl(this);
    }
}

class AidFromTheCowlEffect extends OneShotEffect {

    public AidFromTheCowlEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. If it's a permanent card, you may put it onto the battlefield. Otherwise, you may put that card on the bottom of your library";
    }

    public AidFromTheCowlEffect(final AidFromTheCowlEffect effect) {
        super(effect);
    }

    @Override
    public AidFromTheCowlEffect copy() {
        return new AidFromTheCowlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getIdName(), cards, game);

            if (card != null) {
                if (new FilterPermanentCard().match(card, game) && controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " onto the battlefield?", source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else if (controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " on the bottom of your library?", source, game)) {
                    controller.putCardsOnBottomOfLibrary(cards, game, source, false);
                } else {
                    game.informPlayers(controller.getLogName() + " puts the revealed card back to the top of the library.");
                }
            }
        }
        return true;
    }
}
