
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class CephalidVandal extends CardImpl {

    public CephalidVandal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, put a shred counter on Cephalid Vandal. Then put the top card of your library into your graveyard for each shred counter on Cephalid Vandal.
        Effect effect = new CephalidVandalEffect();
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SHRED.createInstance(), false), TargetController.YOU, false);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CephalidVandal(final CephalidVandal card) {
        super(card);
    }

    @Override
    public CephalidVandal copy() {
        return new CephalidVandal(this);
    }
}

class CephalidVandalEffect extends OneShotEffect {

    public CephalidVandalEffect() {
        super(Outcome.Neutral);
        staticText = "Then mill a card for each shred counter on {this}";
    }

    private CephalidVandalEffect(final CephalidVandalEffect effect) {
        super(effect);
    }

    @Override
    public CephalidVandalEffect copy() {
        return new CephalidVandalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && controller != null) {
            int amount = permanent.getCounters(game).getCount(CounterType.SHRED);
            controller.millCards(amount, source, game);
        }
        return true;
    }
}
