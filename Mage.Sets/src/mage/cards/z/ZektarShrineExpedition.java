package mage.cards.z;

import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.RedElementalWithTrampleAndHaste;

import java.util.UUID;

/**
 * @author North
 */
public final class ZektarShrineExpedition extends CardImpl {

    public ZektarShrineExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Landfall - Whenever a land enters the battlefield under your control, you may put a quest counter on Zektar Shrine Expedition.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        // Remove three quest counters from Zektar Shrine Expedition and sacrifice it: Create a 7/1 red Elemental creature token with trample and haste. Exile it at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(
                new ZektarShrineExpeditionEffect(),
                new CompositeCost(
                        new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)),
                        new SacrificeSourceCost(),
                        "Remove three quest counters from {this} and sacrifice it"
                )
        ));
    }

    private ZektarShrineExpedition(final ZektarShrineExpedition card) {
        super(card);
    }

    @Override
    public ZektarShrineExpedition copy() {
        return new ZektarShrineExpedition(this);
    }
}

class ZektarShrineExpeditionEffect extends OneShotEffect {

    ZektarShrineExpeditionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 7/1 red Elemental creature token with trample and haste. Exile it at the beginning of the next end step";
    }

    private ZektarShrineExpeditionEffect(final ZektarShrineExpeditionEffect effect) {
        super(effect);
    }

    @Override
    public ZektarShrineExpeditionEffect copy() {
        return new ZektarShrineExpeditionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new RedElementalWithTrampleAndHaste());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
