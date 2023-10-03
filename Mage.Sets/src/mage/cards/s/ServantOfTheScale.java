
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ServantOfTheScale extends CardImpl {

    public ServantOfTheScale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Servant of the Scale enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                "with a +1/+1 counter on it"));

        // When Servant of the Scale dies, put X +1/+1 counters on target creature you control, where X is the number of +1/+1 counter on Servant of the Scale.
        Ability ability = new DiesSourceTriggeredAbility(new ServantOfTheScaleEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ServantOfTheScale(final ServantOfTheScale card) {
        super(card);
    }

    @Override
    public ServantOfTheScale copy() {
        return new ServantOfTheScale(this);
    }
}

class ServantOfTheScaleEffect extends OneShotEffect {

    public ServantOfTheScaleEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "put X +1/+1 counters on target creature you control, where X is the number of +1/+1 counters on {this}";
    }

    private ServantOfTheScaleEffect(final ServantOfTheScaleEffect effect) {
        super(effect);
    }

    @Override
    public ServantOfTheScaleEffect copy() {
        return new ServantOfTheScaleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (sourcePermanent != null && controller != null
                && (sourcePermanent.getZoneChangeCounter(game) == source.getSourceObjectZoneChangeCounter() // Token
                || sourcePermanent.getZoneChangeCounter(game) + 1 == source.getSourceObjectZoneChangeCounter())) { // PermanentCard
            int amount = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            if (amount > 0) {
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(amount));
                effect.setTargetPointer(targetPointer);
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
