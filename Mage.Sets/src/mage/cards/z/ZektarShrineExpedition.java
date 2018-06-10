
package mage.cards.z;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.ZektarShrineElementalToken;

/**
 *
 * @author North
 */
public final class ZektarShrineExpedition extends CardImpl {

    public ZektarShrineExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Landfall - Whenever a land enters the battlefield under your control, you may put a quest counter on Zektar Shrine Expedition.
        this.addAbility(new LandfallAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        // Remove three quest counters from Zektar Shrine Expedition and sacrifice it: Create a 7/1 red Elemental creature token with trample and haste. Exile it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZektarShrineExpeditionEffect(), new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public ZektarShrineExpedition(final ZektarShrineExpedition card) {
        super(card);
    }

    @Override
    public ZektarShrineExpedition copy() {
        return new ZektarShrineExpedition(this);
    }
}

class ZektarShrineExpeditionEffect extends OneShotEffect {

    public ZektarShrineExpeditionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 7/1 red Elemental creature token with trample and haste. Exile it at the beginning of the next end step";
    }

    public ZektarShrineExpeditionEffect(final ZektarShrineExpeditionEffect effect) {
        super(effect);
    }

    @Override
    public ZektarShrineExpeditionEffect copy() {
        return new ZektarShrineExpeditionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        CreateTokenEffect effect = new CreateTokenEffect(new ZektarShrineElementalToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }

}
