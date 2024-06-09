
package mage.cards.b;

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

import java.util.UUID;

/**
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

    BountyOfTheLuxaEffect() {
        super(Outcome.Benefit);
        staticText = "remove all flood counters from {this}. " +
                "If no counters were removed this way, put a flood counter on {this} and draw a card. " +
                "Otherwise, add {C}{G}{U}";
    }

    private BountyOfTheLuxaEffect(final BountyOfTheLuxaEffect effect) {
        super(effect);
    }

    @Override
    public BountyOfTheLuxaEffect copy() {
        return new BountyOfTheLuxaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent bountyOfLuxa = source.getSourcePermanentIfItStillExists(game);
        if (bountyOfLuxa == null) {
            // No flood counters will be removed. Only the draw part of the effect will apply.
            controller.drawCards(1, source, game);
            return true;
        }
        int amountRemoved = bountyOfLuxa.removeAllCounters(CounterType.FLOOD.getName(), source, game);
        if (amountRemoved == 0) {
            new AddCountersSourceEffect(CounterType.FLOOD.createInstance()).apply(game, source);
            controller.drawCards(1, source, game);
        } else {
            Mana manaToAdd = new Mana();
            manaToAdd.increaseColorless();
            manaToAdd.increaseGreen();
            manaToAdd.increaseBlue();
            controller.getManaPool().addMana(manaToAdd, game, source);
        }
        return true;
    }

}
