package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDieEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author tiera3 - based on SnickeringSquirrel.java, Magmasaur.java, JinnieFayJetmirsSecond.java
 */
public final class Xenosquirrels extends CardImpl {

    public Xenosquirrels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.ALIEN, SubType.SQUIRREL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Xenosquirrels enters with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(2), true
        ), "with two +1/+1 counters on it"));

        // After you roll a die, you may remove a +1/+1 counter from Xenosquirrels. If you do, increase or decrease the result by 1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new XenosquirrelsEffect()));
    }

    private Xenosquirrels(final Xenosquirrels card) {
        super(card);
    }

    @Override
    public Xenosquirrels copy() {
        return new Xenosquirrels(this);
    }
}

class XenosquirrelsEffect extends ReplacementEffectImpl {

    XenosquirrelsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "After you roll a die, you may remove a +1/+1 counter from Xenosquirrels. If you do, increase or decrease the result by 1.";
    }

    private XenosquirrelsEffect(final XenosquirrelsEffect effect) {
        super(effect);
    }

    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Player dieRoller = game.getPlayer(event.getPlayerId());
        if (controller == null || dieRoller == null || controller != dieRoller) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || 0 == permanent.getCounters(game).getCount(CounterType.P1P1) ) {
            return false;
        }
        if (controller.chooseUse(outcome, "Remove a +1/+1 counter from " + permanent.getLogName() + " to increase or decrease the result of a die ("
                + event.getAmount() + ")  by 1?", source, game)) {
            permanent.removeCounters(CounterType.P1P1.getName(), 1, source, game);
            ((RollDieEvent) event).incResultModifier( player.chooseUse(
                outcome, "Increase or Decrease " + event.getAmount() + " by 1", null,
                "Increase", "Decrease", source, game ) ? 1 : -1 );
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public XenosquirrelsEffect copy() {
        return new XenosquirrelsEffect(this);
    }
}
