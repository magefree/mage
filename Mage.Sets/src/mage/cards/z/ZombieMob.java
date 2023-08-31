
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author tcontis
 */

public final class ZombieMob extends CardImpl {

    public ZombieMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Zombie Mob enters the battlefield with a +1/+1 counter on it for each creature card in your graveyard.
        // When Zombie Mob enters the battlefield, exile all creature cards from your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new ZombieMobEffect(), "with a +1/+1 counter on it for each creature card in your graveyard. When {this} enters the battlefield, exile all creature cards from your graveyard."));

    }

    private ZombieMob(final ZombieMob card) {
        super(card);
    }

    @Override
    public ZombieMob copy() {
        return new ZombieMob(this);
    }
}

class ZombieMobEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();
    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public ZombieMobEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with a +1/+1 counter on it for each creature card in your graveyard. When {this} enters the battlefield, exile all creature cards from your graveyard.";
    }

    private ZombieMobEffect(final ZombieMobEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null && controller != null) {
            int amount = 0;
            amount += controller.getGraveyard().count(filter, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            Cards cards = new CardsImpl(controller.getGraveyard().getCards(filter, game));
            controller.moveCards(cards, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }

    @Override
    public ZombieMobEffect copy() {
        return new ZombieMobEffect(this);
    }

}