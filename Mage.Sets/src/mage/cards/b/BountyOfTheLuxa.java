
package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class BountyOfTheLuxa extends CardImpl {

    public BountyOfTheLuxa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{U}");

        // At the beginning of your precombat main phase, remove all flood counters from Bounty of the Luxa.
        // If no counters were removed this way, put a flood counter on Bounty of the Luxa and draw a card.
        // Otherwise, add {C}{G}{U}.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new BountyOfTheLuxaEffect(), TargetController.YOU, false));
    }

    private BountyOfTheLuxa(final BountyOfTheLuxa card) {
        super(card);
    }

    @Override
    public BountyOfTheLuxa copy() {
        return new BountyOfTheLuxa(this);
    }

}

class BountyOfTheLuxaEffect extends OneShotEffect {

    public BountyOfTheLuxaEffect() {
        super(Outcome.Benefit);
        staticText = "remove all flood counters from {this}. " +
                "If no counters were removed this way, put a flood counter on {this} and draw a card. " +
                "Otherwise, add {C}{G}{U}";
    }

    public BountyOfTheLuxaEffect(final BountyOfTheLuxaEffect effect) {
        super(effect);
    }

    @Override
    public BountyOfTheLuxaEffect copy() {
        return new BountyOfTheLuxaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent bountyOfLuxa = game.getPermanent(source.getSourceId());
        if (bountyOfLuxa != null && bountyOfLuxa.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter()) {
            bountyOfLuxa = null;
        }
        if (controller == null) { return false; }

        if (bountyOfLuxa != null
                && bountyOfLuxa.getCounters(game).getCount(CounterType.FLOOD) > 0) {
            bountyOfLuxa.removeCounters(CounterType.FLOOD.createInstance(bountyOfLuxa.getCounters(game).getCount(CounterType.FLOOD)), source, game);
            if (bountyOfLuxa.getCounters(game).getCount(CounterType.FLOOD) == 0) {
                Mana manaToAdd = new Mana();
                manaToAdd.increaseColorless();
                manaToAdd.increaseGreen();
                manaToAdd.increaseBlue();
                controller.getManaPool().addMana(manaToAdd, game, source);
            }
        } else {
            if (bountyOfLuxa != null) {
                new AddCountersSourceEffect(CounterType.FLOOD.createInstance()).apply(game, source);
            }
            controller.drawCards(1, source, game);
        }
        return true;
    }

}
