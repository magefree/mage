
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class DruidicSatchel extends CardImpl {

    public DruidicSatchel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DruidicSatchelEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public DruidicSatchel(final DruidicSatchel card) {
        super(card);
    }

    @Override
    public DruidicSatchel copy() {
        return new DruidicSatchel(this);
    }
}

class DruidicSatchelEffect extends OneShotEffect {

    public DruidicSatchelEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top card of your library. If it's a creature card, create a 1/1 green Saproling creature token. If it's a land card, put that card onto the battlefield under your control. If it's a noncreature, nonland card, you gain 2 life";
    }

    public DruidicSatchelEffect(final DruidicSatchelEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            controller.revealCards(source, new CardsImpl(card), game);
            if (card.isCreature()) {
                new SaprolingToken().putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            }
            if (card.isLand()) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            if (!card.isCreature() && !card.isLand()) {
                controller.gainLife(2, game, source);
            }
        }
        return true;
    }

    @Override
    public DruidicSatchelEffect copy() {
        return new DruidicSatchelEffect(this);
    }
}
