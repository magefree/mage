package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ZombieMob extends CardImpl {

    public ZombieMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Zombie Mob enters the battlefield with a +1/+1 counter on it for each creature card in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE), false
        ), "with a +1/+1 counter on it for each creature card in your graveyard"));

        // When Zombie Mob enters the battlefield, exile all creature cards from your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ZombieMobExileEffect()));

    }

    private ZombieMob(final ZombieMob card) {
        super(card);
    }

    @Override
    public ZombieMob copy() {
        return new ZombieMob(this);
    }
}

class ZombieMobExileEffect extends OneShotEffect {

    ZombieMobExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile all creature cards from your graveyard";
    }

    private ZombieMobExileEffect(final ZombieMobExileEffect effect) {
        super(effect);
    }

    @Override
    public ZombieMobExileEffect copy() {
        return new ZombieMobExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        controller.moveCards(cards, Zone.EXILED, source, game);
        return true;
    }
}
