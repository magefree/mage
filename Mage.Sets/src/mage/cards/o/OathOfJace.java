
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class OathOfJace extends CardImpl {

    public OathOfJace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.supertype.add(SuperType.LEGENDARY);

        // When Oath of Jace enters the battlefield, draw three cards, then discard two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(3, 2), false));

        // At the beginning of your upkeep, scry X, where X is the number of planeswalkers you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new OathOfJaceEffect(), TargetController.YOU, false));

    }

    private OathOfJace(final OathOfJace card) {
        super(card);
    }

    @Override
    public OathOfJace copy() {
        return new OathOfJace(this);
    }
}

class OathOfJaceEffect extends OneShotEffect {

    public OathOfJaceEffect() {
        super(Outcome.DrawCard);
        this.staticText = "scry X, where X is the number of planeswalkers you control";
    }

    public OathOfJaceEffect(final OathOfJaceEffect effect) {
        super(effect);
    }

    @Override
    public OathOfJaceEffect copy() {
        return new OathOfJaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int planeswalker = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_PLANESWALKER, source.getControllerId(), game);
            if (planeswalker > 0) {
                controller.scry(planeswalker, source, game);
            }
            return true;
        }
        return false;
    }
}
