
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class AngelheartVial extends CardImpl {

    public AngelheartVial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Whenever you're dealt damage, you may put that many charge counters on Angelheart Vial.
        this.addAbility(new AngelheartVialTriggeredAbility());

        // {2}, {tap}, Remove four charge counters from Angelheart Vial: You gain 2 life and draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(4))));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private AngelheartVial(final AngelheartVial card) {
        super(card);
    }

    @Override
    public AngelheartVial copy() {
        return new AngelheartVial(this);
    }
}

class AngelheartVialTriggeredAbility extends TriggeredAbilityImpl {

    public AngelheartVialTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AngelheartVialEffect(), true);
    }

    public AngelheartVialTriggeredAbility(final AngelheartVialTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AngelheartVialTriggeredAbility copy() {
        return new AngelheartVialTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, you may put that many charge counters on {this}.";
    }
}

class AngelheartVialEffect extends OneShotEffect {

    public AngelheartVialEffect() {
        super(Outcome.Benefit);
    }

    public AngelheartVialEffect(final AngelheartVialEffect effect) {
        super(effect);
    }

    @Override
    public AngelheartVialEffect copy() {
        return new AngelheartVialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.CHARGE.createInstance((Integer) this.getValue("damageAmount")), source.getControllerId(), source, game);
        }
        return true;
    }
}

